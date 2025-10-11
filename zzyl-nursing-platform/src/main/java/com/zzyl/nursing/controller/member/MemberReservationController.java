package com.zzyl.nursing.controller.member;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.github.pagehelper.PageInfo;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.core.page.PageResult;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.common.core.redis.RedisCache;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.UserThreadLocal;
import com.zzyl.nursing.domain.Reservation;
import com.zzyl.nursing.dto.ReservationDto;
import com.zzyl.nursing.service.IReservationService;
import com.zzyl.nursing.vo.CountByTimeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 预约信息Controller

 @author ruoyi */
@RestController
@RequestMapping("/member/reservation")
@Api(tags = "预约信息相关接口")
public class MemberReservationController extends BaseController {

    @Autowired
    private IReservationService reservationService;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    private static final String KEY_PREFIX = "cancel_count";



    @GetMapping("/cancelled-count")
    @ApiOperation("查询取消预约数量")
    public R<Integer> getCancelledReservationCount() {
        Long userId = UserThreadLocal.getUserId();
        Integer result = reservationService.cancelledCount(userId);
        redisTemplate.opsForValue().set(String.format("%s:%d", KEY_PREFIX, userId),result);
        return R.ok(result);
    }

    @GetMapping("/countByTime")
    @ApiOperation("查询每个时间段剩余预约次数")
    public R<List<CountByTimeVO>> getRemainingReservations(long time) {
        List<CountByTimeVO> result = reservationService.getRemainingReservations(time);
        return R.ok(result);
    }

    /**
     查询预约信息列表
     */
    @ApiOperation("查询预约信息列表")
    @GetMapping("/page")
    public R<PageResult<Reservation>> list(@ApiParam("预约信息查询条件") Reservation reservation) {
        startPage();
        List<Reservation> list = reservationService.selectReservationList(reservation);
        return R.ok(new PageResult<>(new PageInfo(list).getTotal(),list));
    }

    /**
     新增预约信息
     */
    @ApiOperation("新增预约信息")
    @PostMapping
    public AjaxResult add(@ApiParam("预约信息信息") @RequestBody ReservationDto ReservationDto) {
        Long userId = UserThreadLocal.getUserId();
        Integer cacheObject = redisTemplate.opsForValue().get(String.format("%s:%d", KEY_PREFIX, userId));
        if (cacheObject > 2) {
            throw new BaseException("取消预约次数大于3，不能新建预约了");
        }
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(ReservationDto,reservation);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        reservation.setTime(LocalDateTime.parse(ReservationDto.getTime(), formatter));
        reservation.setCreateBy(UserThreadLocal.getUserId().toString());
        reservation.setStatus(0);
        reservation.setCreateTime(new Date());
        reservationService.save(reservation);
        return success();
    }

    @PutMapping("/{id}/cancel")
    @ApiOperation("取消预约")
    @Transactional
    public AjaxResult cancel(@ApiParam("预约id") @PathVariable Long id) {
        Long userId = UserThreadLocal.getUserId();
        System.out.println(redisTemplate.opsForValue().increment(String.format("%s:%d", KEY_PREFIX, userId), 1));
        Integer i = redisTemplate.opsForValue().get(String.format("%s:%d", KEY_PREFIX, userId));
        i++;
        Reservation reservation = reservationService.selectReservationById(id);
        reservation.setStatus(2);
        reservation.setUpdateBy(UserThreadLocal.getUserId().toString());
        reservation.setUpdateTime(new Date());
        reservationService.updateById(reservation);
        return success();
    }

}