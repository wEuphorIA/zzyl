package com.zzyl.nursing.mq.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("小时数据点")
public class HourlyDataVo {
    
    @ApiModelProperty(value = "时间点（小时:分钟）", example = "00:00")
    private String dateTime;
    
    @ApiModelProperty(value = "数据值", example = "73.0")
    private Double dataValue;
    
    // @ApiModelProperty(value = "数据状态", example = "正常")
    // private String status;
    //
    // /**
    //  * 根据数据值自动计算状态
    //  */
    // public String getStatus() {
    //     if (dataValue == null) {
    //         return "无数据";
    //     } else if (dataValue == 0.0) {
    //         return "设备离线";
    //     } else if (dataValue > 100.0) {
    //         return "数据异常";
    //     } else {
    //         return "正常";
    //     }
    // }
}