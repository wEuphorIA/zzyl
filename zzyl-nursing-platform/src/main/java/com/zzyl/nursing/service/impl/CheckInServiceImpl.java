package com.zzyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.common.utils.DateUtils;
import com.zzyl.nursing.domain.*;
import com.zzyl.nursing.dto.CheckInApplyDto;
import com.zzyl.nursing.mapper.*;
import com.zzyl.nursing.util.CodeGenerator;
import com.zzyl.nursing.vo.CheckInConfigVo;
import com.zzyl.nursing.vo.CheckInDetailVo;
import com.zzyl.nursing.vo.CheckInElderVo;
import com.zzyl.nursing.vo.ElderFamilyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.service.ICheckInService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 入住Service业务层处理

 @author Euphoria
 @date 2025-09-30 */
@Service
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn> implements ICheckInService {

    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private CheckInConfigMapper checkInConfigMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private BedMapper bedMapper;

    /**
     查询入住

     @param id 入住主键
     @return 入住
     */
    @Override
    public CheckIn selectCheckInById(Long id) {
        return checkInMapper.selectById(id);
    }

    /**
     查询入住列表

     @param checkIn 入住
     @return 入住
     */
    @Override
    public List<CheckIn> selectCheckInList(CheckIn checkIn) {
        return checkInMapper.selectCheckInList(checkIn);
    }

    /**
     新增入住

     @param checkIn 入住
     @return 结果
     */
    @Override
    public int insertCheckIn(CheckIn checkIn) {
        return checkInMapper.insert(checkIn);
    }

    /**
     修改入住

     @param checkIn 入住
     @return 结果
     */
    @Override
    public int updateCheckIn(CheckIn checkIn) {
        return checkInMapper.updateById(checkIn);
    }

    /**
     批量删除入住

     @param ids 需要删除的入住主键
     @return 结果
     */
    @Override
    public int deleteCheckInByIds(Long[] ids) {
        return checkInMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     删除入住信息

     @param id 入住主键
     @return 结果
     */
    @Override
    public int deleteCheckInById(Long id) {
        return checkInMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void apply(CheckInApplyDto checkInApplyDto) {

        // 检验老人是否已入住

        Elder checkElder = elderMapper.selectOne(new LambdaQueryWrapper<Elder>()
                .eq(Elder::getIdCardNo, checkInApplyDto.getCheckInElderDto().getIdCardNo())
                .eq(Elder::getStatus, 1));

        if (ObjectUtil.isNotNull(checkElder)) {
            throw new RuntimeException("该老人已入住，不需要重复办理");
        }

        // 更新床位状态为已入住
        Bed bed = bedMapper.selectById(checkInApplyDto.getCheckInConfigDto().getBedId());
        bed.setBedStatus(1);
        bedMapper.updateBed(bed);

        Elder elder = new Elder();
        BeanUtils.copyProperties(checkInApplyDto.getCheckInElderDto(), elder);
        elder.setBedNumber(bed.getBedNumber());
        elder.setBedId(bed.getId());

        // 新增或更新老人
        Elder elderDb = elderMapper.selectOne(new LambdaQueryWrapper<Elder>()
                .eq(Elder::getIdCardNo, checkInApplyDto.getCheckInElderDto().getIdCardNo())
                .ne(Elder::getStatus, 1));
        if (ObjectUtil.isNotNull(elderDb)) {
            //修改
            elder.setId(elderDb.getId());
            elderMapper.updateElder(elder);
        } else {
            //新增
            elderMapper.insert(elder);
        }

        // 新增签约办理
        Contract contract = new Contract();
        BeanUtils.copyProperties(checkInApplyDto.getCheckInContractDto(), contract);
        contract.setElderId(elder.getId().intValue());
        contract.setContractNumber("HT" + CodeGenerator.generateContractNumber());
        contract.setElderName(elder.getName());
        contract.setStartDate(checkInApplyDto.getCheckInConfigDto().getStartDate());
        contract.setEndDate(checkInApplyDto.getCheckInConfigDto().getEndDate());
        //如果当前时间在开始时间之后设置状态为1
        if (LocalDateTime.now().isAfter(checkInApplyDto.getCheckInConfigDto().getStartDate())) {
            contract.setStatus(1);
        }
        contractMapper.insert(contract);

        // 新增入住信息
        CheckIn checkIn = new CheckIn();
        checkIn.setElderId(elder.getId());
        checkIn.setElderName(elder.getName());
        checkIn.setIdCardNo(elder.getIdCardNo());
        checkIn.setStartDate(checkInApplyDto.getCheckInConfigDto().getStartDate());
        checkIn.setEndDate(checkInApplyDto.getCheckInConfigDto().getEndDate());
        checkIn.setNursingLevelName(checkInApplyDto.getCheckInConfigDto().getNursingLevelName());
        checkIn.setBedNumber(bed.getBedNumber());
        checkIn.setRemark(JSON.toJSONString(checkInApplyDto.getElderFamilyDtoList()));

        checkInMapper.insert(checkIn);

        // 新增入住配置
        CheckInConfig checkInConfig = new CheckInConfig();
        BeanUtils.copyProperties(checkInApplyDto.getCheckInConfigDto(), checkInConfig);
        checkInConfig.setCheckInId(checkIn.getId());
        checkInConfigMapper.insert(checkInConfig);

    }

    @Override
    public CheckInDetailVo detail(Long id) {

        CheckInDetailVo checkInDetailVo = new CheckInDetailVo();

        // 查询入住信息// 查询入住配置
        CheckIn checkIn = checkInMapper.selectById(id);
        if (ObjectUtil.isNull(checkIn)) {
            throw new RuntimeException("该老人不存在");
        }
        CheckInConfig checkInConfig = checkInConfigMapper.selectOne(
                new LambdaQueryWrapper<CheckInConfig>()
                        .eq(CheckInConfig::getCheckInId, id));

        CheckInConfigVo checkInConfigVo = new CheckInConfigVo();
        checkInConfigVo.setStartDate(checkIn.getStartDate());
        checkInConfigVo.setEndDate(checkIn.getEndDate());
        checkInConfigVo.setBedNumber(checkIn.getBedNumber());
        BeanUtils.copyProperties(checkInConfig, checkInConfigVo);

        // 查询老人信息
        Elder elder = elderMapper.selectOne(new LambdaQueryWrapper<Elder>()
                .eq(Elder::getId, checkIn.getElderId()));

        CheckInElderVo checkInElderVo = new CheckInElderVo();
        BeanUtils.copyProperties(elder, checkInElderVo);

        // 查询合同
        Contract contract = contractMapper.selectOne(
                new LambdaQueryWrapper<Contract>()
                        .eq(Contract::getElderId, checkIn.getElderId()));

        // 转换家属列表
        Object parse = JSON.parse(checkIn.getRemark());
        List<ElderFamilyVo> elderFamilyVoList = JSON.parseArray(parse.toString(), ElderFamilyVo.class);
        checkInDetailVo.setElderFamilyVoList(elderFamilyVoList);
        // 整合数据返回
        checkInDetailVo.setContract( contract);
        checkInDetailVo.setCheckInConfigVo(checkInConfigVo);
        checkInDetailVo.setCheckInElderVo(checkInElderVo);
        return checkInDetailVo;
    }

}
