package u.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.DataSource;

import com.sun.jmx.snmp.SnmpUnknownSubSystemException;

public class attack {
	public HashMap att(DataSource datasource,String name,String type,String lowPrice,String highPrice,String location,String secondHand,String maxPeople){
		
		HashMap attackResult=new HashMap();
		String result="error";
		Connection con=null;
		String changeID="";
		try{
			String max="1";
			String typeID="1";
			con = datasource.getConnection();
			Statement st=con.createStatement();
			ResultSet selectChangeID=st.executeQuery("select changeID from exchange");
			while(selectChangeID.next()){
				if(Integer.parseInt(selectChangeID.getString("changeID"))>=Integer.parseInt(max)){
					max=Integer.toString(Integer.parseInt(selectChangeID.getString("changeID"))+1);
					System.out.println("max:"+max);
				}
			}
			selectChangeID.close();
			changeID=max;
			String insertStateSQL="insert into statetime(stateID,registerTime,startTime,sendTime,receiveTime,objectTime,endTime)"
					+ " values ('"+max+"','0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00')";
			int insertState=st.executeUpdate(insertStateSQL);
			System.out.println("Listener insertState : " + insertState);
			String insertChangeSQL="insert into exchange(changeID,organiser,participant,stateID) values ('"+max+"','1','1','"+max+"')";
			int insertChange=st.executeUpdate(insertChangeSQL);
			System.out.println("Listener insertChange : " + insertChange);
			ResultSet selectTypeID=st.executeQuery("select typeID from exchangetype where typeName='"+type+"'");
			while(selectTypeID.next()){
				typeID=selectTypeID.getString("typeID");
			}
			selectTypeID.close();
			System.out.println("secondHand : " + secondHand);
			String insertChangeDetailSQL="insert into exchangedetail(changeID,title,typeID,lowPrice,highPrice,participated,location,secondHand,maxPeople,mention,picture)"
					+ " values ('"+max+"','"+name+"','"+typeID+"','"+lowPrice+"','"+highPrice+"','0','"+location+"','"+secondHand+"','"+maxPeople+"','','')";
			int insertChangeDetail=st.executeUpdate(insertChangeDetailSQL);
			if(insertChange==1&&insertChangeDetail==1)
				result="success";
			System.out.println("Listener insertChangeDetail : " + insertChangeDetail);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		      if (con!=null) try {con.close();}catch (Exception ignore) {}
		}
		attackResult.put("changeID", changeID);
		attackResult.put("result", result);
		return attackResult;
	}
}
