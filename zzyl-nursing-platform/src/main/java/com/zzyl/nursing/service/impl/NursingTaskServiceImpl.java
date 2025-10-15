package com.zzyl.nursing.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.common.core.domain.entity.SysUser;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.StringUtils;
import com.zzyl.nursing.domain.*;
import com.zzyl.nursing.dto.CancelNursingTaskDto;
import com.zzyl.nursing.dto.NursingTaskDto;
import com.zzyl.nursing.dto.NursingTaskQueryDto;
import com.zzyl.nursing.mapper.CheckInMapper;
import com.zzyl.nursing.mapper.ElderMapper;
import com.zzyl.nursing.mapper.NursingLevelMapper;
import com.zzyl.nursing.mapper.NursingTaskMapper;
import com.zzyl.nursing.service.*;
import com.zzyl.nursing.vo.NursingPlanVo;
import com.zzyl.nursing.vo.NursingProjectPlanVo;
import com.zzyl.nursing.vo.NursingTaskVo;
import com.zzyl.system.mapper.SysUserMapper;
import com.zzyl.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 护理任务Service业务层处理

 @author ruoyi
 @date 2024-09-27 */
@Service
@Slf4j
public class NursingTaskServiceImpl extends ServiceImpl<NursingTaskMapper, NursingTask> implements INursingTaskService {

    @Autowired
    private NursingTaskMapper nursingTaskMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ElderMapper elderMapper;

    @Resource
    private CheckInMapper checkInMapper;

    /**
     查询护理任务

     @param id 护理任务主键
     @return 护理任务
     */
    @Override
    public NursingTaskVo selectNursingTaskById(Long id) {

        NursingTask nursingTask = getById(id);

        NursingTaskVo nursingTaskVo = BeanUtil.toBean(nursingTask, NursingTaskVo.class);
        if (ObjectUtil.isNull(nursingTask)) {
            throw new BaseException("护理任务不存在");
        }
        Elder elder = elderMapper.selectById(nursingTaskVo.getElderId());
        if (ObjectUtil.isNull(elder)) {
            throw new BaseException("老人不存在");
        }

        CheckIn checkIn = checkInMapper.selectOne(new LambdaQueryWrapper<CheckIn>().eq(CheckIn::getElderId, nursingTaskVo.getElderId()));
        if (ObjectUtil.isNull(checkIn)) {
            throw new BaseException("该老人未入住");
        }

        nursingTaskVo.setAge(IdcardUtil.getAgeByIdCard(elder.getIdCardNo()));
        nursingTaskVo.setSex(String.valueOf(IdcardUtil.getGenderByIdCard(elder.getIdCardNo())));
        nursingTaskVo.setNursingLevelName(checkIn.getNursingLevelName());

        List<Long> nurseIdList = Arrays.stream(nursingTask.getNursingId().split(","))
                .map(String::trim)     // 去除空格
                .map(Long::valueOf)    // 转为Long
                .collect(Collectors.toList());
        // 判空
        if (CollUtil.isEmpty(nurseIdList)) {
            return nursingTaskVo;
        }
        List<SysUser> sysUsers = sysUserMapper.selectBatchIds(nurseIdList);

        if (CollUtil.isEmpty(sysUsers)) {
            return nursingTaskVo;
        }

        List<String> collect = sysUsers.stream().map(SysUser::getNickName).collect(Collectors.toList());

        nursingTaskVo.setNursingName(collect);

        if (nursingTask.getStatus() == 3 || nursingTask.getStatus() == 2){
            String updateBy = nursingTask.getUpdateBy();
            SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(updateBy));
            nursingTaskVo.setUpdater(sysUser.getNickName());
        }

