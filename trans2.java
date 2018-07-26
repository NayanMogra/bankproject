import javax.servlet.*;
 import javax.servlet.http.*;
 import java.sql.*;
 import java.io.*;
 public class trans2 extends HttpServlet
 {
 	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
 	{
 	   res.setContentType("text/html");
 	 	PrintWriter pw=res.getWriter();
	    String ac1=req.getParameter("accno");
	    String ac2=req.getParameter("acno");
	    String a=req.getParameter("amo");
	    String dot=req.getParameter("dot");
	    int a1=Integer.parseInt(ac1);
	    int a2=Integer.parseInt(ac2);
	    int amo=Integer.parseInt(a);
	 	Connection con=null;
	 	PreparedStatement smt=null,smt4=null,smt5=null;
	 	PreparedStatement smt1=null,smt2=null,smt3=null;
	 	ResultSet rs=null,rs1=null,rs2=null;
	 	try
	 	{
	 		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ;
	 		con=DriverManager.getConnection("jdbc:odbc:project");
	 		smt=con.prepareStatement("select * from account where acno=?");
	 		smt.setInt(1,a1);
	 		rs=smt.executeQuery();
	 		int ab;
	 	    HttpSession hs=req.getSession();
			Integer aa=new Integer(hs.getAttribute("cid").toString());
	 		if(rs.next())
	 		{
	 			int com=rs.getInt(4);
	 			smt1=con.prepareStatement("select * from account where acno=?");
	 			smt1.setInt(1,a2);
	 		   	rs1=smt1.executeQuery();
	 			if(rs1.next())
	 			{
	 				ab=rs1.getInt(1);
	 				if(com>=amo)
	 				{
	 					smt1.close();
	 					smt.close();
     					smt2=con.prepareStatement("update account set amo=amo-? where acno=?");
	 					smt2.setInt(1,amo);
	 					smt2.setInt(2,a1);
	 					int l1=smt2.executeUpdate();
	 					smt3=con.prepareStatement("update account set amo=amo+?  where acno=?");
	 					smt3.setInt(1,amo);
	 					smt3.setInt(2,a2);
	 				    int  l2=smt3.executeUpdate();
	 				    if(l1>0 && l2>0)
	 				    {
	 				    	smt4=con.prepareStatement("select * from mntr");
	 				    	rs2=smt4.executeQuery();
	 				    	if(rs2.next())
	 				    	{
	 				    	int tid=rs2.getInt(1);
	 				    	smt5=con.prepareStatement("insert into trans values(?,?,?,?,?,?,?,?,?)");
	 				    	smt5.setInt(1,tid);
	 				    	smt5.setInt(2,aa);
	 				    	smt5.setInt(3,a1);
	 				    	smt5.setString(4,"wd");
	 				    	smt5.setString(5,dot);
	 				    	smt5.setInt(6,amo);
	 				    	smt5.setString(7,"dp");
	 				    	smt5.setInt(8,a2);
	 				    	smt5.setInt(9,ab);
	 				    	int p=smt5.executeUpdate();
	 				    	if(p>0)
	 				    	{
	 				    		pw.println("correct");
	 				    	    smt=con.prepareStatement("update mntr set tid=tid+1");
	 				    	}
	 				    	else
	 				    		{
	 				    			pw.println("error");
	 				    	}
	 				    	}
	 				    	else
	 				    	{
	 				    		pw.println("error");
	 				    	}
	 				    }
	 				    
	 				    else
	 				    {
	 				    	pw.println("error");
	 				    	return;
	 				    }
	 					pw.println("transation done");
	 				}
	 				else
	 					{
	 						pw.println("unsufficnet ammount");
	 				}
	 			}
	 			else
	 				{
	 					pw.println("no such transation account number found");
	 			}
	 		}
	 		else
	 			{
	 				pw.println("No such account found");
	 		}
	 	}
	 	catch(Exception e)
	 	{
	 		pw.println(e.toString());
	 	}
 	}
 }