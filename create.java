

import javax.servlet.*;
 import javax.servlet.http.*;
 import java.sql.*;
 import java.io.*;
 public class create extends HttpServlet
 {
 	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
 	{
 		res.setContentType("text/html");
 	 	PrintWriter pw=res.getWriter();
	    String atype=req.getParameter("atype");
	    String a=req.getParameter("amo");
	    int amo=Integer.parseInt(a);
	 	Connection con=null;
	 	PreparedStatement smt=null;
	 	PreparedStatement smt1=null;
	 	int acc;
	 	try
	 		{
	 		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ;
	 		con=DriverManager.getConnection("jdbc:odbc:project");
			smt=con.prepareStatement("select * from mnacno");
			ResultSet r=smt.executeQuery();
			
			if(r.next())
			{
				acc=r.getInt(1);
				smt=con.prepareStatement("update mnacno set acno=acno+1");
				int l=smt.executeUpdate();
				if(l>0)
				{
					HttpSession hs=req.getSession();
					Integer a1=new Integer(hs.getAttribute("cid").toString());
				smt1=con.prepareStatement("insert into account values(?,?,?,?)");
					smt1.setInt(1,a1);
					smt1.setInt(2,acc);
					smt1.setString(3,atype);
					smt1.setInt(4,amo);
					int l1=smt1.executeUpdate();
					if(l1>0)
					{
						pw.println("<table><tr><td>YOur CouSTOMER ID is</td><td>"+a1+"</td></tr><tr><td>YOur Account NO is</td><td>"+acc+"</td></tr></table>");
						pw.println("<a href=localhost:8080/examples/home.html>click here</a>for next process");
					}
					else
						{
						pw.println("Unable To CREate a NEW ACCOUNT");					}	
						}
	
	 	}
	 	else
	 		pw.println("Unable to gen account no");
	 		}	
	 	catch(Exception e)
	 	{	pw.println(e.toString());}
 }
 }