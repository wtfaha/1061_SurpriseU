package u.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.google.gson.Gson;

import u.model.HomepageServer;


public class HomepageServlet extends HttpServlet {
	private static final int BUFFER_SIZE = 4096; 

    public HomepageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  	
		String state = request.getParameter("state");     
        String changeID = request.getParameter("changeID");
       
        Gson gson = new Gson();
		HashMap getHomepageInfo = new HashMap();
		
		
        //資料庫連線
        DataSource datasource = (DataSource) getServletContext().getAttribute("db");

        //查詢首頁要顯示的所有資訊
        if(state.equals("getHomepageInfo")){
			getHomepageInfo = HomepageServer.getHomepageInfo(datasource);
	
			// 回傳json型態
			System.out.println("getHomepageInfo : " + getHomepageInfo);
			response.getWriter().write(gson.toJson(getHomepageInfo));
		}
        //顯示圖片
        else if(state.equals("getPhoto")){	
        	response.setContentType("image/*");
        	
        	InputStream inputStream = null;
        	OutputStream outStream = null;
	        inputStream = HomepageServer.getPhoto(datasource, changeID);
	       	outStream = response.getOutputStream();
	       	byte[] buffer = new byte[BUFFER_SIZE];
	       	int bytesRead = -1;
	       	
	    	if(inputStream!=null){     
		     	while ((bytesRead = inputStream.read(buffer)) != -1) {
		        	outStream.write(buffer, 0, bytesRead);
		     	}
	    	}
	    	if (inputStream != null){
        		inputStream.close(); 
	    	}
	    	if (outStream != null) {
        		outStream.close();
	    	}
        }
        
        
        
        
        
        
        
        
        
        
	}

}
