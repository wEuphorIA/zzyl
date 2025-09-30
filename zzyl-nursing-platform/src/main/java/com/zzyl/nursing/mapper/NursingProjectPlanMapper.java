package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.vo.NursingProjectPlanVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.NursingProjectPlan;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 护理计划和项目关联Mapper接口
 * 
 * @author alexis
 * @date 2024-12-31
 */
@Mapper
public interface NursingProjectPlanMapper extends BaseMapper<NursingProjectPlan>
{
    /**
     * 查询护理计划和项目关联
     * 
     * @param id 护理计划和项目关联主键
     * @return 护理计划和项目关联
     */
    public NursingProjectPlan selectNursingProjectPlanById(Long id);

    /**
     * 查询护理计划和项目关联列表
     * 
     * @param nursingProjectPlan 护理计划和项目关联
     * @return 护理计划和项目关联集合
     */
    public List<NursingProjectPlan> selectNursingProjectPlanList(NursingProjectPlan nursingProjectPlan);

    /**
     * 新增护理计划和项目关联
     * 
     * @param nursingProjectPlan 护理计划和项目关联
     * @return 结果
     */
    public int insertNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 修改护理计划和项目关联
     * 
     * @param nursingProjectPlan 护理计划和项目关联
     * @return 结果
     */
    public int updateNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 删除护理计划和项目关联
     * 
     * @param id 护理计划和项目关联主键
     * @return 结果
     */
    public int deleteNursingProjectPlanById(Long id);

    /**
     * 批量删除护理计划和项目关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNursingProjectPlanByIds(Long[] ids);

    /**
     * 批量保存护理计划关联的护理项目
     * @param projectPlans  护理计划关联的护理项目集合
     * @param planId    护理计划id
     * @return
     */
    int insertBatch(@Param("projectPlans") List<NursingProjectPlan> projectPlans, @Param("planId") Long planId);


    /**
     * 根据护理计划id查询关联的护理项目列表
     * @param planId
     * @return
     */
    @Select("select id, plan_id, project_id, execute_time, execute_cycle, execute_frequency, create_time, update_time, create_by, update_by, remark from nursing_project_plan where plan_id = #{planId}")
    List<NursingProjectPlanVo> getProjectListByPlanId(@Param("planId") Long planId);

    /**
     * 根据护理计划id删除关联的护理项目
     * @param planId    护理计划id
     */
    @Delete("delete from nursing_project_plan where plan_id = #{planId}")
    void deleteByPlanId(@Param("planId") Long planId);

    /**
     * 根据护理计划id集合批量删除关联的护理项目
     * @param ids  护理计划id集合
     */
    void batchDeleteByPlanIds(@Param("ids") List<Long> ids);
}
