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
import com.zzyl.nursing.domain.Elder;
import com.zzyl.nursing.service.IElderService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 老人Controller
 * 
 * @author Euphoria
 * @date 2025-09-30
 */
@Api(tags = "老人管理")
@RestController
@RequestMapping("/nursing/elder")
public class ElderController extends BaseController
{
    @Autowired
    private IElderService elderService;

    /**
     * 查询老人列表
     */
    @ApiOperation("查询老人列表")
    @PreAuthorize("@ss.hasPermi('nursing:elder:list')")
    @GetMapping("/list")
    public TableDataInfo<List<Elder>> list(@ApiParam("老人查询条件") Elder elder)
    {
        startPage();
        List<Elder> list = elderService.selectElderList(elder);
        return getDataTable(list);
    }

    /**
     * 导出老人列表
     */
    @ApiOperation("导出老人列表")
    @PreAuthorize("@ss.hasPermi('nursing:elder:export')")
    @Log(title = "老人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam(value = "老人查询条件") HttpServletResponse response, Elder elder)
    {
        List<Elder> list = elderService.selectElderList(elder);
        ExcelUtil<Elder> util = new ExcelUtil<Elder>(Elder.class);
        util.exportExcel(response, list, "老人数据");
    }

    /**
     * 获取老人详细信息
     */
    @ApiOperation("获取老人详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:elder:query')")
    @GetMapping(value = "/{id}")
    public R<Elder> getInfo(@ApiParam("老人ID") @PathVariable("id") Long id)
    {
        return R.ok(elderService.selectElderById(id));
    }

    /**
     * 新增老人
     */
    @ApiOperation("新增老人")
    @PreAuthorize("@ss.hasPermi('nursing:elder:add')")
    @Log(title = "老人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("老人信息") @RequestBody Elder elder)
    {
        return toAjax(elderService.insertElder(elder));
    }

    /**
     * 修改老人
     */
    @ApiOperation("修改老人")
    @PreAuthorize("@ss.hasPermi('nursing:elder:edit')")
    @Log(title = "老人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("老人信息") @RequestBody Elder elder)
    {
        return toAjax(elderService.updateElder(elder));
    }

    /**
     * 删除老人
     */
    @ApiOperation("删除老人")
    @PreAuthorize("@ss.hasPermi('nursing:elder:remove')")
    @Log(title = "老人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("老人ID数组") @PathVariable Long[] ids)
    {
        return toAjax(elderService.deleteElderByIds(ids));
    }
}
