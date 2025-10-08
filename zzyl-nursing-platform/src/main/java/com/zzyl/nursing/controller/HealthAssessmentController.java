package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.config.RuoYiConfig;
import com.zzyl.common.core.domain.R;
import com.zzyl.common.utils.PDFUtil;
import com.zzyl.common.utils.file.FileUtils;
import com.zzyl.nursing.dto.HealthAssessmentDto;
import com.zzyl.oss.AliyunOSSOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.zzyl.nursing.domain.HealthAssessment;
import com.zzyl.nursing.service.IHealthAssessmentService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 健康评估Controller

 @author ruoyi
 @date 2024-10-10 */
@RestController
@RequestMapping("/nursing/healthAssessment")
@Api(tags = "健康评估相关接口")
public class HealthAssessmentController extends BaseController {

    @Autowired
    private IHealthAssessmentService healthAssessmentService;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     查询健康评估列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取健康评估列表")
    public TableDataInfo<List<HealthAssessment>> list(HealthAssessment healthAssessment) {
        startPage();
        List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(healthAssessment);
        return getDataTable(list);
    }

    /**
     导出健康评估列表
     */
    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:export')")
    @Log(title = "健康评估", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出健康评估列表")
    public void export(HttpServletResponse response, HealthAssessment healthAssessment) {
        List<HealthAssessment> list = healthAssessmentService.selectHealthAssessmentList(healthAssessment);
        ExcelUtil<HealthAssessment> util = new ExcelUtil<HealthAssessment>(HealthAssessment.class);
        util.exportExcel(response, list, "健康评估数据");
    }

    /**
     获取健康评估详细信息
     */
    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取健康评估详细信息")
    public R<HealthAssessment> getInfo(@ApiParam(value = "健康评估ID", required = true)
                                       @PathVariable("id") Long id) {
        return R.ok(healthAssessmentService.selectHealthAssessmentById(id));
    }

    /**
     新增健康评估
     */
    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:add')")
    @Log(title = "健康评估", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增健康评估")
    public AjaxResult add(@ApiParam(value = "健康评估实体", required = true) @RequestBody HealthAssessmentDto healthAssessmentDto) {
        Long id = healthAssessmentService.insertHealthAssessment(healthAssessmentDto);
        return success(id);
    }

    /**
     修改健康评估
     */
    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:edit')")
    @Log(title = "健康评估", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改健康评估")
    public AjaxResult edit(@ApiParam(value = "健康评估实体", required = true) @RequestBody HealthAssessment healthAssessment) {
        return toAjax(healthAssessmentService.updateHealthAssessment(healthAssessment));
    }

    /**
     删除健康评估
     */
    @PreAuthorize("@ss.hasPermi('nursing:healthAssessment:remove')")
    @Log(title = "健康评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除健康评估")
    public AjaxResult remove(@ApiParam(value = "要删除的数据id的数组") @PathVariable Long[] ids) {
        return toAjax(healthAssessmentService.deleteHealthAssessmentByIds(ids));
    }

    @PostMapping("/upload")
    @ApiOperation("健康文档上传")
    public AjaxResult uploadFile(MultipartFile file,String idCardNo) throws Exception {
        try {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();

            //读取pdf的文件内容
            String pdf = PDFUtil.pdfToString(file.getInputStream());

            //将pdf的内容存到redis中
            redisTemplate.opsForHash().put("healthReport", idCardNo, pdf);

            String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());

            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", url);
            ajax.put("newFileName", FileUtils.getName(url));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
