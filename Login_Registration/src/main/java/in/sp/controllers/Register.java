package in.sp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import in.sp.dbConn.DBConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("regForm")
public class Register extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String myName = req.getParameter("name1");
		String myEmail = req.getParameter("email1");
		String myPass = req.getParameter("pass1");
		String myCity = req.getParameter("pass1");
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		try {
			Connection con = DBConnection.getConnection();
			
			String insert_sql_query = "INSERT INTO register VALUES(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(insert_sql_query);
			ps.setString(1, myName);
			ps.setString(2, myEmail);
			ps.setString(3, myPass);
			ps.setString(4, myCity);
			
			ps.executeUpdate();
			
			int count = ps.executeUpdate();
			
			if(count > 0) {
				out.println("<h3 style = 'color:green'> Registered Successfully </h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/login.html");
				rd.include(req, resp);
			}else
				out.println("<h3 style = 'color:red'> User not register </h3>");
			
				RequestDispatcher rd = req.getRequestDispatcher("/register.html");
				rd.include(req, resp);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
