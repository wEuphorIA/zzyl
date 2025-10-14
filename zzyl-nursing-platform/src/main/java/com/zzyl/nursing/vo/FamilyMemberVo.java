package com.zzyl.nursing.vo;

import com.zzyl.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/14 上午10:38 */
@Data
public class FamilyMemberVo {

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

    private String elderName;
}
