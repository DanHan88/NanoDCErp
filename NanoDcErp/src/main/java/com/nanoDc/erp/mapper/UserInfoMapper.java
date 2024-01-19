package com.nanoDc.erp.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nanoDc.erp.vo.UserInfoVO;

@Mapper
public interface UserInfoMapper {
	public List<UserInfoVO> selectUserInfoList();
	public void addNewUser(UserInfoVO userInfoVO);
	public void addNewUser_pwd(UserInfoVO userInfoVO);
	public int get_last_useriId();
}
