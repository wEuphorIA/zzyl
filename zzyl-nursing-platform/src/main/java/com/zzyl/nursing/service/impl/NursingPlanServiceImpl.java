package com.zzyl.nursing.service.impl;

import java.beans.Transient;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.common.utils.bean.BeanUtils;
import com.zzyl.nursing.domain.NursingProjectPlan;
import com.zzyl.nursing.dto.NursingPlanDto;
import com.zzyl.nursing.mapper.NursingProjectPlanMapper;
import com.zzyl.nursing.vo.NursingPlanVo;
import com.zzyl.nursing.vo.NursingProjectPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.NursingPlanMapper;
import com.zzyl.nursing.domain.NursingPlan;
import com.zzyl.nursing.service.INursingPlanService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


/**
 * 护理计划Service业务层处理
 *
 * @author Euphoria
 * @date 2025-09-27
 */
@Service
public class NursingPlanServiceImpl extends ServiceImpl<NursingPlanMapper, NursingPlan> implements INursingPlanService {

    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    @Autowired
    private NursingProjectPlanMapper nursingProjectPlanMapper;

    /**
     * 查询护理计划
     *
     * @param id 护理计划主键
     * @return 护理计划
     */
    @Override
    public NursingPlanVo selectNursingPlanById(Long id) {

        NursingPlan nursingPlan = getById(id);

        NursingPlanVo nursingPlanVo = new NursingPlanVo();

        BeanUtils.copyProperties(nursingPlan, nursingPlanVo);

        // 2.根据护理计划id查询关联的所有护理项目
        List<NursingProjectPlanVo> list = nursingProjectPlanMapper.selectByPlanId(id);

        nursingPlanVo.setProjectPlans(list);

        return nursingPlanVo;
    }

    /**
     * 查询护理计划列表
     *
     * @param nursingPlan 护理计划
     * @return 护理计划
     */
    @Override
    public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan) {
        return nursingPlanMapper.selectNursingPlanList(nursingPlan);
    }

    /**
     * 新增护理计划
     *
     * @param nursingPlanDto 护理计划
     * @return 结果
     */
    @Override
    @Transactional
    public int insertNursingPlan(NursingPlanDto nursingPlanDto) {


        // 保存基本信息
        NursingPlan nursingPlan = new NursingPlan();
        BeanUtils.copyBeanProp(nursingPlan, nursingPlanDto);

        boolean saveResult = save(nursingPlan);
        // 保存护理项目
        List<NursingProjectPlan> projectPlans = nursingPlanDto.getProjectPlans();
        if (!CollectionUtils.isEmpty(projectPlans)){
            projectPlans.forEach(item -> item.setPlanId(nursingPlan.getId()));

            nursingProjectPlanMapper.insertBatch(projectPlans);
        }
        return saveResult ? 1 : 0;
    }

    /**
     * 修改护理计划
     *
     * @param nursingPlanDto 护理计划
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNursingPlan(NursingPlanDto nursingPlanDto) {

        // 修改基本信息
        NursingPlan nursingPlan = new NursingPlan();
        BeanUtils.copyBeanProp(nursingPlan, nursingPlanDto);

        boolean updateResult = updateById(nursingPlan);

        // 删除护理项目
        LambdaQueryWrapper<NursingProjectPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NursingProjectPlan::getPlanId, nursingPlan.getId());

        nursingProjectPlanMapper.delete(queryWrapper);

        // 保存护理项目
        List<NursingProjectPlan> projectPlans = nursingPlanDto.getProjectPlans();
        if (!CollectionUtils.isEmpty(projectPlans)){
            projectPlans.forEach(item -> item.setPlanId(nursingPlan.getId()));

            nursingProjectPlanMapper.insertBatch(projectPlans);
        }
        return updateResult ? 1 : 0;
    }

    /**
     * 批量删除护理计划
     *
     * @param id 需要删除的护理计划主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteNursingPlanByIds(Long id) {

        boolean removeReslt = removeById(id);

        LambdaQueryWrapper<NursingProjectPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NursingProjectPlan::getPlanId, id);

        nursingProjectPlanMapper.delete(queryWrapper);

        return removeReslt ? 1 : 0;
    }

    /**
     * 删除护理计划信息
     *
     * @param id 护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}
