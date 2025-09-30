package com.zzyl.nursing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzyl.common.core.domain.entity.SysUser;
import com.zzyl.nursing.domain.NursingElder;
import com.zzyl.nursing.vo.NursingNameVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * 护理员老人关联Mapper接口
 *
 * @author ruoyi
 * @date 2024-05-28
 */
@Mapper
public interface NursingElderMapper extends BaseMapper<NursingElder>
{

    @Select("select tu.user_id as userId, tu.nick_name from nursing_elder ne, sys_user tu where ne.elder_id = #{elderId} and tu.user_id = ne.nursing_id and tu.status = 0")
    List<SysUser> selectUserByElderId(Long elderId);

    @Select("")
    List<NursingNameVo> selectNickNameByElderId(@Param("set") Set<Long> elderIds);

    /**
     * 查询护理员老人关联
     *
     * @param id 护理员老人关联主键
     * @return 护理员老人关联
     */
    @Select("")
    public NursingElder selectNursingElderById(Long id);

    /**
     * 查询护理员老人关联列表
     *
     * @param nursingElder 护理员老人关联
     * @return 护理员老人关联集合
     */
    @Select("")
    public List<NursingElder> selectNursingElderList(NursingElder nursingElder);

    /**
     * 新增护理员老人关联
     *
     * @param nursingElder 护理员老人关联
     * @return 结果
     */
    @Insert("")
    public int insertNursingElder(NursingElder nursingElder);

    /**
     * 修改护理员老人关联
     *
     * @param nursingElder 护理员老人关联
     * @return 结果
     */
    @Update("")
    public int updateNursingElder(NursingElder nursingElder);

    /**
     * 删除护理员老人关联
     *
     * @param id 护理员老人关联主键
     * @return 结果
     */
    @Delete("")
    public int deleteNursingElderById(Long id);

    /**
     * 批量删除护理员老人关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @Delete("")
    public int deleteNursingElderByIds(Long[] ids);
}
