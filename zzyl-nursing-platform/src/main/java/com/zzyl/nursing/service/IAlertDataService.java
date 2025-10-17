package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.AlertData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzyl.nursing.dto.AlertProcessResultDTO;

/**
 * 报警数据Service接口
 * 
 * @author Euphoria
 * @date 2025-10-16
 */
public interface IAlertDataService extends IService<AlertData>
{
    /**
     * 查询报警数据
     * 
     * @param id 报警数据主键
     * @return 报警数据
     */
    public AlertData selectAlertDataById(Long id);

    /**
     * 查询报警数据列表
     * 
     * @param alertData 报警数据
     * @return 报警数据集合
     */
    public List<AlertData> selectAlertDataList(AlertData alertData);

    /**
     * 新增报警数据
     * 
     * @param alertData 报警数据
     * @return 结果
     */
    public int insertAlertData(AlertData alertData);

    /**
     * 修改报警数据
     * 
     * @param alertData 报警数据
     * @return 结果
     */
    public int updateAlertData(AlertData alertData);

    /**
     * 批量删除报警数据
     * 
     * @param ids 需要删除的报警数据主键集合
     * @return 结果
     */
    public int deleteAlertDataByIds(Long[] ids);

    /**
     * 删除报警数据信息
     * 
     * @param id 报警数据主键
     * @return 结果
     */
    public int deleteAlertDataById(Long id);

    void handleAlertData(AlertProcessResultDTO dto);
}
