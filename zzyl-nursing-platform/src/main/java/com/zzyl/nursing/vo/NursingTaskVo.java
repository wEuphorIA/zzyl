package com.zzyl.nursing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 护理任务视图对象（精简版）
 */
@Data
@ApiModel("护理任务信息VO")
public class NursingTaskVo {
    
    @ApiModelProperty(value = "任务ID", example = "163")
    private Long id;
    
    @ApiModelProperty(value = "创建时间", example = "2024-09-27 23:08:17")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "护理员ID字符串", example = "102,103")
    private String nursingId;
    
    @ApiModelProperty(value = "护理项目ID", example = "4")
    private Long projectId;
    
    @ApiModelProperty(value = "护理项目名称", example = "助餐")
    private String projectName;
    
    @ApiModelProperty(value = "老人ID", example = "2")
    private Long elderId;
    
    @ApiModelProperty(value = "老人姓名", example = "关羽")
    private String elderName;
    
    @ApiModelProperty(value = "床位号", example = "101-2")
    private String bedNumber;
    
    @ApiModelProperty(value = "预计服务时间", example = "2024-09-27 08:00:00")
    private LocalDateTime estimatedServerTime;
    
    @ApiModelProperty(value = "任务状态", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "护理员姓名列表", example = "[\"小青\", \"小白\"]")
    private List<String> nursingName;
}