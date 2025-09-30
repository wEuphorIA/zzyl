package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.NursingTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 护理任务Service接口
 * 
 * @author alexis
 * @date 2024-11-17
 */
public interface INursingTaskService extends IService<NursingTask>
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
     * 批量删除护理任务
     * 
     * @param ids 需要删除的护理任务主键集合
     * @return 结果
     */
    public int deleteNursingTaskByIds(Long[] ids);

    /**
     * 删除护理任务信息
     * 
     * @param id 护理任务主键
     * @return 结果
     */
    public int deleteNursingTaskById(Long id);
}
