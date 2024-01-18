package com.nanoDc.erp.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingVO;

@Mapper
public interface HardwareRewardSharingMapper {
	public List<HardwareRewardSharingVO> getRewardSharingList();
}
