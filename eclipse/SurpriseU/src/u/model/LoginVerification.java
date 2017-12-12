package u.model;

import java.sql.*;
import java.util.HashMap;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class LoginVerification {

	//已關資料庫
	public HashMap verification(DataSource datasource, String account, String password) {
		String result="登入失敗", userID=null, userName=null;
		Connection con = null;

		try {
			con = datasource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select userID, account, password, userName from user where account='"+account+"' and password='"+password+"' ");

			while (rs.next()) {
				userID = rs.getString("userID");
				userName = rs.getString("userName");
				result = "登入成功";
			}
			rs.close();//關閉rs

		    st.close();//關閉st			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		      if (con!=null) try {con.close();}catch (Exception ignore) {}
		}
		HashMap loginResult = new HashMap();
		loginResult.put("userID", userID);
		loginResult.put("userName", userName);
		loginResult.put("result", result);
		return loginResult;
	}
}
