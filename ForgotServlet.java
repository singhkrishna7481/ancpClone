package clone;

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
public class ForgotServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
    public ForgotServlet() {
        super();
    }
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String username = request.getParameter("username");
		String mobileNumber = request.getParameter("mobileNumber");
		if(username!="" && mobileNumber!="")
		{
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","krish","KR123");
				PreparedStatement pstmt = con.prepareStatement("SELECT * FROM SDETAILS WHERE USERNAME=?");
				pstmt.setString(1, username);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next())
				{
					String phone = rs.getString("PNUMBER");
					if(phone.equals(mobileNumber))
					{
						var pass = rs.getString("PASSWORD");
						pw.println("<script type=\"text/javascript\">");
						pw.println("alert('Your Password is "+pass+"');");
						pw.println("window.location.href = 'index.html';");
						pw.println("</script>");
					}
					else
					{
						pw.println("<script type=\"text/javascript\">");
						pw.println("alert('Invalid Details');");
						pw.println("window.location.href = 'forgot.html';");
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
