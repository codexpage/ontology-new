package tju.edu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tju.edu.db.DBConnection;

public class sensorDAO {

	public void insert(sensor sen){//根据sen来判断写入的relation和value
		//需要设置sensorid-第几号传感器 value-值统一double型 name-名称 
		int sensorid = sen.getSensorid();
		double value = sen.getValue();
//	    int type = sen.getType();
	    String name = sen.getName();
		
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			DBConnection.setDbUrl("ontology");// the name should read from database or owl
			conn = DBConnection.getConnection();
			//这里插入id和值 
			String sql = "insert into "+ name +"sensor(sensorid,"+name+") values(?,?)";
//		System.out.println("sql:"+sql);
			stat = conn.prepareStatement(sql);
			stat.setInt(1, sensorid);
			stat.setDouble(2, value);
			System.out.println(stat);
			int res = stat.executeUpdate();			
			assert res == 1;
		} catch (SQLException e) {
			// TODO: handle exception
			System.err.println("insert error");
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
	
	public void read(sensor sen){//读值的时候必须要传入sensorid和name
		int sensorid = sen.getSensorid();
		String name = sen.getName();
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		double value = 0;
		try {
			DBConnection.setDbUrl("ontology");//immutable database
			conn = DBConnection.getConnection();
//			选取id最大（最新）的某个id的传感器的值
			String sql = "select * from "+name+"sensor where sensorid="+sensorid+" and id >="
					+ " all(select id from "+name+"sensor where sensorid="+sensorid+");";
			stat = conn.prepareStatement(sql);
			System.out.println(stat);
//			sql = "select * from temperaturesensor where sensorid=2 and id >= all(select id from temperaturesensor where sensorid=2);";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				value = rs.getDouble(name);//列名
				sen.setValue(value);
//				System.out.println("senvalue: "+sen.getValue());
				
			}
			
			
		} catch (Exception e) {
			System.err.println("read error");
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
	
//	public void read(temperaturesensor temp) {
//		int sensorid=temp.getSensorid();
//		Connection conn = null;
//		PreparedStatement stat = null;
//		try {
//			conn = DBConnection.getConnection();
//			String sql = 
//					"select * from tempsensor where sensorid=? and id >= all(select id from tempsensor where sensorid=?);";
//			stat = conn.prepareStatement(sql);
//			stat.setInt(1, sensorid);
//			stat.setInt(2, sensorid);
//			int res = stat.executeUpdate();			
//			//todo...
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//			
//	}
//	
//	public int findAll(String database,String relation,int id) {		
//		Connection conn = null;
//		Statement stat = null;
//		ResultSet rs = null;
//		int tmp = 0;
//		try {
//			DBConnection.setDbUrl(database);
//			conn = DBConnection.getConnection();
//			String sql = 
//					"select * from "+relation+" where sensorid="+id
//					+ " and id >= all(select id from "+relation+" where sensorid="+id+");";
//			//在这里找到sendorid相同的数据中最新的一个
//
////			System.out.println(sql);
//			stat = conn.createStatement();	
//			rs = stat.executeQuery(sql);
//			
//			while (rs.next()) {
//				tmp=rs.getInt("temperature");
//				
//			}
//			
//		} catch (SQLException e) {
//		   e.printStackTrace();
//		   
//		} finally {
//			try {
//				if (stat != null)
//					stat.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//			}
//		}
//		return tmp;
//	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sensor s = new sensor();
		s.setName("temperature");
		s.setSensorid(2);
//		s.setValue(32.0);
		sensorDAO sDAO = new sensorDAO();
		sDAO.read(s);
		System.out.println(s.getValue());
		
	}

}
