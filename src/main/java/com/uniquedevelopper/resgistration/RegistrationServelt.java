package com.uniquedevelopper.resgistration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServelt
 */
@WebServlet("/register")
public class RegistrationServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServelt() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nomUser = request.getParameter("nomUser");
		String email = request.getParameter("email");
		String mdp = request.getParameter("mdp");
		String contact = request.getParameter("contact");
		RequestDispatcher dispacher = null;
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstdbjava?useSSL=false", "root", "");
			PreparedStatement pst = con.prepareStatement("insert into user (nomUser,email,mdp,tel) values (?,?,?,?)");
			pst.setString(1, nomUser);
			pst.setString(2, email);
			pst.setString(3, mdp);
			pst.setString(4, contact);
			
			
			int rowCount = pst.executeUpdate();
			dispacher = request.getRequestDispatcher("registration.jsp");
			
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}else{
				request.setAttribute("status", "fail");
			}
			dispacher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
