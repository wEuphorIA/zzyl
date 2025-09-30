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
import com.zzyl.nursing.domain.CheckInConfig;
import com.zzyl.nursing.service.ICheckInConfigService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 入住配置Controller
 * 
 * @author Euphoria
 * @date 2025-09-30
 */
@Api(tags = "入住配置管理")
@RestController
@RequestMapping("/nursing/config")
public class CheckInConfigController extends BaseController
{
    @Autowired
    private ICheckInConfigService checkInConfigService;

    /**
     * 查询入住配置列表
     */
    @ApiOperation("查询入住配置列表")
    @PreAuthorize("@ss.hasPermi('nursing:config:list')")
    @GetMapping("/list")
    public TableDataInfo<List<CheckInConfig>> list(@ApiParam("入住配置查询条件") CheckInConfig checkInConfig)
    {
        startPage();
        List<CheckInConfig> list = checkInConfigService.selectCheckInConfigList(checkInConfig);
        return getDataTable(list);
    }

    /**
     * 导出入住配置列表
     */
    @ApiOperation("导出入住配置列表")
    @PreAuthorize("@ss.hasPermi('nursing:config:export')")
    @Log(title = "入住配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam(value = "入住配置查询条件") HttpServletResponse response, CheckInConfig checkInConfig)
    {
        List<CheckInConfig> list = checkInConfigService.selectCheckInConfigList(checkInConfig);
        ExcelUtil<CheckInConfig> util = new ExcelUtil<CheckInConfig>(CheckInConfig.class);
        util.exportExcel(response, list, "入住配置数据");
    }

    /**
     * 获取入住配置详细信息
     */
    @ApiOperation("获取入住配置详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:config:query')")
    @GetMapping(value = "/{id}")
    public R<CheckInConfig> getInfo(@ApiParam("入住配置ID") @PathVariable("id") Long id)
    {
        return R.ok(checkInConfigService.selectCheckInConfigById(id));
    }

    /**
     * 新增入住配置
     */
    @ApiOperation("新增入住配置")
    @PreAuthorize("@ss.hasPermi('nursing:config:add')")
    @Log(title = "入住配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("入住配置信息") @RequestBody CheckInConfig checkInConfig)
    {
        return toAjax(checkInConfigService.insertCheckInConfig(checkInConfig));
    }

    /**
     * 修改入住配置
     */
    @ApiOperation("修改入住配置")
    @PreAuthorize("@ss.hasPermi('nursing:config:edit')")
    @Log(title = "入住配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("入住配置信息") @RequestBody CheckInConfig checkInConfig)
    {
        return toAjax(checkInConfigService.updateCheckInConfig(checkInConfig));
    }

    /**
     * 删除入住配置
     */
    @ApiOperation("删除入住配置")
    @PreAuthorize("@ss.hasPermi('nursing:config:remove')")
    @Log(title = "入住配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("入住配置ID数组") @PathVariable Long[] ids)
    {
        return toAjax(checkInConfigService.deleteCheckInConfigByIds(ids));
    }
}
