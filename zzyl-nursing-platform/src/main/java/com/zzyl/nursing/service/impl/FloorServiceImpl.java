package com.zzyl.nursing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.nursing.domain.Floor;
import com.zzyl.nursing.mapper.FloorMapper;
import com.zzyl.nursing.service.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 楼层Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@Service
public class FloorServiceImpl extends ServiceImpl<FloorMapper, Floor> implements IFloorService
{
    @Autowired
    private FloorMapper floorMapper;

    /**
     * 查询楼层
     *
     * @param id 楼层主键
     * @return 楼层
     */
    @Override
    public Floor selectFloorById(Long id)
    {
        return getById(id);
    }

    /**
     * 新增楼层
     *
     * @param floor 楼层
     * @return 结果
     */
    @Override
    public int insertFloor(Floor floor)
    {
        return save(floor) ? 1 : 0;
    }

    /**
     * 修改楼层
     *
     * @param floor 楼层
     * @return 结果
     */
    @Override
    public int updateFloor(Floor floor)
    {
        return updateById(floor) ? 1 : 0;
    }

    /**
     * 批量删除楼层
     *
     * @param ids 需要删除的楼层主键
     * @return 结果
     */
    @Override
    public int deleteFloorByIds(Long[] ids)
    {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除楼层信息
     *
     * @param id 楼层主键
     * @return 结果
     */
    @Override
    public int deleteFloorById(Long id)
    {
        return removeById(id) ? 1 : 0;
    }

    /**
     * 查询所有楼层（负责老人）
     */
    @Override
    public List<Floor> selectAllByNur() {
        return floorMapper.selectAllByNur();
    }

}