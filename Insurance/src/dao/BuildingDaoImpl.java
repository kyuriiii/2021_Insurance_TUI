package dao;

import customer.Building;

public class BuildingDaoImpl extends Dao implements BuildingDao{
	
	public BuildingDaoImpl() {
		try {
			super.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void create(Building building, int customerID) {
		String query = "insert into buildinginfo ( customerID, buildingPrice, buildingSize, buildingYear, buildingOutWall, buildingHeight ) values ( " +
				customerID + ", " +
				" '" + building.getBuildingPrice() + "', " +
				" '" + building.getBuildingSize()+ "', " +
				" '" + building.getYear()+ "', " +
				" '" + building.getBuildingOutwall() + "', " +
				building.getBuildingHeight() +")";

		try {
			this.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Building retrieveByID(int buildingID) {
		
		return null;
	}

}
