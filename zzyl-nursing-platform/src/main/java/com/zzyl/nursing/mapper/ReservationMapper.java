package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.vo.CountByTimeVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.zzyl.nursing.domain.Reservation;
import org.apache.ibatis.annotations.Param;
import springfox.documentation.PathProvider;

/**
 预约信息Mapper接口

 @author Euphoria
 @date 2025-10-11 */
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
    // ReservationMapper(PathProvider pathProvider) ;


    /**
     查询预约信息

     @param id 预约信息主键
     @return 预约信息
     */
    public Reservation selectReservationById(Long id);

    /**
     查询预约信息列表

     @param reservation 预约信息
     @return 预约信息集合
     */
    public List<Reservation> selectReservationList(Reservation reservation);

    /**
     新增预约信息

     @param reservation 预约信息
     @return 结果
     */
    public int insertReservation(Reservation reservation);

    /**
     修改预约信息

     @param reservation 预约信息
     @return 结果
     */
    public int updateReservation(Reservation reservation);

    /**
     删除预约信息

     @param id 预约信息主键
     @return 结果
     */
    public int deleteReservationById(Long id);

    /**
     批量删除预约信息

     @param ids 需要删除的数据主键集合
     @return 结果
     */
    public int deleteReservationByIds(Long[] ids);

    Integer cancelledCount(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    List<CountByTimeVO> getRemainingReservations(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
