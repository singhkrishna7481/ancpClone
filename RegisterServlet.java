package clone;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class RegisterServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		{
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String mobileNumber = request.getParameter("mobileNumber");
			String email_id = request.getParameter("email-id");
			if(username!="" && password!="" && mobileNumber!="" && email_id!="")
			{
				try
				{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","krish","KR123");
					PreparedStatement pstmt = con.prepareStatement("INSERT INTO SDETAILS VALUES(?,?,?,?)");
					pstmt.setString(1, username);
					pstmt.setString(2, password);
					pstmt.setString(3, mobileNumber);
					pstmt.setString(4, email_id);
					pstmt.execute();
					pw.println("<script type=\"text/javascript\">");
					pw.println("alert('Registration done');");
					pw.println("window.location.href = 'index.html';");
					pw.println("</script>");
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

}
