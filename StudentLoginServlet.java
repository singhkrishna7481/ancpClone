package clone;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
public class StudentLoginServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
    public StudentLoginServlet() {
        super();
    }
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		String userRequest = request.getParameter("studentLogin");
		PrintWriter pw = response.getWriter();
		pw.println(userRequest);
		String username = request.getParameter("studentUsername");
		String password = request.getParameter("studentPassword");
		
		if(username!="" && password!="")
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","krish","KR123");
				PreparedStatement pstmt = con.prepareStatement("SELECT PASSWORD FROM SDETAILS WHERE USERNAME=?");
				pstmt.setString(1, username);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					String pass = rs.getString(1);
					if(pass.equals(password))
					{
						File htmlFile = new File("K:\\core java prgms\\ANCPclone\\src\\main\\webapp\\StudentPage.html");
						Desktop.getDesktop().browse(htmlFile.toURI());
						pw.println("<script type=\"text/javascript\">");
						pw.println("alert('Login Success');");
						pw.println("window.location.href = 'index.html';");
						pw.println("</script>");
					}
					else
					{
						pw.println("<script type=\"text/javascript\">");
						pw.println("alert('Invalid Password');");
						pw.println("window.location.href = 'index.html';");
						pw.println("</script>");
					}
				}
				else
				{
					pw.println("<script type=\"text/javascript\">");
					pw.println("alert('Student Not Found');");
					pw.println("window.location.href = 'index.html';");
					pw.println("</script>");
				}
			} catch (Exception e) 
			{
				pw.println(e.getMessage());
			}
		}
		else
		{
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Invalid Details');");
			pw.println("window.location.href = 'index.html';");
			pw.println("</script>");
		}
	}

}
