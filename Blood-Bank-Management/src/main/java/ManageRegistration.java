

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class ManageRegistration
 */
@WebServlet("/ManageRegistration")
public class ManageRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/BloodBankUsers","root","farook");

            String deleteId = request.getParameter("deleteId");
            if (deleteId != null) {
                try {
                	String deleteSql = "DELETE FROM users WHERE id = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                    deleteStmt.setInt(1, Integer.parseInt(deleteId));
                    deleteStmt.executeUpdate();                
                } catch (NumberFormatException e) {
                    out.println("<p style='color: red;'>Invalid ID format. Please use a valid number.</p>");
                }            }
 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            out.println("<h2>Manage Blood Donars</h2>");
            out.println("<a href= Admin.html>click here to return to home</a>");
            out.println("<table border='1' style='width:100%'; text-align: left;>");
            out.println("<tr><th>Name</th><th>Age</th><th>Blood Group</th><th>City</th><th>Contact</th><th>Email</th><th>Actions</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("blood_group") + "</td>");
                out.println("<td>" + rs.getString("city") + "</td>");
                out.println("<td>" + rs.getString("contact_details") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>");
                out.println("<a href='ManageRegistration?deleteId=" + id + "' style='color: red;'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            conn.close();
        } catch (Exception e) {
            out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}