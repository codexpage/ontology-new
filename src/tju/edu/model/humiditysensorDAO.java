package tju.edu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tju.edu.db.DBConnection;


public class humiditysensorDAO {
	
	public void insert(humiditysensor hum){
		int sensorid = hum.getSensorid();
		double humidity = hum.getHumidity();
		Connection conn = null;
		PreparedStatement stat = null;
		
		DBConnection.setDbUrl("ontology");
		conn = DBConnection.getConnection();
		String sql = 
				"INSERT INTO humiditysensor(sensorid,humidity) " + 
				"VALUES (?, ?)";
		try {
			stat = conn.prepareStatement(sql);
			stat.setInt(1, sensorid);
			stat.setDouble(2,humidity);
			int res = stat.executeUpdate();			
			assert res == 1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stat != null)
					stat.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
			
		}
	}
	
	
	public double findAll(String database,String relation,int id){
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		double hum = 0;
		
		try {
			DBConnection.setDbUrl(database);//设置数据库名字
			conn = DBConnection.getConnection();
			String sql = 
					"select * from "+relation+" where sensorid="+id
					+ " and id >= all(select id from "+relation+" where sensorid="+id+");";
			//在这里找到sendorid相同的数据中最新的一个

//			System.out.println(sql);
			
			stat = conn.createStatement();	
			rs = stat.executeQuery(sql);
			
			while (rs.next()) {
				hum=rs.getDouble("humidity");
				
			}
			
		} catch (SQLException e) {
		   e.printStackTrace();
		   
		} finally {
			try {
				if (stat != null)
					stat.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return hum;
		
		
	}

}
