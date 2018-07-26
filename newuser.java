import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
public class newuser extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		PrintWriter pw=res.getWriter();
		String arr[]=new String[9];
		arr[1]=req.getParameter("cname");
		arr[2]=req.getParameter("fname");
	        arr[3]=req.getParameter("dob");
		arr[4]=req.getParameter("doa");
		arr[5]=req.getParameter("pcode");
		arr[6]=req.getParameter("panno");
		arr[7]=req.getParameter("adhar");
		arr[8]=req.getParameter("pass");
		Connection con=null;
	 	PreparedStatement smt=null;
	 	PreparedStatement smt1=null;
	 	ResultSet rs=null,rs1=null;
	 	int p=Integer.parseInt(arr[5]);
	 	
	 		try
	 		{	
	 	    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver") ;
	 		con=DriverManager.getConnection("jdbc:odbc:project");
	 		smt1=con.prepareStatement("select * from mncid");
	 		rs1=smt1.executeQuery();
	 	    
	 		
	 		if(rs1.next())
	 		{
	 			arr[0]=rs1.getString(1);
	 			 pw.println("got mncid");		
	 			smt1=con.prepareStatement("update mncid set cid=cid+1");
	 			Integer a=new Integer(arr[0]);
	 			int l1=smt1.executeUpdate();
	 			if(l1>0)
	 			{
	 			pw.println("mncid updated");
	 			 smt=con.prepareStatement("insert into cmaster values(?,?,?,?,?,?,?,?,?)");
	 			 for(int i=0;i<9;i++)
	 			 {if(i==0)
	 			 {smt.setInt(i+1,a.intValue());
	 			HttpSession hs=req.getSession();
	 			 hs.setAttribute("cid",a);
	 			 
	 	    
	 			 }
	 			 	else if(i==5)
	 			 		smt.setInt(i+1,p);
	 			 		else
	 			 			smt.setString(i+1,arr[i]);
	 			 			
		 			 }
                        RequestDispatcher rd=req.getRequestDispatcher("/create.html");
	 	    	rd.forward(req,res);    	
	 			 
	 			}
	 			else 
	 				pw.println("mncid not updated");
	 		}	 
	 			else
	 				pw.println("CID not found");
	 		}
	 	catch(Exception e)		
	 	{pw.println(e.toString()); }		
	}

}