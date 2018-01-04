package u.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.tomcat.jdbc.pool.DataSource;

public class HomepageServer {
	
	//已關資料庫
	public static HashMap getHomepageInfo(DataSource datasource) {
		Connection con = null;
		ArrayList changeIDList = new ArrayList();
		ArrayList titleList = new ArrayList();
		HashMap getHomepageInfo = new HashMap();

		try {
			con = datasource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT changeID, title from exchangedetail NATURAL JOIN exchange NATURAL JOIN statetime WHERE endTime='0' ");

			while (rs.next()) {
				changeIDList.add(rs.getString("changeID"));
				titleList.add(rs.getString("title"));
			}
			rs.close();//關閉rs

		    st.close();//關閉st			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		      if (con!=null) try {con.close();}catch (Exception ignore) {}
		}
		getHomepageInfo.put("changeIDList", changeIDList);
		getHomepageInfo.put("titleList", titleList);
		return getHomepageInfo;
	}
	
	//顯示圖片
	static public InputStream getPhoto(DataSource datasource, String changeID){
		PreparedStatement statement = null;
		InputStream inputStream = null;
		Connection conn = null;

		try {
			conn = datasource.getConnection();

            String sql = "SELECT picture FROM exchangedetail WHERE changeID = ? ";

            statement = conn.prepareStatement(sql);
            statement.setString(1, changeID);
            ResultSet resultSet = statement.executeQuery();
            
            
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob("picture");
                if(blob!=null){
                	inputStream = blob.getBinaryStream();
                }
                if (resultSet != null) try {resultSet.close();} catch (SQLException ignore) {}          
            }
            else {
            	System.out.println("找不到changeID"+ changeID+"的檔案");
            }
            
        }
		catch (SQLException ex) {
        	System.out.println("發生SQLException");
        }
		finally {
            if (statement != null) try {statement.close();}catch (SQLException ignore) {}
            if (conn!=null) try {conn.close();}catch (Exception ignore) {}
        }
		return inputStream; 
	}

	public static void main(String[] args) {
		// TODO 自動產生的方法 Stub

	}

}
