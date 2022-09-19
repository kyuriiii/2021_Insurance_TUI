package dao;

import customer.Building;

public interface BuildingDao {
	//설계의 인터페이스 참고
	public void create(Building building, int customerID);
	public Building retrieveByID( int buildingID );

}
