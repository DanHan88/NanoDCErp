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
	public void updateUser(UserInfoVO userInfoVO);
	public void userPwReset(UserInfoVO userInfoVO);
	public UserInfoVO verifyUserInfoVO(UserInfoVO userInfoVO);
	public String getUserPassword(int i);
	public UserInfoVO selectDetailUserInfoByUserId(int user_id);
}
