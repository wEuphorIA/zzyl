package com.zzyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.AlertDataMapper;
import com.zzyl.nursing.domain.AlertData;
import com.zzyl.nursing.service.IAlertDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 报警数据Service业务层处理
 * 
 * @author Euphoria
 * @date 2025-10-16
 */
@Service
public class AlertDataServiceImpl extends ServiceImpl<AlertDataMapper, AlertData> implements IAlertDataService
{
    @Autowired
    private AlertDataMapper alertDataMapper;

    /**
     * 查询报警数据
     * 
     * @param id 报警数据主键
     * @return 报警数据
     */
    @Override
    public AlertData selectAlertDataById(Long id)
    {
        return alertDataMapper.selectById(id);
    }

    /**
     * 查询报警数据列表
     * 
     * @param alertData 报警数据
     * @return 报警数据
     */
    @Override
    public List<AlertData> selectAlertDataList(AlertData alertData)
    {
        return alertDataMapper.selectAlertDataList(alertData);
    }

    /**
     * 新增报警数据
     * 
     * @param alertData 报警数据
     * @return 结果
     */
    @Override
    public int insertAlertData(AlertData alertData)
    {
        return alertDataMapper.insert(alertData);
    }

    /**
     * 修改报警数据
     * 
     * @param alertData 报警数据
     * @return 结果
     */
    @Override
    public int updateAlertData(AlertData alertData)
    {
        return alertDataMapper.updateById(alertData);
    }

    /**
     * 批量删除报警数据
     * 
     * @param ids 需要删除的报警数据主键
     * @return 结果
     */
    @Override
    public int deleteAlertDataByIds(Long[] ids)
    {
        return alertDataMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除报警数据信息
     * 
     * @param id 报警数据主键
     * @return 结果
     */
    @Override
    public int deleteAlertDataById(Long id)
    {
        return alertDataMapper.deleteById(id);
    }
}
