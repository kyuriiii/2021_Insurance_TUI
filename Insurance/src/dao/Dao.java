package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dao {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	//인터페이스
	public void connect() throws Exception {
		//connect
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?serverTimezone=UTC&useSSL=false", "root", "339675"); //채원
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?serverTimezone=UTC&useSSL=false", "root", "1234qwer"); //규리
	//		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/insurance?serverTimezone=UTC&useSSL=false", "root", "1234");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//create
	public void execute(String query) throws Exception {
		try {
			statement = connect.createStatement();
			statement.execute(query);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ResultSet retrieve(String query) throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			return resultSet;
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
