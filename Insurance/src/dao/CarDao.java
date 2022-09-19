package dao;

import customer.Car;

public interface CarDao {
	//설계의 인터페이스 참고
	public void create(Car car, int customerID);
	public Car retrieveByID( int carID );

}
