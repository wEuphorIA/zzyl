package com.zzyl.nursing.controller.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.FamilyMemberDto;
import com.zzyl.nursing.dto.UserLoginRequestDto;
import com.zzyl.nursing.mq.vo.HourlyDataVo;
import com.zzyl.nursing.service.IDeviceService;
import com.zzyl.nursing.vo.DeviceVo;
import com.zzyl.nursing.vo.FamilyMemberPageVo;
import com.zzyl.nursing.vo.FamilyMemberVo;
import com.zzyl.nursing.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zzyl.common.annotation.Log;
import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.AjaxResult;
import com.zzyl.common.enums.BusinessType;
import com.zzyl.nursing.domain.FamilyMember;
import com.zzyl.nursing.service.IFamilyMemberService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 老人家属Controller

 @author Euphoria
 @date 2025-10-11 */
@Api(tags = "老人家属管理")
@RestController
@RequestMapping("/member/user")
public class FamilyMemberController extends BaseController {

    @Autowired
    private IFamilyMemberService familyMemberService;

    @Autowired
    private IDeviceService deviceService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public AjaxResult login(@RequestBody UserLoginRequestDto loginRequestDto) {
        LoginVo loginVo = familyMemberService.login(loginRequestDto);
        return success(loginVo);
    }

    @GetMapping("/queryServiceProperties/{iotId}")
    @ApiOperation("查询健康数据")
    public R<List<DeviceVo>> queryServiceProperties(@PathVariable String iotId) {
        return R.ok(deviceService.queryServiceProperties(iotId));
    }

    @PostMapping("/add")
    @ApiOperation("绑定家人")
    public R add(@RequestBody FamilyMemberDto familyMemberDto) {
        familyMemberService.add(familyMemberDto);
        return R.ok();
    }

    @GetMapping("/my")
    @ApiOperation("查询当前登录用户的家人信息")
    public R<List<FamilyMemberVo>> my() {
        return R.ok(familyMemberService.my());
    }

    @GetMapping("/list-by-page")
    @ApiOperation("查看已绑定家人列表（分页查询）")
    public R<List<FamilyMemberPageVo>> listByPage() {
        return R.ok(familyMemberService.listByPage());
    }

    @DeleteMapping("/deleteById")
    @ApiOperation("解绑老人")
    public R deleteById(@RequestParam Long id) {
        familyMemberService.deleteById(id);
        return R.ok();
    }

    @GetMapping("/queryDeviceDataListByDay")
    @ApiOperation("按天统计查询指标数据")
    public R<List<HourlyDataVo>> queryDeviceDataListByDay(@RequestParam String iotId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String functionId) {
        return R.ok(familyMemberService.queryDeviceDataListByDay(iotId, startTime, endTime,functionId));
    }

    @GetMapping("/queryDeviceDataListByWeek")
    @ApiOperation("按周统计查询指标数据")
    public R<List<HourlyDataVo>> queryDeviceDataListByWeek(@RequestParam String iotId, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String functionId) {
        return R.ok(familyMemberService.queryDeviceDataListByWeek(iotId, startTime, endTime,functionId));
    }

}
