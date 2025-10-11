package com.zzyl.nursing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 老人家属对象 family_member
 * 
 * @author Euphoria
 * @date 2025-10-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("老人家属实体")
public class FamilyMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // 主键
    @ApiModelProperty("主键")
    private Long id;

    // 手机号
    @Excel(name = "手机号")
    @ApiModelProperty("手机号")
    private String phone;

    // 名称
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    // 头像
    @Excel(name = "头像")
    @ApiModelProperty("头像")
    private String avatar;

    // OpenID
    @Excel(name = "OpenID")
    @ApiModelProperty("OpenID")
    private String openId;

    // 性别(0:男，1:女)
    @Excel(name = "性别(0:男，1:女)")
    @ApiModelProperty("性别(0:男，1:女)")
    private Integer gender;

}
