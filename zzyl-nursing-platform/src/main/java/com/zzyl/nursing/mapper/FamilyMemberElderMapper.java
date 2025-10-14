package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.nursing.vo.FamilyMemberPageVo;
import com.zzyl.nursing.vo.FamilyMemberVo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.FamilyMemberElder;

/**
 * 客户老人关联Mapper接口
 * 
 * @author Euphoria
 * @date 2025-10-14
 */
@Mapper
public interface FamilyMemberElderMapper extends BaseMapper<FamilyMemberElder>
{
    /**
     * 查询客户老人关联
     * 
     * @param id 客户老人关联主键
     * @return 客户老人关联
     */
    public FamilyMemberElder selectFamilyMemberElderById(Long id);

    /**
     * 查询客户老人关联列表
     * 
     * @param familyMemberElder 客户老人关联
     * @return 客户老人关联集合
     */
    public List<FamilyMemberElder> selectFamilyMemberElderList(FamilyMemberElder familyMemberElder);

    /**
     * 新增客户老人关联
     * 
     * @param familyMemberElder 客户老人关联
     * @return 结果
     */
    public int insertFamilyMemberElder(FamilyMemberElder familyMemberElder);

    /**
     * 修改客户老人关联
     * 
     * @param familyMemberElder 客户老人关联
     * @return 结果
     */
    public int updateFamilyMemberElder(FamilyMemberElder familyMemberElder);

    /**
     * 删除客户老人关联
     * 
     * @param id 客户老人关联主键
     * @return 结果
     */
    public int deleteFamilyMemberElderById(Long id);

    /**
     * 批量删除客户老人关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyMemberElderByIds(Long[] ids);

    List<FamilyMemberVo> my(Long userId);

    List<FamilyMemberPageVo> listByPage(Long userId);
}
