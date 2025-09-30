package com.zzyl.nursing.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 房间对象 room
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@Data
@ApiModel("房间实体对象")
public class Room extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 房间编号 */
    @ApiModelProperty(value = "房间编号")
    @Excel(name = "房间编号")
    private String code;

    /** 排序号 */
    @ApiModelProperty(value = "排序号")
    @Excel(name = "排序号")
    private Long sort;

    /** 房间类型名称 */
    @ApiModelProperty(value = "房间类型名称")
    @Excel(name = "房间类型名称")
    private String typeName;

    /** 楼层id */
    @ApiModelProperty(value = "楼层id")
    @Excel(name = "楼层id")
    private Long floorId;

    /** 是否删除 */
    @ApiModelProperty(value = "是否删除")
    @Excel(name = "是否删除")
    private Integer isDeleted;

}
