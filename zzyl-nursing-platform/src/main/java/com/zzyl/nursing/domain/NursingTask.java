package com.zzyl.nursing.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;

/**
 * 护理任务对象 nursing_task
 * 
 * @author alexis
 * @date 2024-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "护理任务实体")
public class NursingTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    // id
    @ApiModelProperty(value = "主键ID")
    private Long id;

    // 护理员id
    @Excel(name = "护理员id")
    @ApiModelProperty(value = "护理员id")
    private String nursingId;

    // 项目id
    @Excel(name = "项目id")
    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    // 护理项目名称
    @Excel(name = "护理项目名称")
    @ApiModelProperty(value = "护理项目名称")
    private String projectName;

    // 老人id
    @Excel(name = "老人id")
    @ApiModelProperty(value = "老人id")
    private Long elderId;

    // 老人姓名
    @Excel(name = "老人姓名")
    @ApiModelProperty(value = "老人姓名")
    private String elderName;

    // 床位编号
    @Excel(name = "床位编号")
    @ApiModelProperty(value = "床位编号")
    private String bedNumber;

    // 预计服务时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预计服务时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预计服务时间")
    private LocalDateTime estimatedServerTime;

    // 实际服务时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际服务时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "实际服务时间")
    private LocalDateTime realServerTime;

    // 执行记录
    @Excel(name = "执行记录")
    @ApiModelProperty(value = "执行记录")
    private String mark;

    // 取消原因
    @Excel(name = "取消原因")
    @ApiModelProperty(value = "取消原因")
    private String cancelReason;

    // 状态  1待执行 2已执行 3已关闭 
    @Excel(name = "状态  1待执行 2已执行 3已关闭 ")
    @ApiModelProperty(value = "状态  1待执行 2已执行 3已关闭 ")
    private Integer status;

    // 执行图片
    @Excel(name = "执行图片")
    @ApiModelProperty(value = "执行图片")
    private String taskImage;

}
