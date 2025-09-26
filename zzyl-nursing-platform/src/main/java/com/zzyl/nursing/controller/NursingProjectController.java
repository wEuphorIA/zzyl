package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
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
import com.zzyl.nursing.domain.NursingProject;
import com.zzyl.nursing.service.INursingProjectService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 护理项目Controller
 * 
 * @author ruoyi
 * @date 2025-09-26
 */
@Api(tags = "护理项目管理")
@RestController
@RequestMapping("/nursing/project")
public class NursingProjectController extends BaseController
{
    @Autowired
    private INursingProjectService nursingProjectService;

    /**
     查询护理项目列表
     */
    @ApiOperation("查询护理项目列表")
    @PreAuthorize("@ss.hasPermi('nursing:project:list')")
    @GetMapping("/list")
    public TableDataInfo<NursingProject> list(@ApiParam("护理项目查询参数") NursingProject nursingProject)
    {
        startPage();
        List<NursingProject> list = nursingProjectService.selectNursingProjectList(nursingProject);
        return getDataTable(list);
    }

    /**
     * 导出护理项目列表
     */
    @ApiOperation("导出护理项目列表")
    @PreAuthorize("@ss.hasPermi('nursing:project:export')")
    @Log(title = "护理项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam("HTTP响应对象") HttpServletResponse response, 
                       @ApiParam("护理项目查询参数") NursingProject nursingProject)
    {
        List<NursingProject> list = nursingProjectService.selectNursingProjectList(nursingProject);
        ExcelUtil<NursingProject> util = new ExcelUtil<NursingProject>(NursingProject.class);
        util.exportExcel(response, list, "护理项目数据");
    }

    /**
     * 获取护理项目详细信息
     */
    @ApiOperation("获取护理项目详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:project:query')")
    @GetMapping(value = "/{id}")
    public R<NursingProject> getInfo(@ApiParam("护理项目ID") @PathVariable("id") Long id)
    {
        NursingProject nursingProject = nursingProjectService.selectNursingProjectById(id);
        return R.ok(nursingProject);
    }

    /**
     * 新增护理项目
     */
    @ApiOperation("新增护理项目")
    @PreAuthorize("@ss.hasPermi('nursing:project:add')")
    @Log(title = "护理项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("护理项目信息") @RequestBody NursingProject nursingProject)
    {
        return toAjax(nursingProjectService.insertNursingProject(nursingProject));
    }

    /**
     * 修改护理项目
     */
    @ApiOperation("修改护理项目")
    @PreAuthorize("@ss.hasPermi('nursing:project:edit')")
    @Log(title = "护理项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("护理项目信息") @RequestBody NursingProject nursingProject)
    {
        return toAjax(nursingProjectService.updateNursingProject(nursingProject));
    }

    /**
     * 删除护理项目
     */
    @ApiOperation("删除护理项目")
    @PreAuthorize("@ss.hasPermi('nursing:project:remove')")
    @Log(title = "护理项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("护理项目ID数组") @PathVariable Long[] ids)
    {
        return toAjax(nursingProjectService.deleteNursingProjectByIds(ids));
    }
}
