package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.AlertRule;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 报警规则Service接口
 * 
 * @author Euphoria
 * @date 2025-10-16
 */
public interface IAlertRuleService extends IService<AlertRule>
{
    /**
     * 查询报警规则
     * 
     * @param id 报警规则主键
     * @return 报警规则
     */
    public AlertRule selectAlertRuleById(Long id);

    /**
     * 查询报警规则列表
     * 
     * @param alertRule 报警规则
     * @return 报警规则集合
     */
    public List<AlertRule> selectAlertRuleList(AlertRule alertRule);

    /**
     * 新增报警规则
     * 
     * @param alertRule 报警规则
     * @return 结果
     */
    public int insertAlertRule(AlertRule alertRule);

    /**
     * 修改报警规则
     * 
     * @param alertRule 报警规则
     * @return 结果
     */
    public int updateAlertRule(AlertRule alertRule);

    /**
     * 批量删除报警规则
     * 
     * @param ids 需要删除的报警规则主键集合
     * @return 结果
     */
    public int deleteAlertRuleByIds(Long[] ids);

    /**
     * 删除报警规则信息
     * 
     * @param id 报警规则主键
     * @return 结果
     */
    public int deleteAlertRuleById(Long id);

    void alertFilter();
}
