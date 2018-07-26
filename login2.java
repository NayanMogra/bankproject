/**
 * @(#)login2.java
 *
 *
 * @author 
 * @version 1.00 2018//7
 */
 import javax.servlet.*;
 import javax.servlet.http.*;
 import java.sql.*;
 import java.io.*;
 public class login2 extends HttpServlet
 {
 public void  doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	 {
	 	PrintWriter pw=res.getWriter();
	    String c=req.getParameter("cid");
	    Integer cid=new Integer(c);
	 	String pwd=req.getParameter("pwd");
	 	Connection con=null;
	 	PreparedStatement smt=null;
	 	PreparedStatement smt1=null;
	 	try
	 		{
	 		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ;
	 		con=DriverManager.getConnection("jdbc:odbc:project");
	 	    smt=con.prepareStatement("select * from cmaster where cid=? and pwd=?");
	 	    smt.setInt(1,cid.intValue());
	 	    smt.setString(2,pwd);
	 	    ResultSet rs=smt.executeQuery();
	 	   if(rs.next())
	 	    {
	 	    	HttpSession hs=req.getSession();
	 			 hs.setAttribute("cid",cid);
	 	    	RequestDispatcher rd=req.getRequestDispatcher("/ask.html");
	 	    	rd.forward(req,res);    	
	 	    	  
	 	    }
	 	    else
	 	    {
	 	    	pw.println("No such Id / password found");
	 	    }
	 	    
	 	}
	 	
	 	catch(Exception e)
	 	{
	 		pw.println(e.toString());
	 	}
	 }
 }
 


