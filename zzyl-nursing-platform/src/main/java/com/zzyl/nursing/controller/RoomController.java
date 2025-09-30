package com.zzyl.nursing.controller;

import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.Room;
import com.zzyl.nursing.service.IRoomService;
import com.zzyl.nursing.vo.RoomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间Controller
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@RestController
@RequestMapping("/elder/room")
@Api(tags =  "房间相关接口")
public class RoomController extends BaseController
{
    @Autowired
    private IRoomService roomService;

    @GetMapping("/getRoomsWithNurByFloorId/{floorId}")
    @ApiOperation("获取所有房间（负责老人）")
    public R<List<RoomVo>> getRoomsWithNurByFloorId(@PathVariable Long floorId) {
        List<RoomVo> list = roomService.getRoomsWithNurByFloorId(floorId);
        return R.ok(list);
    }

    @GetMapping("/getRoomsByFloorId/{floorId}")
    @ApiOperation("获取所有房间（入住配置）")
    public R<List<RoomVo>> getRoomsByFloorId(@ApiParam(value = "楼层ID", required = true)  @PathVariable Long floorId) {
        List<RoomVo> list = roomService.getRoomsByFloorId(floorId);
        return R.ok(list);
    }
    /**
     * 查询房间列表
     */
    @PreAuthorize("@ss.hasPermi('elder:room:list')")
    @GetMapping("/list")
    @ApiOperation("查询房间列表")
    public TableDataInfo list(@ApiParam(value = "房间信息", required = true)  Room room)
    {
        startPage();
        List<Room> list = roomService.selectRoomList(room);
        return getDataTable(list);
    }

    /**
     * 获取房间详细信息
     */
    @PreAuthorize("@ss.hasPermi('elder:room:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取房间详细信息")
    public R<Room> getInfo(@ApiParam(value = "房间ID", required = true)  @PathVariable("id") Long id)
    {
        return R.ok(roomService.selectRoomById(id));
    }

    /**
     * 新增房间
     */
    @PreAuthorize("@ss.hasPermi('elder:room:add')")
    @Log(title = "房间", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增房间")
    public AjaxResult add(@ApiParam(value = "房间信息", required = true)  @RequestBody Room room)
    {
        return toAjax(roomService.insertRoom(room));
    }

    /**
     * 修改房间
     */
    @ApiOperation("修改房间")
    @PreAuthorize("@ss.hasPermi('elder:room:edit')")
    @Log(title = "房间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam(value = "房间信息", required = true)  @RequestBody Room room)
    {
        return toAjax(roomService.updateRoom(room));
    }

    /**
     * 删除房间
     */
    @ApiOperation("删除房间")
    @PreAuthorize("@ss.hasPermi('elder:room:remove')")
    @Log(title = "房间", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam(value = "房间ID数组", required = true)  @PathVariable Long[] ids)
    {
        return toAjax(roomService.deleteRoomByIds(ids));
    }

}