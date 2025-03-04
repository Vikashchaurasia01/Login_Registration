package in.sp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import in.sp.dbConn.DBConnection;
import in.sp.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("loginForm")
public class Login extends HttpServlet {
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String myEmail = req.getParameter("email1");
		String myPass = req.getParameter("pass1");
		
		PrintWriter out = resp.getWriter();
		
		try {
			Connection con = DBConnection.getConnection();
			
			String select_sql_query = "SELECT * FROM register WHERE email=? AND password=?";
			PreparedStatement ps = con.prepareStatement(select_sql_query);
			ps.setString(1, myEmail);
			ps.setString(2, myPass);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				
				User user = new User();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setCity(rs.getString("city"));
				
				HttpSession session = req.getSession();
				session.setAttribute("session_user", user);
				
				// out.println("<h3 style='color:green'> Login Successfully </h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
				rd.forward(req, resp);
			}else {
				out.println("<h3 style='color:red'> Please Enter correct Email or Password </h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/login.html");
				rd.include(req, resp);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
