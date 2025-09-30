package com.zzyl.nursing.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 房型对象 room_type
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@Data
public class RoomType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 房型名称 */
    @Excel(name = "房型名称")
    private String name;

    /** 床位数量 */
    @Excel(name = "床位数量")
    private Long bedCount;

    /** 床位费用 */
    @Excel(name = "床位费用")
    private BigDecimal price;

    /** 介绍 */
    @Excel(name = "介绍")
    private String introduction;

    /** 照片 */
    @Excel(name = "照片")
    private String photo;

    /** 状态，0：禁用，1：启用 */
    @Excel(name = "状态，0：禁用，1：启用")
    private Long status;

}
