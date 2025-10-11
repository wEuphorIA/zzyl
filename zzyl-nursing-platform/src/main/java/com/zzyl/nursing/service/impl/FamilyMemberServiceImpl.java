package com.zzyl.nursing.service.impl;

import java.util.*;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzyl.common.utils.StringUtils;
import com.zzyl.framework.web.service.TokenService;
import com.zzyl.nursing.dto.UserLoginRequestDto;
import com.zzyl.nursing.service.WechatService;
import com.zzyl.nursing.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.FamilyMemberMapper;
import com.zzyl.nursing.domain.FamilyMember;
import com.zzyl.nursing.service.IFamilyMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 老人家属Service业务层处理

 @author Euphoria
 @date 2025-10-11 */
@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember> implements IFamilyMemberService {

    @Autowired
    private FamilyMemberMapper familyMemberMapper;

    @Autowired
    private WechatService wechatService;

    @Resource
    private TokenService tokenService;

    static List<String> DEFAULT_NICKNAME_PREFIX = ListUtil.of(
            "生活更美好",
            "大桔大利",
            "日富一日",
            "好柿开花",
            "柿柿如意",
            "一椰暴富",
            "大柚所为",
            "杨梅吐气",
            "天生荔枝"
    );

    /**
     查询老人家属

     @param id 老人家属主键
     @return 老人家属
     */
    @Override
    public FamilyMember selectFamilyMemberById(Long id) {
        return familyMemberMapper.selectById(id);
    }

    /**
     查询老人家属列表

     @param familyMember 老人家属
     @return 老人家属
     */
    @Override
    public List<FamilyMember> selectFamilyMemberList(FamilyMember familyMember) {
        return familyMemberMapper.selectFamilyMemberList(familyMember);
    }

    /**
     新增老人家属

     @param familyMember 老人家属
     @return 结果
     */
    @Override
    public int insertFamilyMember(FamilyMember familyMember) {
        return familyMemberMapper.insert(familyMember);
    }

    /**
     修改老人家属

     @param familyMember 老人家属
     @return 结果
     */
    @Override
    public int updateFamilyMember(FamilyMember familyMember) {
        return familyMemberMapper.updateById(familyMember);
    }

    /**
     批量删除老人家属

     @param ids 需要删除的老人家属主键
     @return 结果
     */
    @Override
    public int deleteFamilyMemberByIds(Long[] ids) {
        return familyMemberMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     删除老人家属信息

     @param id 老人家属主键
     @return 结果
     */
    @Override
    public int deleteFamilyMemberById(Long id) {
        return familyMemberMapper.deleteById(id);
    }

    @Override
    public LoginVo login(UserLoginRequestDto loginRequestDto) {

        //获取openid
        String openid = wechatService.getOpenid(loginRequestDto.getCode());

        //根据openid查询用户信息
        LambdaQueryWrapper<FamilyMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FamilyMember::getOpenId,openid);
        FamilyMember familyMember = familyMemberMapper.selectOne(queryWrapper);

        if (Objects.isNull(familyMember)) {
            familyMember = FamilyMember.builder().openId(openid).build();
        }

        //获取手机号
        String phone = wechatService.getPhone(loginRequestDto.getPhoneCode());

        familyMember.setPhone(phone);
        //注册用户
        this.saveOrUpdate(familyMember);

        String nickName = DEFAULT_NICKNAME_PREFIX.get((int) (Math.random() * DEFAULT_NICKNAME_PREFIX.size()))
                + StringUtils.substring(familyMember.getPhone(), 7);
        familyMember.setName(nickName);

        //返回jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", familyMember.getId());
        claims.put("nickName", familyMember.getName());

        String token = tokenService.createToken(claims);

        return LoginVo.builder()
                .token(token)
                .nickName(familyMember.getName())
                .build();
    }
}
