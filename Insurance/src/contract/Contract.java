package contract;

import java.util.Calendar;
import java.util.GregorianCalendar;

import customer.Customer;
import insurance.Insurance;

public class Contract {
	private int contractID, price;
	private Customer customer;
	private Insurance insurance;
	private String date, endDate;
	private float insuranceRatio;
	
	public int getContractID() { return contractID; }
	public void setContractID(int contractID) { this.contractID = contractID; }
	public Customer getCustomer() { return customer; }
	public void setCustomer(Customer customer) { this.customer = customer; }
	public Insurance getInsurance() { return insurance; }
	public void setInsurance(Insurance insurance) { this.insurance = insurance; }
	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	public String getEndDate() { return endDate; }
	public void setEndDate(String endDate) { this.endDate = endDate; }
	public float getInsuranceRatio() {  return insuranceRatio; }
	public void setInsuranceRatio(float insuranceRatio) { this.insuranceRatio = insuranceRatio; }

	public float calculateRatio() {
		switch ( insurance.getInsuranceType() ) {
			case "building":				
				if( Integer.parseInt(customer.getM_building().getBuildingPrice()) > 1000000000) insuranceRatio *= 1.2;
				else insuranceRatio *= 1.0;
				
				if (customer.getM_building().getBuildingHeight() > 100) insuranceRatio *= 1.2;
				else if(customer.getM_building().getBuildingHeight() <= 50) insuranceRatio *= 0.9;
				else insuranceRatio *= 1.0;
				
				if(customer.getM_building().getYear() < 2000) insuranceRatio *= 1.3;
				else if ( customer.getM_building().getYear() < 2018 ) insuranceRatio *= 1.1;
				else insuranceRatio *= 0.9;
			break;
			
			case "car":				
				switch(customer.getM_car().getCarType()) {
					case "small": insuranceRatio *= 0.8; break;
					case "freightCar": insuranceRatio *= 1.1; break;
					case "motocycle": insuranceRatio *= 1.2; break;
					case "agricultureCar": insuranceRatio *= 0.9; break;
					default : break;
				}
				//���� ������ ����
				switch(customer.getJob()) {
					case "�л�" : insuranceRatio*= 0.8; break;
					case "�繫��" : insuranceRatio *= 0.9; break;
					case "���" : insuranceRatio *= 1.1; break;
					case "ȭ����������" : insuranceRatio *= 1.2; break;
					case "���������" :insuranceRatio *= 1.3; break;
					default : break;
				}
				//���� ������ ����
				switch(customer.getSex()) {
					case "��" : insuranceRatio *= 1.1; break;
					case "��" : insuranceRatio *= 1.0; break;
				}
			break;
			
			case "driver":
				//�ڵ��� ������ ����
				switch(customer.getM_car().getCarType()) {
					case "small": insuranceRatio *= 0.8; break;
					case "freightCar": insuranceRatio *= 1.1; break;
					case "motocycle": insuranceRatio *= 1.2; break;
					case "agricultureCar": insuranceRatio *= 0.9; break;
					default : break;
				}
				//���� ���̿� ���� 
				int calculateAge = calcualteCustomerAge();
				if ( calculateAge >= 10 && calculateAge < 20 ) insuranceRatio *= 0;
				else if ( calculateAge < 30 ) insuranceRatio *= 1.2;
				else if ( calculateAge < 40 ) insuranceRatio *= 1.1;
				else if ( calculateAge < 50 ) insuranceRatio *= 1.3;
				else if ( calculateAge < 60 ) insuranceRatio *= 1.3;
				else if ( calculateAge < 70 ) insuranceRatio *= 1.3;
				else insuranceRatio *= 1.3;
				
				//���� ������ ����
				switch(customer.getJob()) {
					case "�л�" : insuranceRatio*= 0.8; break;
					case "�繫��" : insuranceRatio *= 0.9; break;
					case "���" : insuranceRatio *= 1.1; break;
					case "ȭ����������" : insuranceRatio *= 1.2; break;
					case "���������" :insuranceRatio *= 1.3; break;
					default : break;
				}
				//���� ������ ����
				switch(customer.getSex()) {
					case "��" : insuranceRatio *= 1.1; break;
					case "��" : insuranceRatio *= 1.0; break;
				}
			break;
		}
		this.setInsuranceRatio(insuranceRatio);
		return this.getInsuranceRatio();
	}

	public int calcualteCustomerAge() {
		String[] strArrary = customer.getCustomerNumber().split("");
		int ageYear;
		if(Integer.parseInt(strArrary[0])!=0) ageYear = 1900 + Integer.parseInt(strArrary[0])* 10 + Integer.parseInt(strArrary[1]);
		else ageYear = 2000 + Integer.parseInt(strArrary[1]);
		GregorianCalendar today = new GregorianCalendar();
		int year = today.get(Calendar.YEAR);
		return year - ageYear;
	}
}
