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
import com.zzyl.nursing.domain.NursingTask;
import com.zzyl.nursing.service.INursingTaskService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理任务Controller
 * 
 * @author alexis
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/nursing/task")
@Api(tags = "护理任务管理")
public class NursingTaskController extends BaseController
{
    @Autowired
    private INursingTaskService nursingTaskService;

    /**
     * 查询护理任务列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取护理任务列表")
    public TableDataInfo<List<NursingTask>> list(@ApiParam(value = "护理任务查询条件") NursingTask nursingTask)
    {
        startPage();
        List<NursingTask> list = nursingTaskService.selectNursingTaskList(nursingTask);
        return getDataTable(list);
    }

    /**
     * 导出护理任务列表
     */
    @Log(title = "护理任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出护理任务列表")
    public void export(HttpServletResponse response, @ApiParam(value = "护理任务查询条件") NursingTask nursingTask)
    {
        List<NursingTask> list = nursingTaskService.selectNursingTaskList(nursingTask);
        ExcelUtil<NursingTask> util = new ExcelUtil<NursingTask>(NursingTask.class);
        util.exportExcel(response, list, "护理任务数据");
    }

    /**
     * 获取护理任务详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取护理任务详细信息")
    public R<NursingTask> getInfo(@ApiParam(value = "护理任务ID", required = true) @PathVariable("id") Long id)
    {
        return R.ok(nursingTaskService.selectNursingTaskById(id));
    }

    /**
     * 新增护理任务
     */
    @Log(title = "护理任务", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增护理任务")
    public AjaxResult add(@ApiParam(value = "护理任务实体", required = true) @RequestBody NursingTask nursingTask)
    {
        return toAjax(nursingTaskService.insertNursingTask(nursingTask));
    }

    /**
     * 修改护理任务
     */
    @Log(title = "护理任务", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改护理任务")
    public AjaxResult edit(@ApiParam(value = "护理任务实体", required = true) @RequestBody NursingTask nursingTask)
    {
        return toAjax(nursingTaskService.updateNursingTask(nursingTask));
    }

    /**
     * 删除护理任务
     */
    @Log(title = "护理任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除护理任务")
    public AjaxResult remove(@ApiParam(value = "护理任务ID数组", required = true) @PathVariable Long[] ids)
    {
        return toAjax(nursingTaskService.deleteNursingTaskByIds(ids));
    }
}
