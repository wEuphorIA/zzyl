package com.zzyl.nursing.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.nursing.domain.RoomType;
import com.zzyl.nursing.mapper.RoomTypeMapper;
import com.zzyl.nursing.service.IRoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 房型Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-26
 */
@Service
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeMapper, RoomType> implements IRoomTypeService
{
    @Autowired
    private RoomTypeMapper roomTypeMapper;

    /**
     * 查询房型
     * 
     * @param id 房型主键
     * @return 房型
     */
    @Override
    public RoomType selectRoomTypeById(Long id)
    {
        return getById(id);
    }

    /**
     * 查询房型列表
     * 
     * @param roomType 房型
     * @return 房型
     */
    @Override
    public List<RoomType> selectRoomTypeList(RoomType roomType)
    {
        return roomTypeMapper.selectRoomTypeList(roomType);
    }

    /**
     * 新增房型
     * 
     * @param roomType 房型
     * @return 结果
     */
    @Override
    public int insertRoomType(RoomType roomType)
    {
        return save(roomType) ? 1 : 0;
    }

    /**
     * 修改房型
     * 
     * @param roomType 房型
     * @return 结果
     */
    @Override
    public int updateRoomType(RoomType roomType)
    {
        return updateById(roomType) ? 1 : 0;
    }

    /**
     * 批量删除房型
     * 
     * @param ids 需要删除的房型主键
     * @return 结果
     */
    @Override
    public int deleteRoomTypeByIds(Long[] ids)
    {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除房型信息
     * 
     * @param id 房型主键
     * @return 结果
     */
    @Override
    public int deleteRoomTypeById(Long id)
    {
        return removeById(id) ? 1 : 0;
    }

    /**
     * 按照状态查询房间类型
     * @param status
     * @return
     */
    @Override
    public List<RoomType> findRoomTypeListByStatus(Integer status) {

        if(ObjectUtil.isEmpty(status)){
            throw new BaseException("参数为空");
        }
        LambdaQueryWrapper<RoomType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomType::getStatus,status);
        return list(wrapper);
    }
}
