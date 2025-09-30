package com.zzyl.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
import com.zzyl.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.NursingTaskMapper;
import com.zzyl.nursing.domain.NursingTask;
import com.zzyl.nursing.service.INursingTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * 护理任务Service业务层处理
 * 
 * @author alexis
 * @date 2024-11-17
 */
@Service
public class NursingTaskServiceImpl extends ServiceImpl<NursingTaskMapper, NursingTask> implements INursingTaskService
{
    @Autowired
    private NursingTaskMapper nursingTaskMapper;

    /**
     * 查询护理任务
     * 
     * @param id 护理任务主键
     * @return 护理任务
     */
    @Override
    public NursingTask selectNursingTaskById(Long id)
    {
        return getById(id);
    }

    /**
     * 查询护理任务列表
     * 
     * @param nursingTask 护理任务
     * @return 护理任务
     */
    @Override
    public List<NursingTask> selectNursingTaskList(NursingTask nursingTask)
    {
        return nursingTaskMapper.selectNursingTaskList(nursingTask);
    }

    /**
     * 新增护理任务
     * 
     * @param nursingTask 护理任务
     * @return 结果
     */
    @Override
    public int insertNursingTask(NursingTask nursingTask)
    {
        return save(nursingTask) ? 1 : 0;
    }

    /**
     * 修改护理任务
     * 
     * @param nursingTask 护理任务
     * @return 结果
     */
    @Override
    public int updateNursingTask(NursingTask nursingTask)
    {
        return updateById(nursingTask) ? 1 : 0;
    }

    /**
     * 批量删除护理任务
     * 
     * @param ids 需要删除的护理任务主键
     * @return 结果
     */
    @Override
    public int deleteNursingTaskByIds(Long[] ids)
    {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除护理任务信息
     * 
     * @param id 护理任务主键
     * @return 结果
     */
    @Override
    public int deleteNursingTaskById(Long id)
    {
        return removeById(id) ? 1 : 0;
    }
}
