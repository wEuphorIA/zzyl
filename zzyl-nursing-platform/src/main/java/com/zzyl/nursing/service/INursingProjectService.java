package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.NursingProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzyl.nursing.vo.NursingProjectVo;

/**
 * 护理项目Service接口
 * 
 * @author alexis
 * @date 2024-12-30
 */
public interface INursingProjectService extends IService<NursingProject>
{
    /**
     * 查询护理项目
     * 
     * @param id 护理项目主键
     * @return 护理项目
     */
    public NursingProject selectNursingProjectById(Long id);

    /**
     * 查询护理项目列表
     * 
     * @param nursingProject 护理项目
     * @return 护理项目集合
     */
    public List<NursingProject> selectNursingProjectList(NursingProject nursingProject);

    /**
     * 新增护理项目
     * 
     * @param nursingProject 护理项目
     * @return 结果
     */
    public int insertNursingProject(NursingProject nursingProject);

    /**
     * 修改护理项目
     * 
     * @param nursingProject 护理项目
     * @return 结果
     */
    public int updateNursingProject(NursingProject nursingProject);

    /**
     * 批量删除护理项目
     * 
     * @param ids 需要删除的护理项目主键集合
     * @return 结果
     */
    public int deleteNursingProjectByIds(Long[] ids);

    /**
     * 删除护理项目信息
     * 
     * @param id 护理项目主键
     * @return 结果
     */
    public int deleteNursingProjectById(Long id);

    /**
     * 查询所有护理项目VO列表
     * @return  结果
     */
    List<NursingProjectVo> getAllProjects();

}
