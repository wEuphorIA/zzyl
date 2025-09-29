package com.zzyl.nursing.controller;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.NursingPlanDto;
import com.zzyl.nursing.vo.NursingPlanVo;
import com.zzyl.nursing.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.zzyl.nursing.domain.NursingPlan;
import com.zzyl.nursing.service.INursingPlanService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理计划Controller
 *
 * @author Euphoria
 * @date 2025-09-27
 */
@RestController
@RequestMapping("/nursing/plan" )
@Api(tags = "护理计划相关接口" )
public class NursingPlanController extends BaseController {
    @Autowired
    private INursingPlanService nursingPlanService;

    /**
     * 查询护理计划列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:list')" )
    @GetMapping("/list" )
    @ApiOperation("查询护理计划列表" )
    public TableDataInfo<NursingPlan> list(@ApiParam(value = "护理计划查询条件" ) NursingPlan nursingPlan) {
        startPage();
        List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
        return getDataTable(list);
    }

    /**
     * 导出护理计划列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:export')" )
    @Log(title = "护理计划" , businessType = BusinessType.EXPORT)
    @PostMapping("/export" )
    @ApiOperation("导出护理计划列表" )
    public void export(HttpServletResponse response, @ApiParam(value = "护理计划查询条件" ) NursingPlan nursingPlan) {
        List<NursingPlan> list = nursingPlanService.selectNursingPlanList(nursingPlan);
        ExcelUtil<NursingPlan> util = new ExcelUtil<NursingPlan>(NursingPlan. class);
        util.exportExcel(response, list, "护理计划数据" );
    }

    /**
     * 获取护理计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:query')" )
    @GetMapping(value = "/{id}" )
    @ApiOperation("获取护理计划详细信息" )
    public R<NursingPlanVo> getInfo(@ApiParam(value = "护理计划ID" , required = true)
                                  @PathVariable("id" ) Long id) {
        return R.ok(nursingPlanService.selectNursingPlanById(id));
    }

    /**
     * 新增护理计划
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:add')" )
    @Log(title = "护理计划" , businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增护理计划" )
    public AjaxResult add(@ApiParam(value = "护理计划实体" , required = true) @RequestBody NursingPlanDto nursingPlanDto) {
        return toAjax(nursingPlanService.insertNursingPlan(nursingPlanDto));
    }

    /**
     * 修改护理计划
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:edit')" )
    @Log(title = "护理计划" , businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改护理计划" )
    public AjaxResult edit(@ApiParam(value = "护理计划实体" , required = true) @RequestBody NursingPlanDto nursingPlanDto) {
        return toAjax(nursingPlanService.updateNursingPlan(nursingPlanDto));
    }

    /**
     * 删除护理计划
     */
    @PreAuthorize("@ss.hasPermi('nursing:plan:remove')" )
    @Log(title = "护理计划" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}" )
    @ApiOperation("删除护理计划" )
    public AjaxResult remove(@ApiParam(value = "护理计划ID数组" , required = true) @PathVariable Long id) {
        return toAjax(nursingPlanService.deleteNursingPlanByIds(id));
    }

    @GetMapping("/all")
    @ApiOperation("查询全部护理计划")
    public R<List<NursingPlan>> all(){
        List<NursingPlan> result = nursingPlanService.list();
        return R.ok(result);
    }
}