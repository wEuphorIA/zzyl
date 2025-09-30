package com.zzyl.nursing.service;

import java.util.List;
import com.zzyl.nursing.domain.NursingLevel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzyl.nursing.vo.NursingLevelVo;

/**
 * 护理等级Service接口
 * 
 * @author alexis
 * @date 2024-12-30
 */
public interface INursingLevelService extends IService<NursingLevel>
{
    /**
     * 查询护理等级
     * 
     * @param id 护理等级主键
     * @return 护理等级
     */
    public NursingLevel selectNursingLevelById(Long id);

    /**
     * 查询护理等级列表
     * 
     * @param nursingLevel 护理等级
     * @return 护理等级集合
     */
    public List<NursingLevel> selectNursingLevelList(NursingLevel nursingLevel);

    /**
     * 新增护理等级
     * 
     * @param nursingLevel 护理等级
     * @return 结果
     */
    public int insertNursingLevel(NursingLevel nursingLevel);

    /**
     * 修改护理等级
     * 
     * @param nursingLevel 护理等级
     * @return 结果
     */
    public int updateNursingLevel(NursingLevel nursingLevel);

    /**
     * 批量删除护理等级
     * 
     * @param ids 需要删除的护理等级主键集合
     * @return 结果
     */
    public int deleteNursingLevelByIds(Long[] ids);

    /**
     * 删除护理等级信息
     * 
     * @param id 护理等级主键
     * @return 结果
     */
    public int deleteNursingLevelById(Long id);

    /**
     * 查询护理等级Vo列表
     * @param nursingLevel  条件
     * @return  结果
     */
    List<NursingLevelVo> selectNursingLevelVoList(NursingLevel nursingLevel);
}
