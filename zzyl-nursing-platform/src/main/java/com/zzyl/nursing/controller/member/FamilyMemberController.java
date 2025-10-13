package com.zzyl.nursing.controller.member;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
import com.zzyl.nursing.dto.UserLoginRequestDto;
import com.zzyl.nursing.service.IDeviceService;
import com.zzyl.nursing.vo.DeviceVo;
import com.zzyl.nursing.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
