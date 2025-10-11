package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.zzyl.nursing.domain.FamilyMember;

/**
 * 老人家属Mapper接口
 * 
 * @author Euphoria
 * @date 2025-10-11
 */
@Mapper
public interface FamilyMemberMapper extends BaseMapper<FamilyMember>
{
    /**
     * 查询老人家属
     * 
     * @param id 老人家属主键
     * @return 老人家属
     */
    public FamilyMember selectFamilyMemberById(Long id);

    /**
     * 查询老人家属列表
     * 
     * @param familyMember 老人家属
     * @return 老人家属集合
     */
    public List<FamilyMember> selectFamilyMemberList(FamilyMember familyMember);

    /**
     * 新增老人家属
     * 
     * @param familyMember 老人家属
     * @return 结果
     */
    public int insertFamilyMember(FamilyMember familyMember);

    /**
     * 修改老人家属
     * 
     * @param familyMember 老人家属
     * @return 结果
     */
    public int updateFamilyMember(FamilyMember familyMember);

    /**
     * 删除老人家属
     * 
     * @param id 老人家属主键
     * @return 结果
     */
    public int deleteFamilyMemberById(Long id);

    /**
     * 批量删除老人家属
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyMemberByIds(Long[] ids);
}
