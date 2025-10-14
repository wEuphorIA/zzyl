package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/14 上午11:31 */
@Data
@ApiModel("老人家属信息VO")
public class FamilyMemberPageVo {

    @ApiModelProperty(value = "家属成员ID", example = "7")
    private String mid;

    @ApiModelProperty(value = "家属备注称谓", example = "皇叔")
    private String mremark;

    @ApiModelProperty(value = "关联老人ID", example = "1")
    private String elderId;

    @ApiModelProperty(value = "老人姓名", example = "刘备")
    private String name;

    @ApiModelProperty(value = "老人照片", example = "https://example.com/avatar.png")
    private String image;

    @ApiModelProperty(value = "老人床位号", example = "101-1")
    private String bedNumber;

    @ApiModelProperty(value = "房间类型", example = "豪华单人间")
    private String typeName;

    @ApiModelProperty(value = "物联网设备ID", example = "847XFy3JE4CcQk6SpHMHj0rk00")
    private String iotId;

    @ApiModelProperty(value = "设备名称", example = "watch01")
    private String deviceName;

    @ApiModelProperty(value = "产品密钥", example = "j0rkvUZBtRu")
    private String productKey;
}
