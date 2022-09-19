package dao;

import reward.RewardInfo;

public class DamageDaoImpl extends Dao implements DamageDao{
	
	public DamageDaoImpl() {
		try {
			super.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	@Override
	public void create(RewardInfo rewardInfo) {
      String query = "insert into damageAssess ( accidentID, employeeID, payment, reason ) values ( " +
                  + rewardInfo.getAccident().getAccidentID()  + ", " +
                  " '" + rewardInfo.getEmployee().getEmployeeID() + "', " +
                  " '" + rewardInfo.getPayment()+ "', " +
                  " '" + rewardInfo.getAssessReason()+  "' )";
      try {
         this.execute(query);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
	@Override
	public RewardInfo retrieve() {
		return null;
	}
	@Override
	public void update(RewardInfo rewardInfo) {
		
	}

}
