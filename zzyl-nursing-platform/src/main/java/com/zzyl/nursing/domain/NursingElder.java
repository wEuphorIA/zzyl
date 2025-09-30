package com.zzyl.nursing.domain;

import com.zzyl.common.annotation.Excel;
import com.zzyl.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 护理员老人关联对象 nursing_elder
 *
 * @author ruoyi
 * @date 2024-05-28
 */
@Data
public class NursingElder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 护理员id */
    @Excel(name = "护理员id")
    private Long nursingId;

    /** 老人id */
    @Excel(name = "老人id")
    private Long elderId;
}
