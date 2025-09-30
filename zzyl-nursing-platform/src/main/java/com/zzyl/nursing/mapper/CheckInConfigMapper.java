package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.CheckInConfig;

/**
 * 入住配置Mapper接口
 * 
 * @author Euphoria
 * @date 2025-09-30
 */
@Mapper
public interface CheckInConfigMapper extends BaseMapper<CheckInConfig>
{
    /**
     * 查询入住配置
     * 
     * @param id 入住配置主键
     * @return 入住配置
     */
    public CheckInConfig selectCheckInConfigById(Long id);

    /**
     * 查询入住配置列表
     * 
     * @param checkInConfig 入住配置
     * @return 入住配置集合
     */
    public List<CheckInConfig> selectCheckInConfigList(CheckInConfig checkInConfig);

    /**
     * 新增入住配置
     * 
     * @param checkInConfig 入住配置
     * @return 结果
     */
    public int insertCheckInConfig(CheckInConfig checkInConfig);

    /**
     * 修改入住配置
     * 
     * @param checkInConfig 入住配置
     * @return 结果
     */
    public int updateCheckInConfig(CheckInConfig checkInConfig);

    /**
     * 删除入住配置
     * 
     * @param id 入住配置主键
     * @return 结果
     */
    public int deleteCheckInConfigById(Long id);

    /**
     * 批量删除入住配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCheckInConfigByIds(Long[] ids);
}
