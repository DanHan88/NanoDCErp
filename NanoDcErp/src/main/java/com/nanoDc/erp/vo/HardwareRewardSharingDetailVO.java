package com.nanoDc.erp.vo;

import java.util.Date;

public class HardwareRewardSharingDetailVO {

	private UserInfoVO userInfoVO;
	private HardwareRewardSharingVO hardwareRewardSharingVO;
	private HardwareProductVO hardwareProductVO;
	private long reward_sharing_detail_id;
	private long hw_reward_sharing_id;
	private int user_id;
	private double reward_fil;
	
	
	
	public HardwareProductVO getHardwareProductVO() {
		return hardwareProductVO;
	}
	public void setHardwareProductVO(HardwareProductVO hardwareProductVO) {
		this.hardwareProductVO = hardwareProductVO;
	}
	public UserInfoVO getUserInfoVO() {
		return userInfoVO;
	}
	public void setUserInfoVO(UserInfoVO userInfoVO) {
		this.userInfoVO = userInfoVO;
	}
	public HardwareRewardSharingVO getHardwareRewardSharingVO() {
		return hardwareRewardSharingVO;
	}
	public void setHardwareRewardSharingVO(HardwareRewardSharingVO hardwareRewardSharingVO) {
		this.hardwareRewardSharingVO = hardwareRewardSharingVO;
	}
	public long getReward_sharing_detail_id() {
		return reward_sharing_detail_id;
	}
	public void setReward_sharing_detail_id(long reward_sharing_detail_id) {
		this.reward_sharing_detail_id = reward_sharing_detail_id;
	}
	public long getHw_reward_sharing_id() {
		return hw_reward_sharing_id;
	}
	public void setHw_reward_sharing_id(long hw_reward_sharing_id) {
		this.hw_reward_sharing_id = hw_reward_sharing_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getReward_fil() {
		return reward_fil;
	}
	public void setReward_fil(double reward_fil) {
		this.reward_fil = reward_fil;
	}
}
