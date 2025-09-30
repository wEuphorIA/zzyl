package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.NursingTask;

/**
 * 护理任务Mapper接口
 * 
 * @author alexis
 * @date 2024-11-17
 */
@Mapper
public interface NursingTaskMapper extends BaseMapper<NursingTask>
{
    /**
     * 查询护理任务
     * 
     * @param id 护理任务主键
     * @return 护理任务
     */
    public NursingTask selectNursingTaskById(Long id);

    /**
     * 查询护理任务列表
     * 
     * @param nursingTask 护理任务
     * @return 护理任务集合
     */
    public List<NursingTask> selectNursingTaskList(NursingTask nursingTask);

    /**
     * 新增护理任务
     * 
     * @param nursingTask 护理任务
     * @return 结果
     */
    public int insertNursingTask(NursingTask nursingTask);

    /**
     * 修改护理任务
     * 
     * @param nursingTask 护理任务
     * @return 结果
     */
    public int updateNursingTask(NursingTask nursingTask);

    /**
     * 删除护理任务
     * 
     * @param id 护理任务主键
     * @return 结果
     */
    public int deleteNursingTaskById(Long id);

    /**
     * 批量删除护理任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNursingTaskByIds(Long[] ids);
}
