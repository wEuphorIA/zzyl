package com.zzyl.plan.service.impl;

import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.plan.mapper.NursingPlanMapper;
import com.zzyl.plan.domain.NursingPlan;
import com.zzyl.plan.service.INursingPlanService;

/**
 * 护理计划Service业务层处理
 * 
 * @author Euphoria
 * @date 2025-09-26
 */
@Service
public class NursingPlanServiceImpl implements INursingPlanService 
{
    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    /**
     * 查询护理计划
     * 
     * @param id 护理计划主键
     * @return 护理计划
     */
    @Override
    public NursingPlan selectNursingPlanById(Long id)
    {
        return nursingPlanMapper.selectNursingPlanById(id);
    }

    /**
     * 查询护理计划列表
     * 
     * @param nursingPlan 护理计划
     * @return 护理计划
     */
    @Override
    public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan)
    {
        return nursingPlanMapper.selectNursingPlanList(nursingPlan);
    }

    /**
     * 新增护理计划
     * 
     * @param nursingPlan 护理计划
     * @return 结果
     */
    @Override
    public int insertNursingPlan(NursingPlan nursingPlan)
    {
        nursingPlan.setCreateTime(DateUtils.getNowDate());
        return nursingPlanMapper.insertNursingPlan(nursingPlan);
    }

    /**
     * 修改护理计划
     * 
     * @param nursingPlan 护理计划
     * @return 结果
     */
    @Override
    public int updateNursingPlan(NursingPlan nursingPlan)
    {
        nursingPlan.setUpdateTime(DateUtils.getNowDate());
        return nursingPlanMapper.updateNursingPlan(nursingPlan);
    }

    /**
     * 批量删除护理计划
     * 
     * @param ids 需要删除的护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanByIds(Long[] ids)
    {
        return nursingPlanMapper.deleteNursingPlanByIds(ids);
    }

    /**
     * 删除护理计划信息
     * 
     * @param id 护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanById(Long id)
    {
        return nursingPlanMapper.deleteNursingPlanById(id);
    }
}
