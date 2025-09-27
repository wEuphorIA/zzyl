package com.zzyl.nursing.service.impl;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.NursingPlanMapper;
import com.zzyl.nursing.domain.NursingPlan;
import com.zzyl.nursing.service.INursingPlanService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



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

    /**
     * 查询护理计划
     *
     * @param id 护理计划主键
     * @return 护理计划
     */
    @Override
    public NursingPlan selectNursingPlanById(Long id) {
        return getById(id);
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
     * @param nursingPlan 护理计划
     * @return 结果
     */
    @Override
    public int insertNursingPlan(NursingPlan nursingPlan) {
        return save(nursingPlan) ? 1 : 0;
    }

    /**
     * 修改护理计划
     *
     * @param nursingPlan 护理计划
     * @return 结果
     */
    @Override
    public int updateNursingPlan(NursingPlan nursingPlan) {
        return updateById(nursingPlan) ? 1 : 0;
    }

    /**
     * 批量删除护理计划
     *
     * @param ids 需要删除的护理计划主键
     * @return 结果
     */
    @Override
    public int deleteNursingPlanByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
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
