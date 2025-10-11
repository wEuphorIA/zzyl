package com.zzyl.nursing.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 预约信息对象 reservation
 * 
 * @author Euphoria
 * @date 2025-10-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("预约信息实体")
public class Reservation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // 主键ID
    @ApiModelProperty("主键ID")
    private Long id;

    // 预约人姓名
    @Excel(name = "预约人姓名")
    @ApiModelProperty("预约人姓名")
    private String name;

    // 预约人手机号
    @Excel(name = "预约人手机号")
    @ApiModelProperty("预约人手机号")
    private String mobile;

    // 预约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预约时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("预约时间")
    private LocalDateTime time;

    // 探访人
    @Excel(name = "探访人")
    @ApiModelProperty("探访人")
    private String visitor;

    // 预约类型，0：参观预约，1：探访预约
    @Excel(name = "预约类型，0：参观预约，1：探访预约")
    @ApiModelProperty("预约类型，0：参观预约，1：探访预约")
    private Integer type;

    // 预约状态，0：待报道，1：已完成，2：取消，3：过期
    @Excel(name = "预约状态，0：待报道，1：已完成，2：取消，3：过期")
    @ApiModelProperty("预约状态，0：待报道，1：已完成，2：取消，3：过期")
    private Integer status;

}
