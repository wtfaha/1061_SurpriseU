package u.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.google.gson.Gson;

import u.model.attack;


//@WebServlet("/attackServlet")
public class attackServlet extends HttpServlet {
    public attackServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String type=request.getParameter("type");
		String lowPrice=request.getParameter("lowPrice");
		String highPrice=request.getParameter("highPrice");
		String location=request.getParameter("location");
		String secondHand=request.getParameter("secondHand");
		String maxPeople=request.getParameter("maxPeople");
		
		Gson gson = new Gson();
		HashMap Result = new HashMap();
		
		DataSource datasource = (DataSource) getServletContext().getAttribute("db");
		
		attack at=new attack();
		Result=at.att(datasource, name, type, lowPrice, highPrice, location, secondHand,maxPeople);
		
		System.out.println("Result : " + Result);
		response.getWriter().write(gson.toJson(Result));
	}

}
