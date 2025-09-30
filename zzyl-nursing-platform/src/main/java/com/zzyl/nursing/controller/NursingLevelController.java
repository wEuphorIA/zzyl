package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.vo.NursingLevelVo;
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
import com.zzyl.nursing.domain.NursingLevel;
import com.zzyl.nursing.service.INursingLevelService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理等级Controller
 * 
 * @author alexis
 * @date 2024-12-30
 */
@Api(tags = "护理等级管理")
@RestController
@RequestMapping("/nursing/level")
public class NursingLevelController extends BaseController
{
    @Autowired
    private INursingLevelService nursingLevelService;

    /**
     * 查询护理等级列表
     */
    @ApiOperation("查询护理等级列表")
    @PreAuthorize("@ss.hasPermi('nursing:level:list')")
    @GetMapping("/list")
    public TableDataInfo<List<NursingLevelVo>> list(@ApiParam("护理等级查询条件") NursingLevel nursingLevel)
    {
        startPage();
        List<NursingLevelVo> list = nursingLevelService.selectNursingLevelVoList(nursingLevel);
        return getDataTable(list);
    }

    /**
     * 导出护理等级列表
     */
    @ApiOperation("导出护理等级列表")
    @PreAuthorize("@ss.hasPermi('nursing:level:export')")
    @Log(title = "护理等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam(value = "护理等级查询条件") HttpServletResponse response, NursingLevel nursingLevel)
    {
        List<NursingLevel> list = nursingLevelService.selectNursingLevelList(nursingLevel);
        ExcelUtil<NursingLevel> util = new ExcelUtil<NursingLevel>(NursingLevel.class);
        util.exportExcel(response, list, "护理等级数据");
    }

    /**
     * 获取护理等级详细信息
     */
    @ApiOperation("获取护理等级详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:level:query')")
    @GetMapping(value = "/{id}")
    public R<NursingLevel> getInfo(@ApiParam("护理等级ID") @PathVariable("id") Long id)
    {
        return R.ok(nursingLevelService.selectNursingLevelById(id));
    }

    /**
     * 新增护理等级
     */
    @ApiOperation("新增护理等级")
    @PreAuthorize("@ss.hasPermi('nursing:level:add')")
    @Log(title = "护理等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("护理等级信息") @RequestBody NursingLevel nursingLevel)
    {
        return toAjax(nursingLevelService.insertNursingLevel(nursingLevel));
    }

    /**
     * 修改护理等级
     */
    @ApiOperation("修改护理等级")
    @PreAuthorize("@ss.hasPermi('nursing:level:edit')")
    @Log(title = "护理等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("护理等级信息") @RequestBody NursingLevel nursingLevel)
    {
        return toAjax(nursingLevelService.updateNursingLevel(nursingLevel));
    }

    /**
     * 删除护理等级
     */
    @ApiOperation("删除护理等级")
    @PreAuthorize("@ss.hasPermi('nursing:level:remove')")
    @Log(title = "护理等级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("护理等级ID数组") @PathVariable Long[] ids)
    {
        return toAjax(nursingLevelService.deleteNursingLevelByIds(ids));
    }
}
