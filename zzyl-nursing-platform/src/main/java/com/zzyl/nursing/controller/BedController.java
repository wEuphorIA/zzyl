package com.zzyl.nursing.controller;

import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.Bed;
import com.zzyl.nursing.service.IBedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 床位Controller
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@RestController
@RequestMapping("/elder/bed")
@Api(tags =  "床位相关接口")
public class BedController extends BaseController
{
    @Autowired
    private IBedService bedService;

    /**
     * 查询床位列表
     */
    @PreAuthorize("@ss.hasPermi('elder:bed:list')")
    @GetMapping("/list")
    @ApiOperation("查询床位列表")
    public TableDataInfo list(Bed bed)
    {
        startPage();
        List<Bed> list = bedService.selectBedList(bed);
        return getDataTable(list);
    }

    /**
     * 获取床位详细信息
     */
    @PreAuthorize("@ss.hasPermi('elder:bed:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取床位详细信息")
    public R<Bed> getInfo(@ApiParam(value = "床位ID", required = true)  @PathVariable("id") Long id)
    {
        return R.ok(bedService.selectBedById(id));
    }

    /**
     * 新增床位
     */
    @PreAuthorize("@ss.hasPermi('elder:bed:add')")
    @Log(title = "床位", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增床位")
    public AjaxResult add(@RequestBody Bed bed)
    {
        return toAjax(bedService.insertBed(bed));
    }

    /**
     * 修改床位
     */
    @ApiOperation("修改床位")
    @PreAuthorize("@ss.hasPermi('elder:bed:edit')")
    @Log(title = "床位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Bed bed)
    {
        return toAjax(bedService.updateBed(bed));
    }

    /**
     * 删除床位
     */
    @ApiOperation("删除床位")
    @PreAuthorize("@ss.hasPermi('elder:bed:remove')")
    @Log(title = "床位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(value = "床位ID", required = true)  @PathVariable Long[] ids)
    {
        return toAjax(bedService.deleteBedByIds(ids));
    }
}
