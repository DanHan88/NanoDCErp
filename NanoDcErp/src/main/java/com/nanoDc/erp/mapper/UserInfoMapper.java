package com.nanoDc.erp.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nanoDc.erp.vo.UserInfoVO;

@Mapper
public interface UserInfoMapper {
	public List<UserInfoVO> selectUserInfoList();
}
