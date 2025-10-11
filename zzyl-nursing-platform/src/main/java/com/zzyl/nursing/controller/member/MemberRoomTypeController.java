package com.zzyl.nursing.controller.member;

import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.nursing.domain.RoomType;
import com.zzyl.nursing.service.IRoomTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member/roomTypes")
@Api(tags = "客户房型管理")
public class MemberRoomTypeController extends BaseController {

    @Autowired
    private IRoomTypeService roomTypeService;

    @GetMapping
    @ApiOperation("根据状态查询房型")
    public AjaxResult findRoomTypeListByStatus(Integer status) {

        List<RoomType> roomTypeVoList = roomTypeService.findRoomTypeListByStatus(status);
        return success(roomTypeVoList);
    }

}
