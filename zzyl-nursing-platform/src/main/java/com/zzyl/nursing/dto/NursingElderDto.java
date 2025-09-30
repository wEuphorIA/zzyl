package com.zzyl.nursing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("护理员与老人关系Dto")
public class NursingElderDto {

    @ApiModelProperty(value = "老人id")
    private Long elderId;

    @ApiModelProperty(value = "护理员id列表")
    private List<Long> nursingIds;
}
