

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class AboutUs
 */
@WebServlet("/AboutUs")
public class AboutUs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AboutUs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      String content = request.getParameter("aboutUsContent");

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/About_us","root","farook");
	            String sql = "INSERT INTO about_us (content) VALUES (?)";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, content);
	            stmt.executeUpdate();

	            stmt.close();
	            conn.close();
	            response.sendRedirect("AboutUs.html");
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().write("Error: " + e.getMessage());
	        }
	    }
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/About_us","root","farook");
        String sql = "SELECT content FROM about_us ORDER BY id DESC LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        response.setContentType("text/html");
        response.getWriter();
        if (rs.next()) {
            response.getWriter().write("<p>" + rs.getString("content") + "</p>");
        } else {
            response.getWriter().write("<p>No content available</p>");
        }
        response.getWriter().write("</div></body></html>");

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Error: " + e.getMessage());
    }
}
}
