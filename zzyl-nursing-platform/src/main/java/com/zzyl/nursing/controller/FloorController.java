package com.zzyl.nursing.controller;

import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.Floor;
import com.zzyl.nursing.service.IFloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 楼层Controller
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@RestController
@RequestMapping("/elder/floor")
@Api(tags =  "楼层相关接口")
public class FloorController extends BaseController
{
    @Autowired
    private IFloorService floorService;

    /**
     * 查询楼层列表
     */
    @PreAuthorize("@ss.hasPermi('elder:floor:list')")
    @GetMapping("/list")
    @ApiOperation("查询所有楼层列表")
    public R<List<Floor>> list()
    {
        List<Floor> list = floorService.list();
        return R.ok(list);
    }

    /**
     * 获取楼层详细信息
     */
    @PreAuthorize("@ss.hasPermi('elder:floor:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取楼层详细信息")
    public R<Floor> getInfo(@ApiParam(value = "楼层ID", required = true) @PathVariable("id") Long id)
    {
        return R.ok(floorService.selectFloorById(id));
    }

    /**
     * 新增楼层
     */
    @PreAuthorize("@ss.hasPermi('elder:floor:add')")
    @Log(title = "楼层", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增楼层")
    public AjaxResult add(@ApiParam(value = "楼层信息", required = true)  @RequestBody Floor floor)
    {
        return toAjax(floorService.insertFloor(floor));
    }

    /**
     * 修改楼层
     */
    @ApiOperation("修改楼层")
    @PreAuthorize("@ss.hasPermi('elder:floor:edit')")
    @Log(title = "楼层", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam(value = "楼层信息", required = true) @RequestBody Floor floor)
    {
        return toAjax(floorService.updateFloor(floor));
    }

    /**
     * 删除楼层
     */
    @ApiOperation("删除楼层")
    @PreAuthorize("@ss.hasPermi('elder:floor:remove')")
    @Log(title = "楼层", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(value = "楼层ID", required = true) @PathVariable Long[] ids)
    {
        return toAjax(floorService.deleteFloorByIds(ids));
    }

    @GetMapping("/getAllFloorsWithNur")
    @ApiOperation(value = "获取所有楼层 (负责老人)", notes = "无需参数，获取所有楼层，返回楼层信息列表")
    public R<List<Floor>> getAllFloorsWithNur() {
        List<Floor> list = floorService.selectAllByNur();
        return R.ok(list);
    }
}
