package dao;

import customer.Driver;

public interface DriverDao {
	//설계의 인터페이스 참고
	public void create(Driver driver, int customerID);
	public Driver retrieveByID( int driverID );

}
