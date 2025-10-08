package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.HealthAssessment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzyl.nursing.dto.HealthAssessmentDto;

/**
 * 健康评估Service接口
 * 
 * @author ruoyi
 * @date 2024-10-10
 */
public interface IHealthAssessmentService extends IService<HealthAssessment>
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
     * @param healthAssessmentDto 健康评估
     * @return 结果
     */
    public Long insertHealthAssessment(HealthAssessmentDto healthAssessmentDto);

    /**
     * 修改健康评估
     * 
     * @param healthAssessment 健康评估
     * @return 结果
     */
    public int updateHealthAssessment(HealthAssessment healthAssessment);

    /**
     * 批量删除健康评估
     * 
     * @param ids 需要删除的健康评估主键集合
     * @return 结果
     */
    public int deleteHealthAssessmentByIds(Long[] ids);

    /**
     * 删除健康评估信息
     * 
     * @param id 健康评估主键
     * @return 结果
     */
    public int deleteHealthAssessmentById(Long id);
}
