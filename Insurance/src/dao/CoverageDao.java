package dao;

import insurance.Coverage;

public interface CoverageDao {
	//설계의 인터페이스 참고
	public int create(Coverage coverage);
	public Coverage retrieveByID( int coverageID );

}
