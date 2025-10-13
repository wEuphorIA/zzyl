package com.zzyl.nursing.service;

import java.util.List;

import com.huaweicloud.sdk.iotda.v5.model.ProductSummary;
import com.zzyl.nursing.domain.Device;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 设备Service接口
 * 
 * @author Euphoria
 * @date 2025-10-13
 */
public interface IDeviceService extends IService<Device>
{
    /**
     * 查询设备
     * 
     * @param id 设备主键
     * @return 设备
     */
    public Device selectDeviceById(Long id);

    /**
     * 查询设备列表
     * 
     * @param device 设备
     * @return 设备集合
     */
    public List<Device> selectDeviceList(Device device);

    /**
     * 新增设备
     * 
     * @param device 设备
     * @return 结果
     */
    public int insertDevice(Device device);

    /**
     * 修改设备
     * 
     * @param device 设备
     * @return 结果
     */
    public int updateDevice(Device device);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的设备主键集合
     * @return 结果
     */
    public int deleteDeviceByIds(Long[] ids);

    /**
     * 删除设备信息
     * 
     * @param id 设备主键
     * @return 结果
     */
    public int deleteDeviceById(Long id);

    void syncProductList();

    List<ProductSummary> allProduct();
}
