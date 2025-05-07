

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

/**
 * Servlet implementation class ManageQuery
 */
@WebServlet("/ManageQuery")
public class ManageQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageQuery() {
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Query", "root", "farook");

            String deleteId = request.getParameter("deleteId");
            if (deleteId != null) {
                String deleteSql = "DELETE FROM queries WHERE id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, Integer.parseInt(deleteId));
                deleteStmt.executeUpdate();
            }
            String selectSql = "SELECT * FROM queries";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();
            
            out.println("<h2>Manage Queries</h2>");
            out.println("<a href= Admin.html>click here to return to home</a>");
            out.println("<table border='1' style='width:100%; text-align:left;'>");
            out.println("<tr><th>Name</th><th>Email</th><th>Query</th><th>Actions</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String query = rs.getString("query");

                out.println("<tr>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + query + "</td>");
                out.println("<td><a href='ManageQuery?deleteId=" + id + "'>Delete</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
            conn.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}

