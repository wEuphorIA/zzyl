package com.zzyl.nursing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.nursing.domain.Bed;
import com.zzyl.nursing.mapper.BedMapper;
import com.zzyl.nursing.service.IBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 床位Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements IBedService
{
    @Autowired
    private BedMapper bedMapper;

    /**
     * 查询床位
     * 
     * @param id 床位主键
     * @return 床位
     */
    @Override
    public Bed selectBedById(Long id)
    {
        return getById(id);
    }

    /**
     * 查询床位列表
     * 
     * @param bed 床位
     * @return 床位
     */
    @Override
    public List<Bed> selectBedList(Bed bed)
    {
        return bedMapper.selectBedList(bed);
    }

    /**
     * 新增床位
     * 
     * @param bed 床位
     * @return 结果
     */
    @Override
    public int insertBed(Bed bed)
    {
        return save(bed) ? 1 : 0;
    }

    /**
     * 修改床位
     * 
     * @param bed 床位
     * @return 结果
     */
    @Override
    public int updateBed(Bed bed)
    {
        return updateById(bed) ? 1 : 0;
    }

    /**
     * 批量删除床位
     * 
     * @param ids 需要删除的床位主键
     * @return 结果
     */
    @Override
    public int deleteBedByIds(Long[] ids)
    {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除床位信息
     * 
     * @param id 床位主键
     * @return 结果
     */
    @Override
    public int deleteBedById(Long id)
    {
        return removeById(id) ? 1 : 0;
    }
}
