package com.zzyl.nursing.vo.health;

import lombok.Data;

@Data
public class SystemScore {

    /**
     * 呼吸系统
     */
    private int breathingSystem;

    /**
     * 消化系统
     */
    private int digestiveSystem;

    /**
     * 内分泌系统
     */
    private int endocrineSystem;

    /**
     * 免疫系统
     */
    private int immuneSystem;

    /**
     * 循环系统
     */
    private int circulatorySystem;

    /**
     * 泌尿系统
     */
    private int urinarySystem;

    /**
     * 感觉系统
     */
    private int motionSystem;

    /**
     * 感官系统
     */
    private int senseSystem;
}