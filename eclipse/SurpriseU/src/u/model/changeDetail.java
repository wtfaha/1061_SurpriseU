package u.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.apache.tomcat.util.http.fileupload.FileItem;

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

	public static void uploadPhoto(List<FileItem> items, DataSource datasource,String changeID) throws IOException {

		Connection con = null;
		try {
			con = datasource.getConnection();

			for (FileItem item : items) {

				InputStream is = item.getInputStream(); // 原圖

				// 存圖片到資料庫
				saveFileInfoToDB(con, is,changeID);

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
	}

	// 存檔案資訊到資料庫
	static private void saveFileInfoToDB(Connection conn, InputStream is,String changeID) {

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("update exchangedetail set picture= ? where changeID='"+changeID+"'");	
			ps.setBlob(1, is); // 原圖
			ps.execute();

			if (is != null) {
				is.close();
				// System.out.println("上傳照片InputStream.close()");
			}

		} catch (SQLException e1) {
			System.out.println(e1);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException ignore) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception ignore) {
				}
		}

	}

	public static InputStream getPhoto(DataSource datasource, String changeID, String typeID) {
		PreparedStatement statement = null;
		InputStream inputStream = null;
		Connection conn = null;

		try {
			conn = datasource.getConnection();
			System.out.println("1");
			String sql = "SELECT picture FROM exchangedetail WHERE changeID = ? ";

			statement = conn.prepareStatement(sql);
			statement.setString(1, changeID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Blob blob = resultSet.getBlob("picture");
				System.out.println(blob);
				if (blob != null) {
					inputStream = blob.getBinaryStream();
				} else {
					resultSet.close();
					statement.close();
					System.out.println("1");
					PreparedStatement defaultStatement = null;
					String defaultSql = "select defaultPicture from exchangetype where typeID= ? ";
					defaultStatement = conn.prepareStatement(defaultSql);
					defaultStatement.setString(1, typeID);
					System.out.println("2");
					ResultSet defaultResultSet = defaultStatement.executeQuery();
					System.out.println("3");
					if (defaultResultSet.next()) {
						System.out.println("4");
						blob = defaultResultSet.getBlob("defaultPicture");
						inputStream = blob.getBinaryStream();
						System.out.println("5");

					} else {
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
