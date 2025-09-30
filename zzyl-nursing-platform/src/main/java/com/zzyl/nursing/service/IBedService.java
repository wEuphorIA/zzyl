package com.zzyl.nursing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzyl.nursing.domain.Bed;

import java.util.List;

/**
 * 床位Service接口
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
public interface IBedService extends IService<Bed>
{
    /**
     * 查询床位
     * 
     * @param id 床位主键
     * @return 床位
     */
    public Bed selectBedById(Long id);

    /**
     * 查询床位列表
     * 
     * @param bed 床位
     * @return 床位集合
     */
    public List<Bed> selectBedList(Bed bed);

    /**
     * 新增床位
     * 
     * @param bed 床位
     * @return 结果
     */
    public int insertBed(Bed bed);

    /**
     * 修改床位
     * 
     * @param bed 床位
     * @return 结果
     */
    public int updateBed(Bed bed);

    /**
     * 批量删除床位
     * 
     * @param ids 需要删除的床位主键集合
     * @return 结果
     */
    public int deleteBedByIds(Long[] ids);

    /**
     * 删除床位信息
     * 
     * @param id 床位主键
     * @return 结果
     */
    public int deleteBedById(Long id);
}
