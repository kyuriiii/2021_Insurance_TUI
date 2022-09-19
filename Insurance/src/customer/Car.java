package customer;

public class Car {

	private String carNum,carType;
	private int carPrice,own; // 0은 무, 1은 유

	public Car(){	}
	// getter & setter
	public String getCarNum() {return carNum;}
	public void setCarNum(String carNum) {	this.carNum = carNum;}
	public int isOwn() {return own;	}
	public void setOwn(int own) {this.own = own;}
	public String getCarType() {return carType;}
	public void setCarType(String carType) {this.carType = carType;}
	public int getCarPrice() {	return carPrice;}
	public void setCarPrice(int carPrice) {this.carPrice = carPrice;}

	public void finalize() throws Throwable {	}

}