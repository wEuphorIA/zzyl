package com.zzyl.nursing.service.impl;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.NursingLevelMapper;
import com.zzyl.nursing.domain.NursingLevel;
import com.zzyl.nursing.service.INursingLevelService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



/**
 * 护理等级Service业务层处理
 *
 * @author Euphoria
 * @date 2025-09-27
 */
@Service
public class NursingLevelServiceImpl extends ServiceImpl<NursingLevelMapper, NursingLevel> implements INursingLevelService {

    @Autowired
    private NursingLevelMapper nursingLevelMapper;

    /**
     * 查询护理等级
     *
     * @param id 护理等级主键
     * @return 护理等级
     */
    @Override
    public NursingLevel selectNursingLevelById(Long id) {
        return getById(id);
    }

    /**
     * 查询护理等级列表
     *
     * @param nursingLevel 护理等级
     * @return 护理等级
     */
    @Override
    public List<NursingLevel> selectNursingLevelList(NursingLevel nursingLevel) {
        return nursingLevelMapper.selectNursingLevelList(nursingLevel);
    }

    /**
     * 新增护理等级
     *
     * @param nursingLevel 护理等级
     * @return 结果
     */
    @Override
    public int insertNursingLevel(NursingLevel nursingLevel) {
        return save(nursingLevel) ? 1 : 0;
    }

    /**
     * 修改护理等级
     *
     * @param nursingLevel 护理等级
     * @return 结果
     */
    @Override
    public int updateNursingLevel(NursingLevel nursingLevel) {
        return updateById(nursingLevel) ? 1 : 0;
    }

    /**
     * 批量删除护理等级
     *
     * @param ids 需要删除的护理等级主键
     * @return 结果
     */
    @Override
    public int deleteNursingLevelByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     * 删除护理等级信息
     *
     * @param id 护理等级主键
     * @return 结果
     */
    @Override
    public int deleteNursingLevelById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}
