package com.zzyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.zzyl.common.constant.CacheConstants;
import com.zzyl.nursing.config.WebSocketServer;
import com.zzyl.nursing.domain.AlertData;
import com.zzyl.nursing.domain.DeviceData;
import com.zzyl.nursing.mapper.DeviceMapper;
import com.zzyl.nursing.service.IAlertDataService;
import com.zzyl.nursing.vo.AlertNotifyVo;
import com.zzyl.system.mapper.SysUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.AlertRuleMapper;
import com.zzyl.nursing.domain.AlertRule;
import com.zzyl.nursing.service.IAlertRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import static com.zzyl.common.constant.CacheConstants.IOT_DEVICE_LAST_DATA;

/**
 报警规则Service业务层处理

 @author Euphoria
 @date 2025-10-16 */
@Service
@Slf4j
public class AlertRuleServiceImpl extends ServiceImpl<AlertRuleMapper, AlertRule> implements IAlertRuleService {

    @Autowired
    private AlertRuleMapper alertRuleMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private IAlertDataService alertDataService;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     查询报警规则

     @param id 报警规则主键
     @return 报警规则
     */
    @Override
    public AlertRule selectAlertRuleById(Long id) {
        return alertRuleMapper.selectById(id);
    }

    /**
     查询报警规则列表

     @param alertRule 报警规则
     @return 报警规则
     */
    @Override
    public List<AlertRule> selectAlertRuleList(AlertRule alertRule) {
        return alertRuleMapper.selectAlertRuleList(alertRule);
    }

    /**
     新增报警规则

     @param alertRule 报警规则
     @return 结果
     */
    @Override
    public int insertAlertRule(AlertRule alertRule) {
        return alertRuleMapper.insert(alertRule);
    }

    /**
     修改报警规则

     @param alertRule 报警规则
     @return 结果
     */
    @Override
    public int updateAlertRule(AlertRule alertRule) {
        return alertRuleMapper.updateById(alertRule);
    }

