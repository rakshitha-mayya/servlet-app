package com.uniquedeveloper.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistretionServlet
 */
@WebServlet("/register")
public class RegistretionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		String urepass = request.getParameter("re_pass");
		RequestDispatcher dispatcher = null;
		
		Connection con = null;
		
		if (uname.isEmpty() || upwd.isEmpty() || uemail.isEmpty() || umobile.isEmpty()) {
            
			
            request.setAttribute("errorMessage", "error");            
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
            
            
        } else if ( upwd.equals(urepass)){
        	
        	try {
        		Class.forName("com.mysql.cj.jdbc.Driver");
        		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?useSSL=false","root","rakshitha");
        		PreparedStatement pstr = con.prepareStatement("select * from user where uemail = ?");
    			pstr.setString(1,uemail);
    			//pstr.setString(2,upwd);
    			
    			ResultSet rs = pstr.executeQuery();
    			if(rs.next()) {
    				request.setAttribute("status1", "failed");
    				dispatcher = request.getRequestDispatcher("registration.jsp");
    				dispatcher.forward(request, response);
    				
    				
    			}else {
    				try {
    					
    					
    	        		Class.forName("com.mysql.cj.jdbc.Driver");
    	            	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?useSSL=false","root","rakshitha");
    	        		PreparedStatement pst = con.prepareStatement("insert into user(uname,upwd,uemail,umobile) values(?,?,?,?)");
    	        		pst.setString(1,uname);
    	        		pst.setString(2,upwd);
    	        		pst.setString(3,uemail);
    	        		pst.setString(4,umobile);
    				
    	        		int rowCount = pst.executeUpdate();	
    				
    	        		dispatcher = request.getRequestDispatcher("registration.jsp");
    	        		if (rowCount > 0) {
    	        			request.setAttribute("status","success");
    	        		}else {
    	        			request.setAttribute("status","failed");
    	        		}
    	        		dispatcher.forward(request, response);
    	        		
    	        		
    	        	}catch(Exception e) {
    	        		e.printStackTrace();
    	        	}finally {
    	        		try {
    	        			con.close();
    	        		}catch(SQLException e) {
    	        			e.printStackTrace();
    	        		}
    	        	}		
    			}
    			
    			
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        	else {
        	request.setAttribute("status2", "error");            
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
	}
}
