package com.nanoDc.erp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nanoDc.erp.vo.HardwareInvestmentVO;

@Mapper
public interface HardwareInvestmentMapper {
	 public List<HardwareInvestmentVO> getInvestmentList();
	
	
	
	
}
