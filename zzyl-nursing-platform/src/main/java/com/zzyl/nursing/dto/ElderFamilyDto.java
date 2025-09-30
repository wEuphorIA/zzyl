package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 老人家属信息
 *
 * @author itcast
 * @create 2023/12/18 20:11
 **/
@ApiModel(description = "老人家属信息")
@Data
public class ElderFamilyDto {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;


    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String phone;

    /**
     * 亲属关系
     */
    @ApiModelProperty(value = "亲属关系")
    private String kinship;

}
