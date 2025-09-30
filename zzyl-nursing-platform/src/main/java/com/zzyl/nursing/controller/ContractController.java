package com.zzyl.nursing.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.zzyl.common.core.domain.R;
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
import com.zzyl.nursing.domain.Contract;
import com.zzyl.nursing.service.IContractService;
import com.zzyl.common.utils.poi.ExcelUtil;
import com.zzyl.common.core.page.TableDataInfo;

/**
 * 合同Controller
 * 
 * @author Euphoria
 * @date 2025-09-30
 */
@Api(tags = "合同管理")
@RestController
@RequestMapping("/nursing/contract")
public class ContractController extends BaseController
{
    @Autowired
    private IContractService contractService;

    /**
     * 查询合同列表
     */
    @ApiOperation("查询合同列表")
    @PreAuthorize("@ss.hasPermi('nursing:contract:list')")
    @GetMapping("/list")
    public TableDataInfo<List<Contract>> list(@ApiParam("合同查询条件") Contract contract)
    {
        startPage();
        List<Contract> list = contractService.selectContractList(contract);
        return getDataTable(list);
    }

    /**
     * 导出合同列表
     */
    @ApiOperation("导出合同列表")
    @PreAuthorize("@ss.hasPermi('nursing:contract:export')")
    @Log(title = "合同", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@ApiParam(value = "合同查询条件") HttpServletResponse response, Contract contract)
    {
        List<Contract> list = contractService.selectContractList(contract);
        ExcelUtil<Contract> util = new ExcelUtil<Contract>(Contract.class);
        util.exportExcel(response, list, "合同数据");
    }

    /**
     * 获取合同详细信息
     */
    @ApiOperation("获取合同详细信息")
    @PreAuthorize("@ss.hasPermi('nursing:contract:query')")
    @GetMapping(value = "/{id}")
    public R<Contract> getInfo(@ApiParam("合同ID") @PathVariable("id") Long id)
    {
        return R.ok(contractService.selectContractById(id));
    }

    /**
     * 新增合同
     */
    @ApiOperation("新增合同")
    @PreAuthorize("@ss.hasPermi('nursing:contract:add')")
    @Log(title = "合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("合同信息") @RequestBody Contract contract)
    {
        return toAjax(contractService.insertContract(contract));
    }

    /**
     * 修改合同
     */
    @ApiOperation("修改合同")
    @PreAuthorize("@ss.hasPermi('nursing:contract:edit')")
    @Log(title = "合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("合同信息") @RequestBody Contract contract)
    {
        return toAjax(contractService.updateContract(contract));
    }

    /**
     * 删除合同
     */
    @ApiOperation("删除合同")
    @PreAuthorize("@ss.hasPermi('nursing:contract:remove')")
    @Log(title = "合同", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@ApiParam("合同ID数组") @PathVariable Long[] ids)
    {
        return toAjax(contractService.deleteContractByIds(ids));
    }
}
