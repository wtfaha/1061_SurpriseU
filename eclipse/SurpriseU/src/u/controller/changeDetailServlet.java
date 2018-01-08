package u.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.google.gson.Gson;

import u.model.changeDetail;

//@WebServlet("/changeDetailServlet")
public class changeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;

	public changeDetailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String state = request.getParameter("state");
		String changeID = request.getParameter("changeID");

		Gson gson = new Gson();
		HashMap Result = new HashMap();

		DataSource datasource = (DataSource) getServletContext().getAttribute("db");
		changeDetail detail = new changeDetail();
		if (state.equals("getDetail")) {
			Result = detail.getDetail(datasource, changeID);
			System.out.println("Result : " + Result);
			response.getWriter().write(gson.toJson(Result));
		} else if (state.equals("getPhoto")) {
			response.setContentType("image/*");
			
			InputStream inputStream = null;
			OutputStream outStream = null;
			
			inputStream = changeDetail.getPhoto(datasource, changeID);
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
				//System.out.println("顯示照片inputStream.close()");
			}
			
			if (outStream != null) {
				outStream.close();
				//System.out.println("顯示照片outStream.close()");
			}          
		}

	}

}
