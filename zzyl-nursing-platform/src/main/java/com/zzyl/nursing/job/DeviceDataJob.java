package com.zzyl.nursing.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.nursing.domain.DeviceData;
import com.zzyl.nursing.service.IDeviceDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/14 下午7:58 */
@Component
@Slf4j
public class DeviceDataJob {

    @Resource
    private IDeviceDataService deviceDataService;

    private static final int RETENTION_DAYS = 15; // 保留15天数据

    @Scheduled(cron = "0 0 2 * * ?")
    public void clean(){
        LocalDate cutoffDate = LocalDate.now().minusDays(RETENTION_DAYS);
        log.info("开始清理{}之前的数据...", cutoffDate);

        boolean remove = deviceDataService.remove(new LambdaQueryWrapper<DeviceData>().le(DeviceData::getCreateTime, cutoffDate));

        if (remove) {
            log.info("数据清理完成");
        } else {
            log.warn("数据清理失败或无符合条件数据");
        }
    }
}
