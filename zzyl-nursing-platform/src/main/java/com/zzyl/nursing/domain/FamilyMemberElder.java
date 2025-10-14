package com.zzyl.nursing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 客户老人关联对象 family_member_elder
 * 
 * @author Euphoria
 * @date 2025-10-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("客户老人关联实体")
public class FamilyMemberElder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // id
    @ApiModelProperty("id")
    private Long id;

    // 家属id
    @Excel(name = "家属id")
    @ApiModelProperty("家属id")
    private Long familyMemberId;

    // 老人id
    @Excel(name = "老人id")
    @ApiModelProperty("老人id")
    private Long elderId;

}
