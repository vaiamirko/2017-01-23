package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Confine;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries() {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public static void main(String[] args) {
		List<Country> list ;
		BordersDAO dao = new BordersDAO() ;
		list = dao.loadAllCountries() ;
		for(Country c: list) {
			System.out.println(c);
		}
	}
	
public List<Country> loadCountriesPerAnno(int anno,Map<Integer,Country> MappaIdCountry) {
		
		String sql = "SELECT s.state1no as n1 " + 
				"FROM contiguity AS s  " + 
				"WHERE s.YEAR<= ?   " + 
				"GROUP BY s.state1no   ";
				

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = MappaIdCountry.get(rs.getInt("n1"));
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}


public List<Confine> loadCOnfini (int anno,Map<Integer,Country> MappaIdCountry) {
	
	String sql = "  SELECT s.state1no as n1,s.state2no as n2  " + 
			"FROM contiguity AS s  " + 
			"WHERE s.YEAR<= ?  AND s.conttype='1'  " + 
			"GROUP BY s.state1no,s.state2no";
			

	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		st.setInt(1, anno);
		ResultSet rs = st.executeQuery() ;
		
		List<Confine> list = new LinkedList<Confine>() ;
		
		while( rs.next() ) {
			
			Country c1 = MappaIdCountry.get(rs.getInt("n1"));
			Country c2 = MappaIdCountry.get(rs.getInt("n2"));
			list.add(new Confine(c1, c2));
		}
		
		conn.close() ;
		
		return list ;
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return null ;
}
}
