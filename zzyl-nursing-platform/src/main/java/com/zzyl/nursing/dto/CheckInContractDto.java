
package com.zzyl.nursing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author itcast
 */
@ApiModel(description = "合同信息")
@Data
public class CheckInContractDto {
    /**
     * 合同名称
     */
    @ApiModelProperty(value = "合同名称")
    private String contractName;

    /**
     * 签约时间
     */
    @ApiModelProperty(value = "签约时间,格式：yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signDate;

    /**
     * 丙方名称
     */
    @ApiModelProperty(value = "丙方名称")
    private String thirdPartyName;

    /**
     * 丙方手机号
     */
    @ApiModelProperty(value = "丙方手机号")
    private String thirdPartyPhone;

    /**
     * 合同pdf文件地址
     */
    @ApiModelProperty(value = "合同pdf文件地址")
    private String agreementPath;

}


