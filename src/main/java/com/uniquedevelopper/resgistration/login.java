package com.uniquedevelopper.resgistration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String mdp = request.getParameter("mdp");
		 
		HttpSession session =request.getSession();
		RequestDispatcher  dispacher = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstdbjava?useSSL=false", "root", "");
			PreparedStatement pst = con.prepareStatement("select * from user where email=? and mdp=?");
			pst.setString(1, email);
			pst.setString(2, mdp);
			
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				session.setAttribute("nomUser", rs.getString("nomUser"));
				dispacher = request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status", "failed");
				dispacher = request.getRequestDispatcher("login.jsp");
			}
			dispacher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();		}
	}

}
