package u.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.DataSource;

public class changeDetail {
	public HashMap getDetail(DataSource datasource, String changeID) {
		HashMap detailResult = new HashMap();
		Connection con = null;
		String result = "error";

		try {
			con = datasource.getConnection();
			Statement st = con.createStatement();
			ResultSet selectChangeDetail = st
					.executeQuery("select * from exchangedetail where changeID='" + changeID + "'");
			while (selectChangeDetail.next()) {
				result = "success";
				detailResult.put("result", result);
				detailResult.put("nowPeople", selectChangeDetail.getString("participated"));
				detailResult.put("mention", selectChangeDetail.getString("mention"));
				detailResult.put("typeID", selectChangeDetail.getString("typeID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Exception ignore) {
				}
		}

		return detailResult;
	}

	public InputStream getPhoto(DataSource datasource, String changeID, String typeID) {
		PreparedStatement statement = null;
		PreparedStatement defaultStatement = null;
		InputStream inputStream = null;
		Connection conn = null;

		try {
			conn = datasource.getConnection();

			String sql = "SELECT picture FROM exchangedetail WHERE changeID = ? ";

			statement = conn.prepareStatement(sql);
			statement.setString(1, changeID);
			ResultSet resultSet = statement.executeQuery();

			String defaultSql = "select picture from exchangetype where typeID= ? ";
			defaultStatement = conn.prepareStatement(defaultSql);
			defaultStatement.setString(1, typeID);
			ResultSet defaultResultSet = defaultStatement.executeQuery();

			if (resultSet.next()) {
				Blob blob = resultSet.getBlob("picture");
				if (blob != null) {
					inputStream = blob.getBinaryStream();
				} else {
					if (defaultResultSet.next()) {
						blob = defaultResultSet.getBlob("picture");
						inputStream = blob.getBinaryStream();
					}else{
						System.out.println("找不到typeID" + typeID + "的檔案");
					}
				}
				if (resultSet != null)
					try {
						resultSet.close();
					} catch (SQLException ignore) {
					}
			} else {
				System.out.println("找不到changeID" + changeID + "的檔案");
			}

		} catch (SQLException ex) {
			System.out.println("發生SQLException");
		} finally {
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception ignore) {
				}
		}
		return inputStream;
	}
}
