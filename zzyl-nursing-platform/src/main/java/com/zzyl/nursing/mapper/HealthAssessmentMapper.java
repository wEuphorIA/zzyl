package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.HealthAssessment;

/**
 * 健康评估Mapper接口
 * 
 * @author ruoyi
 * @date 2024-10-10
 */
@Mapper
public interface HealthAssessmentMapper extends BaseMapper<HealthAssessment>
{
    /**
     * 查询健康评估
     * 
     * @param id 健康评估主键
     * @return 健康评估
     */
    public HealthAssessment selectHealthAssessmentById(Long id);

    /**
     * 查询健康评估列表
     * 
     * @param healthAssessment 健康评估
     * @return 健康评估集合
     */
    public List<HealthAssessment> selectHealthAssessmentList(HealthAssessment healthAssessment);

    /**
     * 新增健康评估
     * 
     * @param healthAssessment 健康评估
     * @return 结果
     */
    public int insertHealthAssessment(HealthAssessment healthAssessment);

    /**
     * 修改健康评估
     * 
     * @param healthAssessment 健康评估
     * @return 结果
     */
    public int updateHealthAssessment(HealthAssessment healthAssessment);

    /**
     * 删除健康评估
     * 
     * @param id 健康评估主键
     * @return 结果
     */
    public int deleteHealthAssessmentById(Long id);

    /**
     * 批量删除健康评估
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHealthAssessmentByIds(Long[] ids);
}
