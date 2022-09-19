package dao;

import reward.RewardInfo;

public interface DamageDao {
	public void create(RewardInfo rewardInfo);
	public RewardInfo retrieve();
	public void update (RewardInfo rewardInfo);
}