    /**
     批量删除报警规则

     @param ids 需要删除的报警规则主键
     @return 结果
     */
    @Override
    public int deleteAlertRuleByIds(Long[] ids) {
        return alertRuleMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     删除报警规则信息

     @param id 报警规则主键
     @return 结果
     */
    @Override
    public int deleteAlertRuleById(Long id) {
        return alertRuleMapper.deleteById(id);
    }

    @Override
    public void alertFilter() {
        Long count = lambdaQuery().eq(AlertRule::getStatus, 1).count();
        if (count <= 0) {
            log.info("没有启用的报警规则");
            return;
        }

        List<Object> values = redisTemplate.opsForHash().values(IOT_DEVICE_LAST_DATA);

        if (CollUtil.isEmpty(values)) {
            log.info("没有设备数据");
            return;
        }

        List<DeviceData> deviceDatas = new ArrayList<>();
        for (Object value : values) {
            List<DeviceData> deviceData = JSON.parseArray(value.toString(), DeviceData.class);
            deviceDatas.addAll(deviceData);
        }

        deviceDatas.forEach(this::alertFilter);

    }

    private void alertFilter(DeviceData deviceData) {

        // 判断当前上报时间是不是超过了一分钟
        long between = LocalDateTimeUtil.between(deviceData.getAlarmTime(), LocalDateTime.now(), ChronoUnit.SECONDS);

        if (between > 60) {
            log.info("设备数据上报时间超过一分钟，忽略");
            return;
        }

        // 查规则
        List<AlertRule> iotIdRules = lambdaQuery()
                .eq(AlertRule::getStatus, 1)
                .eq(AlertRule::getIotId, deviceData.getIotId())
                .eq(AlertRule::getFunctionId, deviceData.getFunctionId())
                .eq(AlertRule::getProductKey, deviceData.getProductKey())
                .list();

        List<AlertRule> allRules = lambdaQuery()
                .eq(AlertRule::getStatus, 1)
                .eq(AlertRule::getIotId, -1)
                .eq(AlertRule::getFunctionId, deviceData.getFunctionId())
                .eq(AlertRule::getProductKey, deviceData.getProductKey())
                .list();

        Collection<AlertRule> allAlertRules = CollUtil.addAll(allRules, iotIdRules);

        if (CollectionUtils.isEmpty(allAlertRules)) {
            log.info("没有匹配的报警规则");
            return;
        }

        allAlertRules.forEach(rule -> deviceDataAlarmHandler(rule, deviceData));
    }

    private void deviceDataAlarmHandler(AlertRule rule, DeviceData deviceData) {
        //判断上报时间是否在规则的生效时段内 00:00:00~23:59:59
        String[] split = rule.getAlertEffectivePeriod().split("~");
        LocalTime startTime = LocalTime.parse(split[0]);
        LocalTime endTime = LocalTime.parse(split[1]);

        LocalTime now = LocalTime.now();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            log.info("设备数据上报时间不在规则的生效时段内");
            return;
        }

        String iotId = deviceData.getIotId();
        String alertTriggerCountKey = CacheConstants.ALERT_TRIGGER_COUNT_PREFIX + iotId + ":" + deviceData.getFunctionId() + ":" + rule.getId();

        String dataValue = deviceData.getDataValue();
        Double value = rule.getValue();
        String operator = rule.getOperator();
        int compare = Double.compare(Double.parseDouble(dataValue), value);
        if (("<".equals(operator) && compare < 0) || (">=".equals(operator) && compare >= 0)) {
            log.info("设备数据满足报警规则");
            log.info("设备数据：{} : {} : {} ", dataValue, operator, value);
        } else {
            log.info("设备数据不满足报警规则");
            redisTemplate.delete(alertTriggerCountKey);
            return;
        }

        //判断是否在沉默周期内
        String alertSilentKey = CacheConstants.ALERT_SILENT_PREFIX + iotId + ":" + deviceData.getFunctionId() + ":" + rule.getId();
        String silentValue = redisTemplate.opsForValue().get(alertSilentKey);

        if (StrUtil.isNotEmpty(silentValue)) {
            log.info("当前设备{},功能{},规则{},处于沉默状态", deviceData.getIotId(), deviceData.getFunctionId(), rule.getId());
            return;
        }

        //判断持续周期
        String triggerCount = redisTemplate.opsForValue().get(alertTriggerCountKey);
        int count = StrUtil.isEmpty(triggerCount) ? 1 : Integer.parseInt(triggerCount) + 1;

        Integer duration = rule.getDuration();
        if (ObjectUtil.notEqual(count, duration)) {
            log.info("当前设备{},功能{},规则{}持续周期未达到，当前是第{}个周期，需要达到{}个周期才会报警", deviceData.getIotId(), deviceData.getFunctionId(), rule.getId(), count, duration);
            redisTemplate.opsForValue().set(alertTriggerCountKey, String.valueOf(count));
            return;
        }

        //设置沉默周期
        //删除redis的报警数据
        redisTemplate.delete(alertTriggerCountKey);
        redisTemplate.opsForValue().set(alertSilentKey, "1", rule.getAlertSilentPeriod(), TimeUnit.MINUTES);

        //查找报警通知人
        List<Long> userIds = new ArrayList<>();
        if (ObjectUtil.equals(rule.getAlertDataType(), 0)) {
            //老人异常数据，通知护理员
            if (deviceData.getLocationType() == 0) {
                // 说明是随身设备，根据老人查找护理员
                userIds = deviceMapper.selectNursingIdsByIotIdWithElder(deviceData.getIotId());
            } else if (deviceData.getLocationType() == 1 && deviceData.getPhysicalLocationType() == 2) {
                userIds = deviceMapper.selectNursingIdsByIotIdWithBed(deviceData.getIotId());
            }
        } else {
            //设备异常数据，通知维修工
            userIds = sysUserRoleMapper.selectUserIdByRoleName("护理工");
        }

        //不管是哪种情况，都要通知超级管理员
        List<Long> longs = sysUserRoleMapper.selectUserIdByRoleName("超级管理员");
        userIds.addAll(longs);

        userIds = CollUtil.distinct(userIds);

        //保存报警数据
        List<AlertData> alertDataList = insertAlertData(userIds, rule, deviceData);

        // websocket推送消息
        websocketNotity(alertDataList.get(0), rule, userIds);
    }

    /**
     * websocket推送消息
     * @param alertData
     * @param rule
     * @param allUserIds
     */
    private void websocketNotity(AlertData alertData, AlertRule rule, Collection<Long> allUserIds) {

        // 属性拷贝
        AlertNotifyVo alertNotifyVo = BeanUtil.toBean(alertData, AlertNotifyVo.class);
        alertNotifyVo.setFunctionName(rule.getFunctionName());
        alertNotifyVo.setAlertDataType(rule.getAlertDataType());
        alertNotifyVo.setNotifyType(1);
        // 向指定的人推送消息
        webSocketServer.sendMessageToConsumer(alertNotifyVo, allUserIds);

    }

    private List<AlertData> insertAlertData(List<Long> userIds, AlertRule rule, DeviceData deviceData) {
        List<AlertData> list = new ArrayList<>();
        userIds.forEach(userId -> {
            AlertData alertData = BeanUtil.toBean(deviceData, AlertData.class);
            alertData.setId(null);
            alertData.setStatus(0);
            alertData.setUserId(userId);
            alertData.setType(rule.getAlertDataType());
            String alertReason = CharSequenceUtil.format("{}{}{},持续{}个周期就报警", rule.getFunctionName(), rule.getOperator(), rule.getValue(), rule.getDuration());
            alertData.setAlertReason(alertReason);
            alertData.setAlertRuleId(rule.getId());
            list.add(alertData);
        });

        alertDataService.saveBatch(list);
        return list;
    }
}
