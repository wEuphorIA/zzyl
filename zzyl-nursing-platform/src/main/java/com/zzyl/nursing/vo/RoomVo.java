package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("房间Vo")
public class RoomVo {

    @ApiModelProperty(value = "房间ID", required = true)
    private Long id;

    @ApiModelProperty(value = "楼层名称", required = true)
    private String floorName;

    @ApiModelProperty(value = "楼层ID", required = true)
    private String floorId;

    @ApiModelProperty(value = "房间ID", required = true)
    private String roomId;

    @ApiModelProperty(value = "房间编号", required = true)
    private String code;

    @ApiModelProperty(value = "房间价格", required = true)
    private String price;

    @ApiModelProperty(value = "床位列表", required = true)
    private List<BedVo> bedVoList;

}