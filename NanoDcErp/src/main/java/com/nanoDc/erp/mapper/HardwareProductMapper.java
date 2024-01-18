package com.nanoDc.erp.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nanoDc.erp.vo.HardwareProductVO;

@Mapper
public interface HardwareProductMapper {
	public List<HardwareProductVO> getProductList();
}