        return nursingTaskVo;
    }

    /**
     查询护理任务列表

     @param nursingTaskQueryDto 护理任务
     @return 护理任务
     */
    @Override
    public List<NursingTaskVo> selectNursingTaskList(NursingTaskQueryDto nursingTaskQueryDto) {

        List<NursingTaskVo> nursingTaskVos = nursingTaskMapper.selectNursingTaskList(nursingTaskQueryDto);

        if (CollUtil.isEmpty(nursingTaskVos)) {
            return new ArrayList<>();
        }

        List<Long> nursingIdList = nursingTaskVos.stream()
                .map(NursingTaskVo::getNursingId)
                .filter(Objects::nonNull)
                .flatMap(idStr -> Arrays.stream(idStr.split(",")))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .map(Long::valueOf)
                .distinct()
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(nursingIdList)) {
            return nursingTaskVos;
        }

        List<SysUser> sysUsers = sysUserMapper.selectBatchIds(nursingIdList);


        if (CollUtil.isEmpty(sysUsers)) {
            return nursingTaskVos;
        }

        Map<Long, String> userNameMap = sysUsers.stream()
                .collect(Collectors.toMap(SysUser::getUserId, SysUser::getNickName));

        nursingTaskVos.forEach(task -> {
            if (task.getNursingId() != null) {
                List<String> nurseNames = Arrays.stream(task.getNursingId().split(","))
                        .map(String::trim)
                        .map(Long::valueOf)
                        .map(userId -> userNameMap.getOrDefault(userId, "未知护理员"))
                        .collect(Collectors.toList());
                task.setNursingName(nurseNames);
            }
        });

        return nursingTaskVos;
    }

    /**
     新增护理任务

     @param nursingTask 护理任务
     @return 结果
     */
    @Override
    public int insertNursingTask(NursingTask nursingTask) {
        return save(nursingTask) ? 1 : 0;
    }

    /**
     修改护理任务

     @param nursingTask 护理任务
     @return 结果
     */
    @Override
    public int updateNursingTask(NursingTask nursingTask) {
        return updateById(nursingTask) ? 1 : 0;
    }

    /**
     批量删除护理任务

     @param ids 需要删除的护理任务主键
     @return 结果
     */
    @Override
    public int deleteNursingTaskByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     删除护理任务信息

     @param id 护理任务主键
     @return 结果
     */
    @Override
    public int deleteNursingTaskById(Long id) {
        return removeById(id) ? 1 : 0;
    }

    @Autowired
    private ICheckInService checkInService;

    @Autowired
    private ICheckInConfigService checkInConfigService;

    @Autowired
    private INursingLevelService nursingLevelService;

    @Autowired
    private INursingPlanService nursingPlanService;

    @Autowired
    private INursingElderService nursingElderService;

    @Autowired
    private INursingProjectService nursingProjectService;

    /**
     生成护理任务

     @param elder
     */
    @Override
    @Transactional
    public void createMonthTask(Elder elder) {
        //校验
        if (elder == null || elder.getId() == null) {
            log.info("老人不能为空");
            return;
        }

        //获取入住信息
        CheckIn checkIn = checkInService.getOne(Wrappers.<CheckIn>lambdaQuery().eq(CheckIn::getElderId, elder.getId()));

        //获取入住及入住配置信息
        CheckInConfig checkInConfig = checkInConfigService.getOne(Wrappers.<CheckInConfig>lambdaQuery().eq(CheckInConfig::getCheckInId, checkIn.getId()));
        //开始时间
        LocalDateTime startTime = LocalDateTime.now();
        //判断开始是在当天时间
        if (checkInConfig.getFeeStartDate().toLocalDate().isEqual(LocalDateTime.now().toLocalDate())) {
            startTime = checkInConfig.getFeeStartDate();
        }

        //护理等级
        NursingLevel nursingLevel = nursingLevelService.selectNursingLevelById(checkInConfig.getNursingLevelId());
        //护理计划
        NursingPlanVo nursingPlanVo = nursingPlanService.selectNursingPlanById(nursingLevel.getLplanId());
        //查询老人对应的护理员列表
        List<NursingElder> nursingElderList = nursingElderService.list(Wrappers.<NursingElder>lambdaQuery().eq(NursingElder::getElderId, elder.getId()));
        String nursingIds;
        if (CollUtil.isNotEmpty(nursingElderList)) {
            List<Long> list = nursingElderList.stream().map(NursingElder::getNursingId).collect(Collectors.toList());
            //多个id用逗号分隔，转换为字符串
            nursingIds = StringUtils.join(list, ",");
        } else {
            nursingIds = "";
        }

        //查询所有的护理项目
        List<NursingProject> list = nursingProjectService.list();
        //转换为map key是id,value是name
        Map<Long, String> nursingProjectMap = list.stream().collect(Collectors.toMap(NursingProject::getId, NursingProject::getName));

        //组装护理任务
        List<NursingTask> nursingTasks = new ArrayList<>();

        LocalDateTime finalStartTime = startTime;
        nursingPlanVo.getProjectPlans().forEach(v -> {
            //执行频次
            Integer executeFrequency = v.getExecuteFrequency();
            //执行时间
            String executeTime = v.getExecuteTime();
            LocalTime localTime = LocalTime.parse(executeTime);
            //开始执行时间
            LocalDateTime firstExecutionTime = LocalDateTime.of(finalStartTime.toLocalDate(), localTime);
            // 计算相差天数
            LocalDateTime monthEndTime = LocalDateTime.of(finalStartTime.getYear(), checkInConfig.getFeeStartDate().getMonth(), finalStartTime.toLocalDate().lengthOfMonth(), 23, 59);
            // 间隔天数
            long diffDays = monthEndTime.toLocalDate().toEpochDay() - finalStartTime.toLocalDate().toEpochDay() + 1;
            if (v.getExecuteCycle().equals(0)) {
                // 日
                createTaskByDay(firstExecutionTime, diffDays, nursingTasks, executeFrequency, elder, v, nursingIds, nursingProjectMap);
            } else if (v.getExecuteCycle().equals(1)) {
                // 周
                createTaskByWeek(firstExecutionTime, diffDays, nursingTasks, executeFrequency, elder, v, monthEndTime, nursingIds, nursingProjectMap);
            } else {
                // 月
                createTaskByMonth(firstExecutionTime, monthEndTime, nursingTasks, executeFrequency, elder, v, nursingIds, nursingProjectMap);
            }
        });

        if (CollUtil.isEmpty(nursingTasks)) {
            return;
        }
        saveBatch(nursingTasks);
    }

    /**
     按月创建任务

     @param firstExecutionTime
     @param monthEndTime
     @param nursingTasks
     @param executeFrequency
     @param elder
     @param v                   */
    private void createTaskByMonth(LocalDateTime firstExecutionTime, LocalDateTime monthEndTime, List<NursingTask> nursingTasks, Integer executeFrequency, Elder elder, NursingProjectPlanVo v, String nursingIds, Map<Long, String> nursingProjectMap) {
        LocalDateTime executionTime = firstExecutionTime;
        Integer diffDay = (monthEndTime.plusSeconds(1).getDayOfMonth() - executionTime.getDayOfMonth()) / executeFrequency;
        for (int x = 0; x < executeFrequency; x++) {
            // 根据时间差和执行顺序计算每个任务的具体时间
            LocalDateTime seconds = executionTime.plusDays(diffDay * x);
            // 初始化护理任务对象
            NursingTask nursingTask = getNursingTask(elder, v, nursingIds, seconds, nursingProjectMap);
            // 将生成的任务添加到任务列表中
            nursingTasks.add(nursingTask);
        }
    }

    /**
     根据周为单位创建护理任务
     <p>
     此方法旨在根据给定的开始时间、差异天数、执行频率等参数，为指定的老人生成护理任务列表
     它考虑了跨年情况以及每月结束时间的限制，避免生成越过月末的有效任务

     @param firstExecutionTime 首次执行时间，用于计算后续任务的时间点
     @param diffDays           任务之间的时间差，以天为单位，此方法专注于周级别，故此值应与7的倍数有关
     @param nursingTasks       护理任务列表，方法将新生成的任务添加到此列表中
     @param executeFrequency   执行频率，决定每周内任务执行的次数
     @param elder              老人信息对象，任务相关的老人信息由此对象提供
     @param v                  护理项目计划的视图对象，包含项目ID等信息
     @param monthEndTime       每月结束时间的限制，确保任务不会跨月生成
     @param nursingIds         护理员ID列表，以字符串形式传递，用于分配任务给护理员
     */
    private void createTaskByWeek(LocalDateTime firstExecutionTime, long diffDays, List<NursingTask> nursingTasks, Integer executeFrequency, Elder elder, NursingProjectPlanVo v, LocalDateTime monthEndTime, String nursingIds, Map<Long, String> nursingProjectMap) {
        int i;
        // 以7天为步长遍历差异天数，创建每周的任务
        for (i = 0; i < diffDays - 7; i = i + 7) {
            // 计算每周结束时间
            LocalDateTime dayEndTime = LocalDateTime.of(firstExecutionTime.plusDays(i + 7).toLocalDate(), LocalTime.of(23, 59));
            // 计算本周的执行起始时间
            LocalDateTime executionTime = firstExecutionTime.plusDays(i);
            // 根据执行频率计算时间差，用于确定本周内各任务的时间点
            Integer diffDay = (dayEndTime.plusSeconds(1).getDayOfYear() - executionTime.getDayOfYear()) / executeFrequency;
            // 根据执行频率生成本周的任务
            for (int x = 0; x < executeFrequency; x++) {
                // 根据时间差和执行顺序计算每个任务的具体时间
                LocalDateTime seconds = executionTime.plusDays(diffDay * x);
                // 初始化护理任务对象
                NursingTask nursingTask = getNursingTask(elder, v, nursingIds, seconds, nursingProjectMap);
                // 将生成的任务添加到任务列表中
                nursingTasks.add(nursingTask);
            }
        }

        // 处理边界情况，当i超过diffDays-7且小于diffDays时
        if (i > diffDays - 7 && i < diffDays) {
            // 计算到达diffDays时的结束时间
            LocalDateTime dayEndTime = LocalDateTime.of(firstExecutionTime.plusDays(i + 7).toLocalDate(), LocalTime.of(23, 59));
            // 如果结束时间与开始时间年份不同，则返回，避免跨年任务
            if (ObjectUtil.notEqual(dayEndTime.getYear(), firstExecutionTime.getYear())) {
                return;
            }
            // 计算执行时间
            LocalDateTime executionTime = firstExecutionTime.plusDays(i);
            // 计算时间差，确定任务时间点
            Integer diffDay = (dayEndTime.plusSeconds(1).getDayOfYear() - executionTime.getDayOfYear()) / executeFrequency;
            // 根据执行频率生成任务，直到月末
            for (int x = 0; x < executeFrequency; x++) {
                // 计算每个任务的时间
                LocalDateTime seconds = executionTime.plusDays(diffDay * x);
                // 如果任务时间超过月末结束时间，则停止生成
                if (seconds.isAfter(monthEndTime)) {
                    break;
                }
                // 初始化护理任务对象
                NursingTask nursingTask = getNursingTask(elder, v, nursingIds, seconds, nursingProjectMap);
                // 将生成的任务添加到任务列表中
                nursingTasks.add(nursingTask);
            }
        }

    }


    /**
     * 按日创建任务
     * @param firstExecutionTime
     * @param diffDays
     * @param nursingTasks
     * @param executeFrequency
     * @param elder
     * @param v
     */
    /**
     根据天数创建护理任务
     <p>
     该方法根据首次执行时间、任务间隔天数、护理任务列表、执行频率、老人信息和护理项目计划数据，
     创建一系列的护理任务。任务的创建以天为单位，直到达到任务间隔天数为止。

     @param firstExecutionTime 首次执行时间，标志着任务创建的起始时间
     @param diffDays           任务间隔天数，确定需要创建任务的天数范围
     @param nursingTasks       护理任务列表，创建的任务将被添加到此列表中
     @param executeFrequency   执行频率，决定一天内任务执行的次数
     @param elder              老人对象，包含执行任务的老人的信息
     @param v                  护理项目计划数据对象，包含护理项目的相关信息
     */
    private void createTaskByDay(LocalDateTime firstExecutionTime, long diffDays, List<NursingTask> nursingTasks, Integer executeFrequency, Elder elder, NursingProjectPlanVo v, String nursingIds, Map<Long, String> nursingProjectMap) {
        // 遍历每一天，从首次执行时间开始，直到达到任务间隔天数
        for (int i = 0; i < diffDays; i++) {
            // 计算每一天的任务执行起始时间
            LocalDateTime executionTime = firstExecutionTime.plusDays(i);
            // 确定当天任务执行的结束时间（当天的23:59）
            LocalDateTime dayEndTime = LocalDateTime.of(executionTime.toLocalDate(), LocalTime.of(23, 59));
            // 计算当天内每次执行任务之间的小时差，用于确定执行时间点
            Integer diffHour = (dayEndTime.plusSeconds(1).getHour() - executionTime.getHour()) / executeFrequency;
            // 根据执行频率创建任务
            for (int x = 0; x < executeFrequency; x++) {
                // 计算每个任务的具体执行时间
                LocalDateTime seconds = executionTime.plusHours(diffHour * x);
                // 创建新的护理任务对象
                NursingTask nursingTask = getNursingTask(elder, v, nursingIds, seconds, nursingProjectMap);
                // 将生成的任务添加到任务列表中
                nursingTasks.add(nursingTask);
            }
        }
    }

    /**
     获取护理任务对象

     @param elder
     @param v
     @param nursingIds
     @param seconds
     @return
     */
    private NursingTask getNursingTask(Elder elder, NursingProjectPlanVo v, String nursingIds, LocalDateTime seconds, Map<Long, String> nursingProjectMap) {
        NursingTask nursingTask = new NursingTask();
        // 设置任务状态
        nursingTask.setStatus(1);
        // 设置护理员ID列表
        nursingTask.setNursingId(nursingIds);
        // 老人姓名
        nursingTask.setElderName(elder.getName());
        // 设置床位号
        nursingTask.setBedNumber(elder.getBedNumber());

        // 设置预估服务时间 - 生成比当前时间大的随机时间
        LocalDateTime now = LocalDateTime.now();
        if (seconds.isBefore(now)) {
            // 如果原定时间早于当前时间，生成一个未来24小时内的随机时间
            Random random = new Random();
            // 生成1-24小时之间的随机分钟数
            int randomMinutes = random.nextInt(24 * 60) + 1;
            nursingTask.setEstimatedServerTime(now.plusMinutes(randomMinutes));
        } else {
            nursingTask.setEstimatedServerTime(seconds);
        }
        // 设置项目ID
        nursingTask.setProjectId(Long.valueOf(v.getProjectId()));
        // 设置老人ID
        nursingTask.setElderId(elder.getId());
        //匹配护理项目
        nursingTask.setProjectName(nursingProjectMap.get(nursingTask.getProjectId()));

        return nursingTask;
    }

    @Override
    public void cancel(CancelNursingTaskDto cancelNursingTaskDto) {
        NursingTask nursingTask = nursingTaskMapper.selectById(cancelNursingTaskDto.getTaskId());

        if (ObjectUtil.isNull(nursingTask)){
            throw new BaseException("任务不存在");
        }

        //修改状态 设置取消原因
        nursingTask.setStatus(3);
        nursingTask.setCancelReason(cancelNursingTaskDto.getReason());

        nursingTaskMapper.updateById(nursingTask);
    }

    @Override
    public void doTask(NursingTaskDto nursingTaskDto) {
        NursingTask nursingTask = nursingTaskMapper.selectById(nursingTaskDto.getTaskId());

        if (ObjectUtil.isNull(nursingTask)){
            throw new BaseException("任务不存在");
        }

        //修改状态 设置完成
        nursingTask.setStatus(2);
        nursingTask.setMark(nursingTaskDto.getMark());
        nursingTask.setTaskImage(nursingTaskDto.getTaskImage());
        nursingTask.setRealServerTime(LocalDateTime.parse(nursingTaskDto.getEstimatedServerTime()));
        nursingTaskMapper.updateById(nursingTask);
    }
}
