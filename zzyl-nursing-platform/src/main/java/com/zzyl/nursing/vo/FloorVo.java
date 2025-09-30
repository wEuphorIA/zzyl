package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class FloorVo {

    private Long id;

    @ApiModelProperty(value = "楼层名称")
    private String name;

    @ApiModelProperty(value = "楼层编号")
    private Integer code;

    @ApiModelProperty(value = "房间")
    private List<RoomVo> roomVoList;

}
