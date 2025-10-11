package com.zzyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.zzyl.nursing.vo.CountByTimeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.ReservationMapper;
import com.zzyl.nursing.domain.Reservation;
import com.zzyl.nursing.service.IReservationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 预约信息Service业务层处理

 @author Euphoria
 @date 2025-10-11 */
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements IReservationService {
    @Autowired
    private ReservationMapper reservationMapper;

    /**
     查询预约信息

     @param id 预约信息主键
     @return 预约信息
     */
    @Override
    public Reservation selectReservationById(Long id) {
        return reservationMapper.selectById(id);
    }

    /**
     查询预约信息列表

     @param reservation 预约信息
     @return 预约信息
     */
    @Override
    public List<Reservation> selectReservationList(Reservation reservation) {
        return reservationMapper.selectReservationList(reservation);
    }

    /**
     新增预约信息

     @param reservation 预约信息
     @return 结果
     */
    @Override
    public int insertReservation(Reservation reservation) {
        return reservationMapper.insert(reservation);
    }

    /**
     修改预约信息

     @param reservation 预约信息
     @return 结果
     */
    @Override
    public int updateReservation(Reservation reservation) {
        return reservationMapper.updateById(reservation);
    }

    /**
     批量删除预约信息

     @param ids 需要删除的预约信息主键
     @return 结果
     */
    @Override
    public int deleteReservationByIds(Long[] ids) {
        return reservationMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     删除预约信息信息

     @param id 预约信息主键
     @return 结果
     */
    @Override
    public int deleteReservationById(Long id) {
        return reservationMapper.deleteById(id);
    }

    @Override
    public Integer cancelledCount(Long userId) {
        long time = System.currentTimeMillis();
        LocalDateTime ldt = LocalDateTimeUtil.of(time);
        LocalDateTime startTime = ldt.toLocalDate().atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1);
        return reservationMapper.cancelledCount(userId,startTime,endTime);
    }

    @Override
    public List<CountByTimeVO> getRemainingReservations(long time) {
        LocalDateTime ldt = LocalDateTimeUtil.of(time);
        LocalDateTime startTime = ldt.toLocalDate().atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1);
        return reservationMapper.getRemainingReservations(startTime,endTime);
    }
}
