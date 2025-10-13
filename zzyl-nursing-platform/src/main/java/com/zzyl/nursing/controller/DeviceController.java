package com.zzyl.nursing.controller;

import java.util.List;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.DeviceDto;
import com.zzyl.nursing.vo.DeviceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.nursing.domain.Device;
import com.zzyl.nursing.service.IDeviceService;
import com.zzyl.common.core.page.TableDataInfo;

/**
 设备Controller

 @author Euphoria
 @date 2025-10-13 */
@Api(tags = "设备管理")
@RestController
@RequestMapping("/nursing/device")
public class DeviceController extends BaseController {

    @Autowired
    private IDeviceService deviceService;

    /**
     查询设备列表
     */
    @ApiOperation("查询设备列表")
    @PreAuthorize("@ss.hasPermi('nursing:device:list')")
    @GetMapping("/list")
    public TableDataInfo<List<Device>> list(@ApiParam("设备查询条件") Device device) {
        startPage();
        List<Device> list = deviceService.selectDeviceList(device);
        return getDataTable(list);
    }

    @PostMapping("/syncProductList")
    @ApiOperation(value = "从物联网平台同步产品列表")
    public AjaxResult syncProductList(){
        deviceService.syncProductList();
        return success();
    }

    @GetMapping("/allProduct")
    @ApiOperation(value = "查询所有产品列表")
    public AjaxResult allProduct(){
        return success(deviceService.allProduct());
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册设备")
    public AjaxResult register(@RequestBody DeviceDto deviceDto){
        deviceService.register(deviceDto);
        return success();
    }


    @GetMapping("/{iotId}")
    @ApiOperation(value = "查询设备信息")
    public AjaxResult getInfo(@PathVariable String iotId){

        return success(deviceService.getInfo(iotId));
    }

    @GetMapping("/queryServiceProperties/{iotId}")
    @ApiOperation(value = "查看设备上报的数据")
    public R<List<DeviceVo>> queryServiceProperties(@PathVariable String iotId){
        return R.ok(deviceService.queryServiceProperties(iotId));
    }

    @PutMapping
    @ApiOperation(value = "修改设备数据")
    public AjaxResult update(@RequestBody DeviceDto deviceDto){
        deviceService.update(deviceDto);
        return success();
    }

    @DeleteMapping("/{iotId}")
    @ApiOperation("删除设备")
    public AjaxResult delete(@PathVariable String iotId){
        deviceService.delete(iotId);
        return success();
    }

}
