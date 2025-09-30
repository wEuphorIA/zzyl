package com.zzyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.ElderMapper;
import com.zzyl.nursing.domain.Elder;
import com.zzyl.nursing.service.IElderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 老人Service业务层处理
 * 
 * @author Euphoria
 * @date 2025-09-30
 */
@Service
public class ElderServiceImpl extends ServiceImpl<ElderMapper, Elder> implements IElderService
{
    @Autowired
    private ElderMapper elderMapper;

    /**
     * 查询老人
     * 
     * @param id 老人主键
     * @return 老人
     */
    @Override
    public Elder selectElderById(Long id)
    {
        return elderMapper.selectById(id);
    }

    /**
     * 查询老人列表
     * 
     * @param elder 老人
     * @return 老人
     */
    @Override
    public List<Elder> selectElderList(Elder elder)
    {
        return elderMapper.selectElderList(elder);
    }

    /**
     * 新增老人
     * 
     * @param elder 老人
     * @return 结果
     */
    @Override
    public int insertElder(Elder elder)
    {
        return elderMapper.insert(elder);
    }

    /**
     * 修改老人
     * 
     * @param elder 老人
     * @return 结果
     */
    @Override
    public int updateElder(Elder elder)
    {
        return elderMapper.updateById(elder);
    }

    /**
     * 批量删除老人
     * 
     * @param ids 需要删除的老人主键
     * @return 结果
     */
    @Override
    public int deleteElderByIds(Long[] ids)
    {
        return elderMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除老人信息
     * 
     * @param id 老人主键
     * @return 结果
     */
    @Override
    public int deleteElderById(Long id)
    {
        return elderMapper.deleteById(id);
    }
}
