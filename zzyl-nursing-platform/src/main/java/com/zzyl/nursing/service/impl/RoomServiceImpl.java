package com.zzyl.nursing.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzyl.common.constant.CacheConstants;
import com.zzyl.nursing.domain.DeviceData;
import com.zzyl.nursing.domain.Room;
import com.zzyl.nursing.mapper.RoomMapper;
import com.zzyl.nursing.service.IRoomService;
import com.zzyl.nursing.vo.BedVo;
import com.zzyl.nursing.vo.DeviceInfo;
import com.zzyl.nursing.vo.DeviceVo;
import com.zzyl.nursing.vo.RoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.zzyl.common.constant.CacheConstants.IOT_DEVICE_LAST_DATA;

/**
 * 房间Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 查询房间
     *
     * @param id 房间主键
     * @return 房间
     */
    @Override
    public Room selectRoomById(Long id) {
        return getById(id);
    }

    /**
     * 查询房间列表
     *
     * @param room 房间
     * @return 房间
     */
    @Override
    public List<Room> selectRoomList(Room room) {
        return roomMapper.selectRoomList(room);
    }

    /**
     * 新增房间
     *
     * @param room 房间
     * @return 结果
     */
    @Override
    public int insertRoom(Room room) {
        return save(room) ? 1 : 0;
    }

    /**
     * 修改房间
     *
     * @param room 房间
     * @return 结果
     */
    @Override
    public int updateRoom(Room room) {
        return updateById(room) ? 1 : 0;
    }

    /**
     * 批量删除房间
     *
     * @param ids 需要删除的房间主键
     * @return 结果
     */
    @Override
    public int deleteRoomByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 根据楼层 id 获取房间视图对象列表
     *
     * @param floorId
     * @return
     */
    @Override
    public List<RoomVo> getRoomsByFloorId(Long floorId) {
        return roomMapper.selectByFloorId(floorId);
    }


    /**
     * 获取所有房间（负责老人）
     *
     * @param floorId
     * @return
     */
    @Override
    public List<RoomVo> getRoomsWithNurByFloorId(Long floorId) {
        return roomMapper.selectByFloorIdWithNur(floorId);
    }

    @Override
    public RoomVo one(Long id) {
        return roomMapper.one(id);
    }

    @Override
    public List<RoomVo> getRoomsWithDeviceByFloorId(Long floorId) {
        //SQL返回的数据是基础数据，找到的是房间、床位、设备（房间|床位）
        List<RoomVo> roomVos = roomMapper.getRoomsWithDeviceByFloorId(floorId);

        roomVos.forEach(roomVo -> {
            List<DeviceInfo> deviceVos = roomVo.getDeviceVos();
            deviceVos.forEach(deviceInfo -> {
                Object o = redisTemplate.opsForHash().get(IOT_DEVICE_LAST_DATA, deviceInfo.getIotId());
                if (o == null) {
                    return;
                }
                List<DeviceData> deviceData = JSON.parseArray(o.toString(), DeviceData.class);
                deviceInfo.setDeviceDataVos(deviceData);
            });

            List<BedVo> bedVoList = roomVo.getBedVoList();
            bedVoList.forEach(bedVo -> {
                bedVo.getDeviceVos().forEach(deviceInfo -> {
                    Object o = redisTemplate.opsForHash().get(IOT_DEVICE_LAST_DATA, deviceInfo.getIotId());
                    if (o == null) {
                        return;
                    }
                    List<DeviceData> deviceData = JSON.parseArray(o.toString(), DeviceData.class);
                    deviceInfo.setDeviceDataVos(deviceData);
                });
            });
        });
        return roomVos;
    }
}
