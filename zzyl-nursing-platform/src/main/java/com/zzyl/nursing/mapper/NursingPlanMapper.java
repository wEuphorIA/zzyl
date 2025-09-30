package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.NursingPlan;

/**
 * 护理计划Mapper接口
 * 
 * @author alexis
 * @date 2024-12-30
 */
@Mapper
public interface NursingPlanMapper extends BaseMapper<NursingPlan>
{
    /**
     * 查询护理计划
     * 
     * @param id 护理计划主键
     * @return 护理计划
     */
    public NursingPlan selectNursingPlanById(Long id);

    /**
     * 查询护理计划列表
     * 
     * @param nursingPlan 护理计划
     * @return 护理计划集合
     */
    public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan);

    /**
     * 新增护理计划
     * 
     * @param nursingPlan 护理计划
     * @return 结果
     */
    public int insertNursingPlan(NursingPlan nursingPlan);

    /**
     * 修改护理计划
     * 
     * @param nursingPlan 护理计划
     * @return 结果
     */
    public int updateNursingPlan(NursingPlan nursingPlan);

    /**
     * 删除护理计划
     * 
     * @param id 护理计划主键
     * @return 结果
     */
    public int deleteNursingPlanById(Long id);

    /**
     * 批量删除护理计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNursingPlanByIds(Long[] ids);
}
