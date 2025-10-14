package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.DeviceDataPageReqDto;
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
import com.zzyl.nursing.domain.DeviceData;
import com.zzyl.nursing.service.IDeviceDataService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 设备数据Controller

 @author ruoyi
 @date 2025-10-14 */
@Api(tags = "设备数据管理")
@RestController
@RequestMapping("/nursing/data")
public class DeviceDataController extends BaseController {

    @Autowired
    private IDeviceDataService deviceDataService;

    /**
     查询设备数据列表
     */
    @ApiOperation("查询设备数据列表")
    @PreAuthorize("@ss.hasPermi('nursing:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(@ApiParam("设备数据查询条件") DeviceDataPageReqDto deviceDataPageReqDto) {
        return deviceDataService.selectDeviceDataList(deviceDataPageReqDto);
    }



    /**
     获取设备数据详细信息
     */
    @ApiOperation("获取设备数据详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:data:query')")
    @GetMapping(value = "/{id}")
    public R<DeviceData> getInfo(@ApiParam("设备数据ID") @PathVariable("id") Long id) {
        return R.ok(deviceDataService.selectDeviceDataById(id));
    }

    /**
     新增设备数据
     */
    @ApiOperation("新增设备数据")
    @PreAuthorize("@ss.hasPermi('nursing:data:add')")
    @Log(title = "设备数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("设备数据信息") @RequestBody DeviceData deviceData) {
        return toAjax(deviceDataService.insertDeviceData(deviceData));
    }

    /**
     修改设备数据
     */
    @ApiOperation("修改设备数据")
    @PreAuthorize("@ss.hasPermi('nursing:data:edit')")
    @Log(title = "设备数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("设备数据信息") @RequestBody DeviceData deviceData) {
        return toAjax(deviceDataService.updateDeviceData(deviceData));
    }

    /**
     删除设备数据
     */
    @ApiOperation("删除设备数据")
    @PreAuthorize("@ss.hasPermi('nursing:data:remove')")
    @Log(title = "设备数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("设备数据ID数组") @PathVariable Long[] ids) {
        return toAjax(deviceDataService.deleteDeviceDataByIds(ids));
    }
}
