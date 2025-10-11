package com.zzyl.nursing.dto;

import lombok.Data;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/11 下午7:49 */
@Data
public class ReservationDto {

    private String mobile; //手机号

    private String name; //预约人

    private String time; //时间

    private Integer type;//预约类型  0为参观预约

    private String visitor;//家人姓名（老人姓名）

}
