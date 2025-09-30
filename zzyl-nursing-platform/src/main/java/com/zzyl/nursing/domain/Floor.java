package com.zzyl.nursing.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 楼层对象 floor
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@Data
@ApiModel("楼层实体")
public class Floor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty(value = "id")
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称")
    private String name;

    /** 编号 */
    @Excel(name = "编号")
    @ApiModelProperty(value = "编号")
    private Long code;

}
