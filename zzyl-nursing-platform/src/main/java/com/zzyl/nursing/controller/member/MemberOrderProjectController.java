package com.zzyl.nursing.controller.member;

import com.zzyl.common.core.controller.BaseController;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.core.page.TableDataInfo;
import com.zzyl.nursing.domain.FamilyMember;
import com.zzyl.nursing.domain.NursingProject;
import com.zzyl.nursing.service.IFamilyMemberService;
import com.zzyl.nursing.service.INursingProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 @author Euphoria
 @version 1.0
 @description: TODO
 @date 2025/10/11 下午6:16 */
@RestController
@RequestMapping("/member/orders/project")
@Api(tags = "服务项接口")
public class MemberOrderProjectController extends BaseController {

    @Resource
    private INursingProjectService nursingProjectService;

    @GetMapping("/page")
    @ApiOperation("查询护理项目列表")
    public TableDataInfo<List<NursingProject>> list(@ApiParam("护理项目查询条件") NursingProject nursingProject) {
        startPage();
        List<NursingProject> list = nursingProjectService.selectNursingProjectList(nursingProject);
        return getDataTable(list);
    }

    @GetMapping("/{id}")
    @ApiOperation("查询详细护理项目")
    public R<NursingProject> getInfo(@ApiParam("护理项目ID") @PathVariable("id") Long id) {
        return R.ok(nursingProjectService.selectNursingProjectById(id));
    }

}
