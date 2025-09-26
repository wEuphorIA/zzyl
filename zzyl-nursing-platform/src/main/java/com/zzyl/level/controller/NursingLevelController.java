package com.zzyl.level.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.zzyl.level.domain.NursingLevel;
import com.zzyl.level.service.INursingLevelService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 护理等级Controller
 * 
 * @author Euphoria
 * @date 2025-09-26
 */
@RestController
@RequestMapping("/level/level")
public class NursingLevelController extends BaseController
{
    @Autowired
    private INursingLevelService nursingLevelService;

    /**
     * 查询护理等级列表
     */
    @PreAuthorize("@ss.hasPermi('level:level:list')")
    @GetMapping("/list")
    public TableDataInfo list(NursingLevel nursingLevel)
    {
        startPage();
        List<NursingLevel> list = nursingLevelService.selectNursingLevelList(nursingLevel);
        return getDataTable(list);
    }

    /**
     * 导出护理等级列表
     */
    @PreAuthorize("@ss.hasPermi('level:level:export')")
    @Log(title = "护理等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NursingLevel nursingLevel)
    {
        List<NursingLevel> list = nursingLevelService.selectNursingLevelList(nursingLevel);
        ExcelUtil<NursingLevel> util = new ExcelUtil<NursingLevel>(NursingLevel.class);
        util.exportExcel(response, list, "护理等级数据");
    }

    /**
     * 获取护理等级详细信息
     */
    @PreAuthorize("@ss.hasPermi('level:level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(nursingLevelService.selectNursingLevelById(id));
    }

    /**
     * 新增护理等级
     */
    @PreAuthorize("@ss.hasPermi('level:level:add')")
    @Log(title = "护理等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NursingLevel nursingLevel)
    {
        return toAjax(nursingLevelService.insertNursingLevel(nursingLevel));
    }

    /**
     * 修改护理等级
     */
    @PreAuthorize("@ss.hasPermi('level:level:edit')")
    @Log(title = "护理等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NursingLevel nursingLevel)
    {
        return toAjax(nursingLevelService.updateNursingLevel(nursingLevel));
    }

    /**
     * 删除护理等级
     */
    @PreAuthorize("@ss.hasPermi('level:level:remove')")
    @Log(title = "护理等级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(nursingLevelService.deleteNursingLevelByIds(ids));
    }
}
