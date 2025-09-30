package com.zzyl.nursing.vo;

import com.zzyl.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("床位Vo")
public class BedVo {

    @ApiModelProperty(value = "床位ID")
    private Long id;

    /**
     * 床位号
     */
    @ApiModelProperty(value = "床位号")
    private String bedNumber;

    /**
     * 床位状态: 未入住0, 已入住1 入住申请中2
     */
    @ApiModelProperty(value = "床位状态: 未入住0, 已入住1 入住申请中2")
    private Integer bedStatus;

    /**
     * 房间ID
     */
    @ApiModelProperty(value = "房间ID")
    private Long roomId;

    /**
     * 老人姓名
     */
    @ApiModelProperty(value = "老人姓名")
    private String ename;

    /**
     * 老人id
     */
    @ApiModelProperty(value = "老人id")
    private Long elderId;

    @ApiModelProperty(value = "护理员")
    private List<SysUser> userVos;

}
