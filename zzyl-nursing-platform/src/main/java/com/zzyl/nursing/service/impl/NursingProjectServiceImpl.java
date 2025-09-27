package com.zzyl.nursing.service.impl;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.NursingProjectMapper;
import com.zzyl.nursing.domain.NursingProject;
import com.zzyl.nursing.service.INursingProjectService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



/**
 * 护理项目Service业务层处理
 *
 * @author Euphoria
 * @date 2025-09-27
 */
@Service
public class NursingProjectServiceImpl extends ServiceImpl<NursingProjectMapper, NursingProject> implements INursingProjectService {

    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    /**
     * 查询护理项目
     *
     * @param id 护理项目主键
     * @return 护理项目
     */
    @Override
    public NursingProject selectNursingProjectById(Long id) {
        return getById(id);
    }

    /**
     * 查询护理项目列表
     *
     * @param nursingProject 护理项目
     * @return 护理项目
     */
    @Override
    public List<NursingProject> selectNursingProjectList(NursingProject nursingProject) {
        return nursingProjectMapper.selectNursingProjectList(nursingProject);
    }

    /**
     * 新增护理项目
     *
     * @param nursingProject 护理项目
     * @return 结果
     */
    @Override
    public int insertNursingProject(NursingProject nursingProject) {
        return save(nursingProject) ? 1 : 0;
    }

    /**
     * 修改护理项目
     *
     * @param nursingProject 护理项目
     * @return 结果
     */
    @Override
    public int updateNursingProject(NursingProject nursingProject) {
        return updateById(nursingProject) ? 1 : 0;
    }

    /**
     * 批量删除护理项目
     *
     * @param ids 需要删除的护理项目主键
     * @return 结果
     */
    @Override
    public int deleteNursingProjectByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除护理项目信息
     *
     * @param id 护理项目主键
     * @return 结果
     */
    @Override
    public int deleteNursingProjectById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}
