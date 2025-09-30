package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 老人入住请求模型
 *
 * @author itcast
 * @create 2023/12/18 18:44
 **/
@ApiModel(description = "老人入住请求模型")
@Data
public class CheckInElderVo {

    /**
     * 老人id
     */
    @ApiModelProperty(value = "老人id")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    /**
     * 出生日期，格式：yyyy-MM-dd
     */
    @ApiModelProperty(value = "出生日期，格式：yyyy-MM-dd")
    private String birthday;

    /**
     * 性别，0：男，1：女，2：未知
     */
    @ApiModelProperty(value = "性别，0：男，1：女，2：未知")
    private Integer sex;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 家庭住址
     */
    @ApiModelProperty(value = "家庭住址")
    private String address;

    /**
     * 一寸照片
     */
    @ApiModelProperty(value = "一寸照片")
    private String image;

    /**
     * 身份证国徽面
     */
    @ApiModelProperty(value = "身份证国徽面")
    private String idCardNationalEmblemImg;

    /**
     * 身份证人像面
     */
    @ApiModelProperty(value = "身份证人像面")
    private String idCardPortraitImg;

    /**
     * 年龄
     */
    private Integer age;

}
