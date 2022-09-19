package pCustomer;

import java.util.ArrayList;

/**
 * @author kyuri
 * @version 1.0
 * @created 02-5-2021 ¿ÀÀü 10:32:57
 */
public interface PCustomerList {
	public boolean add(PCustomer pCustomer);
	public boolean delete(int customerID);
	public PCustomer search(int customerID);
	public boolean update(PCustomer pCustomer, int customerID);
	public ArrayList<PCustomer> getCustomerList();

}