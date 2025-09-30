package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.domain.Floor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 楼层Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@Mapper
public interface FloorMapper extends BaseMapper<Floor>
{
    /**
     * 查询楼层
     *
     * @param id 楼层主键
     * @return 楼层
     */
    public Floor selectFloorById(Long id);

    /**
     * 查询楼层列表
     *
     * @param floor 楼层
     * @return 楼层集合
     */
    public List<Floor> selectFloorList(Floor floor);

    /**
     * 新增楼层
     *
     * @param floor 楼层
     * @return 结果
     */
    public int insertFloor(Floor floor);

    /**
     * 修改楼层
     *
     * @param floor 楼层
     * @return 结果
     */
    public int updateFloor(Floor floor);

    /**
     * 删除楼层
     *
     * @param id 楼层主键
     * @return 结果
     */
    public int deleteFloorById(Long id);

    /**
     * 批量删除楼层
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFloorByIds(Long[] ids);

    /**
     * 查询所有楼层（负责老人）
     * @return 结果
     */
    List<Floor> selectAllByNur();
}
