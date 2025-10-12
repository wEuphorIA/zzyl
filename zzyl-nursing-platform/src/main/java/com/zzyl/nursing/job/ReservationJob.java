package com.zzyl.nursing.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.nursing.domain.Contract;
import com.zzyl.nursing.domain.Reservation;
import com.zzyl.nursing.service.IReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/12 下午2:02 */
@Component
@Slf4j
public class ReservationJob {

    @Resource
    private IReservationService reservationService;

    @Scheduled(cron = "0 1,31 * * * ?")
    public void updateReservationStatusJob(){
        LambdaQueryWrapper<Reservation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reservation::getStatus, 0)
                .le(Reservation::getTime, LocalDateTime.now());

        List<Reservation> list = reservationService.list(lambdaQueryWrapper);

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(s->{s.setStatus(3);s.setUpdateTime(new Date());});
            reservationService.saveOrUpdateBatch(list);
        }
    }
}
