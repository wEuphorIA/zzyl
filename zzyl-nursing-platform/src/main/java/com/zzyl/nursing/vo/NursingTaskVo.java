package com.zzyl.nursing.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 护理任务视图对象
 */
@Data
@ApiModel("护理任务信息VO")
public class NursingTaskVo {

    @ApiModelProperty(value = "任务ID", example = "191")
    private Long id;

    @ApiModelProperty(value = "创建时间", example = "2024-09-27 23:08:17")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人ID", example = "1")
    private String updateBy;

    @ApiModelProperty(value = "更新时间", example = "2024-09-27 23:25:18")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "护理员ID字符串", example = "103")
    private String nursingId;

    @ApiModelProperty(value = "护理项目ID", example = "6")
    private Long projectId;

    @ApiModelProperty(value = "护理项目名称", example = "洗头")
    private String projectName;

    @ApiModelProperty(value = "老人ID", example = "3")
    private Long elderId;

    @ApiModelProperty(value = "老人姓名", example = "张飞")
    private String elderName;

    @ApiModelProperty(value = "床位号", example = "303-1")
    private String bedNumber;

    @ApiModelProperty(value = "预计服务时间", example = "2024-09-27 08:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime estimatedServerTime;

    @ApiModelProperty(value = "实际服务时间", example = "2024-09-27 23:25:13")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime realServerTime;

    @ApiModelProperty(value = "执行记录", example = "222")
    private String mark;

    @ApiModelProperty(value = "取消原因", example = "老人不舒服")
    private String cancelReason;

    @ApiModelProperty(value = "任务状态(1待执行 2已执行 3已关闭)", example = "2")
    private Integer status;

    @ApiModelProperty(value = "任务图片", example = "https://itheim.oss-cn-beijing.aliyuncs.com/1d7763da-f02e-4b81-b414-2d23e7503a6e.jpg")
    private String taskImage;

    @ApiModelProperty(value = "护理员姓名列表", example = "[\"小白\"]")
    private List<String> nursingName;

    @ApiModelProperty(value = "护理等级名称", example = "5号护理等级")
    private String nursingLevelName;

    @ApiModelProperty(value = "年龄", example = "72")
    private Integer age;

    @ApiModelProperty(value = "更新人姓名", example = "若依")
    private String updater;

    @ApiModelProperty(value = "状态描述", example = "已执行")
    private String statusDesc;

    @ApiModelProperty(value = "是否超时", example = "false")
    private Boolean overtime;

    private String createBy;

    private String remark;

    private String sex;
}