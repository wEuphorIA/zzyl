package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.AlertRule;
import com.zzyl.nursing.service.IAlertRuleService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 报警规则Controller
 * 
 * @author Euphoria
 * @date 2025-10-16
 */
@Api(tags = "报警规则管理")
@RestController
@RequestMapping("/nursing/alertRule")
public class AlertRuleController extends BaseController
{
    @Autowired
    private IAlertRuleService alertRuleService;

    /**
     * 查询报警规则列表
     */
    @ApiOperation("查询报警规则列表")
    @PreAuthorize("@ss.hasPermi('nursing:alertRule:list')")
    @GetMapping("/list")
    public TableDataInfo<List<AlertRule>> list(@ApiParam("报警规则查询条件") AlertRule alertRule)
    {
        startPage();
        List<AlertRule> list = alertRuleService.selectAlertRuleList(alertRule);
        return getDataTable(list);
    }

    /**
     * 导出报警规则列表
     */
    @ApiOperation("导出报警规则列表")
    @PreAuthorize("@ss.hasPermi('nursing:alertRule:export')")
    @Log(title = "报警规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam(value = "报警规则查询条件") HttpServletResponse response, AlertRule alertRule)
    {
        List<AlertRule> list = alertRuleService.selectAlertRuleList(alertRule);
        ExcelUtil<AlertRule> util = new ExcelUtil<AlertRule>(AlertRule.class);
        util.exportExcel(response, list, "报警规则数据");
    }

    /**
     * 获取报警规则详细信息
     */
    @ApiOperation("获取报警规则详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:alertRule:query')")
    @GetMapping(value = "/{id}")
    public R<AlertRule> getInfo(@ApiParam("报警规则ID") @PathVariable("id") Long id)
    {
        return R.ok(alertRuleService.selectAlertRuleById(id));
    }

    /**
     * 新增报警规则
     */
    @ApiOperation("新增报警规则")
    @PreAuthorize("@ss.hasPermi('nursing:alertRule:add')")
    @Log(title = "报警规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("报警规则信息") @RequestBody AlertRule alertRule)
    {
        return toAjax(alertRuleService.insertAlertRule(alertRule));
    }

    /**
     * 修改报警规则
     */
    @ApiOperation("修改报警规则")
    @PreAuthorize("@ss.hasPermi('nursing:alertRule:edit')")
    @Log(title = "报警规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("报警规则信息") @RequestBody AlertRule alertRule)
    {
        return toAjax(alertRuleService.updateAlertRule(alertRule));
    }

    /**
     * 删除报警规则
     */
    @ApiOperation("删除报警规则")
    @PreAuthorize("@ss.hasPermi('nursing:alertRule:remove')")
    @Log(title = "报警规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("报警规则ID数组") @PathVariable Long[] ids)
    {
        return toAjax(alertRuleService.deleteAlertRuleByIds(ids));
    }
}
