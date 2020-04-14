package dao;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import model.Food;
import utility.ConnectionManager;

public class FoodData {
	
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int BillNo=1;
	public int selectitem;

	EmployeeAdd eadd=new EmployeeAdd();
	//Generate date
	String pattern = "dd-MM-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	public String date = simpleDateFormat.format(new Date());
	String CustName;
	int foodprice;
	int totalBill;

	ArrayList<Food> Itemlist = new ArrayList<>();//store menu
	ArrayList<String>selectedfood=new ArrayList<>();//Store data in will
	ArrayList<OrderFood>orderfood=new ArrayList<>();//orderd items





	Food food=new Food();

	public void setItemlist(ArrayList<Food> itemlist) {
		Itemlist = itemlist;
	}



	public ArrayList<String> getSelectedfood() {
		return selectedfood;
	}
	public void setSelectedfood(ArrayList<String> selectedfood) {
		this.selectedfood = selectedfood;
	}

	public ArrayList<Food> getItemlist() {
		return Itemlist;
	}
	public ArrayList<OrderFood> getorderfood()
	{
		return orderfood;
	}



	public FoodData()
	{

	}
	//adding food items to database
	
	public void additem(String fname, int price) throws Exception
	{
				
		//sql
		Connection cn = ConnectionManager.getconnection();
		
		
		
			
			String count="select count(*) AS rowcount from food";
			ConnectionManager con=new ConnectionManager();
			Statement st=con.getconnection().createStatement();
			ResultSet rs=st.executeQuery(count);
			rs.next();
			int fid = rs.getInt("rowcount") ;
			rs.close();
			
			
			String insert="insert into FOOD (FNAME,PRICE,ICOUNT,IMONEY,FID) values(?,?,?,?,?)";
			PreparedStatement ps = cn.prepareStatement(insert);
					
			ps.setString(1,fname);
			ps.setInt(2,price);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setInt(5,++fid);
			
			
			
			ps.executeUpdate();
			Itemlist.add(food);
			

		
	}
	
	
	//print food items
	public void displayfood() throws Exception
	{
		String menu="select fname,price from food";
		ConnectionManager con=new ConnectionManager();
		Statement st=con.getconnection().createStatement();
		ResultSet rs=st.executeQuery(menu);
		int spacecount=30;
		System.out.println(" Item Name\t\t     Price");
		System.out.println("***********************************");
		while(rs.next())
		{
			String fname=rs.getString("FNAME");
			int price=rs.getInt("PRICE");
			int len=fname.length();
			System.out.print(fname);
			for(int i=0;i<spacecount-len;i++)
			{
				System.out.print("-");
			}
			System.out.println(price);
		}
		
		System.out.println("******************************************\n\n");
		
		
	}
	
	
	//bill generation
	public void Bill() throws NumberFormatException, IOException
	{
		selectedfood.clear();
		foodprice=0;
		totalBill=0;
		int number;
		OrderFood order=new OrderFood();
		orderfood.add(order);

		System.out.println("Enter Customer Name");
		CustName=br.readLine();

		System.out.println("enter how many item's do you want");
		int ordercount=Integer.parseInt(br.readLine());
		for(int i=0;i<ordercount;i++)
		{
			System.out.println("Select Food Item"+(i+1));

			selectitem=Integer.parseInt(br.readLine());
			System.out.println("enter how many plates dou yo want");
			number=Integer.parseInt(br.readLine());
			Food food=Itemlist.get(selectitem-1);
			//updating item count
			int getitemcount=food.getIcount();
			int updateicount=getitemcount+number;
			food.setIcount(updateicount);
			//System.out.println("item icount value"+food.getIcount());
			//updating money of item
			int getitemmoney=food.getImoney();
			int costitem=food.getPrice()*number;
			int updateimoney=getitemmoney+costitem;
			food.setImoney(updateimoney);
			//System.out.println("earning from item "+food.getImoney());

			//System.out.println("print what is food"+food.getFname());

			order.setOrderFood(food);//changed from Food to food
			foodprice=food.getPrice()*number;
			String itemdata=food.getFname()+"\t\t "+food.getPrice()+"R\t   "+number+"\t\t "+foodprice;
			selectedfood.add(itemdata);

			totalBill+=foodprice;


		}

		String heading="ITEM NAME\tPRICE\tQUANTITY\tCOST";
		order.setBilltotal(totalBill);
		//print food items in bill
		System.out.println("\n\n   <-----Hotel Telugu Ruchulu----->");
		System.out.println("\n\t\t Receipt\t\t\t\n");
		System.out.println("*********************************************\n");
		System.out.println(heading);
		for(String sfood:selectedfood)
			System.out.println(sfood);
		System.out.println("\nTotal Bill Amount is  \t\t\t"+totalBill);
		System.out.println("\nCustomer Name      \t\t\t "+CustName);
		System.out.println("\nDate\t               \t\t\t"+date);
		System.out.println("\n\t<--Thank you visit again-->\n");





	}

	//update price method
	public int EditPrice() throws NumberFormatException, IOException
	{
		System.out.println("Enter Updated Price of Item");
		return Integer.parseInt(br.readLine());
	}
	///updating food cost
	public void UpdateFood() throws Exception
	{
		int itemid;
		boolean data=false;
		
		System.out.println("Enter the Item ID Do you Want To Update");

		itemid=Integer.parseInt(br.readLine());
		System.out.println("Enter the Updated Price");
		int price =Integer.parseInt(br.readLine());
		
		//SQL
		Connection cn = ConnectionManager.getconnection();
		String update="update FOOD set price ="+price+"where fid="+itemid;
		String Foodname="select Fname as itemname  from food where fid="+itemid;
		
		Statement st=cn.createStatement();
		ResultSet rs=st.executeQuery(update);
		try
		{
			data=rs.next();
			
			
		}
		catch(Exception e)
		{
			System.out.println("No item exist with food ID "+itemid);
		}
		
		if(data==true)
		{
			rs=st.executeQuery(Foodname);
			rs.next();
			String 	Fname = rs.getString("itemname") ;
			System.out.println("Price of "+Fname +"is updated to "+price);
		}
		
	
			}


	//Delete food Item from List
	public void DeleteFood() throws Exception
	{
		int itemid;
		boolean delstatus=false;
		
		System.out.println("Enter the Item ID Do you Want To Delete");
		itemid=Integer.parseInt(br.readLine());
		String query="DELETE FROM FOOD WHERE FID="+itemid;
		
		Connection cn = ConnectionManager.getconnection();
		Statement st=cn.createStatement();
		
		ResultSet rs=st.executeQuery(query);
		
		
		
		try
		{
			delstatus=rs.next();
			
			
		}
		catch(Exception e)
		{
			System.out.println("No item exist with food ID "+itemid);
		}
		
		
		

		
		if(delstatus==true)
		{
								
			System.out.println("Item Deleted From Menu");
		}
		
	}


	public void Generatepdf()
	{
		String filename=BillNo+" "+date+CustName+".pdf";
		BillNo++;
		Document document = new Document();
		try
		{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
			document.open();

			document.add(new Paragraph("   <-----Hotel Telugu Ruchulu----->"));
			document.add(new Paragraph("\n             Receipt            \n"));
			document.add(new Paragraph("*******************************************************"));
			document.add(new Paragraph("ITEM NAME    PRICE    QUANTITY    COST"));
			for(String sfood:selectedfood)
				document.add(new Paragraph(sfood));
			document.add(new Paragraph("*******************************************************"));
			document.add(new Paragraph("\nTotal Bill Amount is                          "+totalBill));
			document.add(new Paragraph("\nCustomer Name                                 "+CustName));
			document.add(new Paragraph("\nDate                                          "+date));
			document.add(new Paragraph("\n    <--Thank you visit again-->\n"));



			document.close();
			writer.close();
		} catch (DocumentException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}



	//Generate day report
	public void getreport() throws Exception
	{
		int totalmoney=0;
		int spacecount=30;

		
		System.out.println("*\n\n--------------------------------------------------------------------------*");
		System.out.println("ITEM NAME\t    \tITEM PRICE     TOTAL ORDERS     MONEY");
		System.out.println("*--------------------------------------------------------------------------*");
		Connection cn = ConnectionManager.getconnection();
		String query="select *  from food where icount>=0";
		Statement st=cn.createStatement();
		ResultSet rs=st.executeQuery(query);
		
		while(rs.next())
		{
			String fname=rs.getString("FNAME");
			int price=rs.getInt("PRICE");
			int icount=rs.getInt("ICOUNT");
			int imoney=rs.getInt("IMONEY");
			int len=fname.length();
			System.out.print(fname);
			for(int i=0;i<spacecount-len;i++)
			{
				System.out.print("-");
			}
			System.out.println(price+"----------"+icount+"--------------"+imoney);
			totalmoney+=imoney;
			
		}

		System.out.println("*----------------------------------------------------------------------*");
		System.out.println("Total money\t\t\t\t\t\t"+ totalmoney);
		System.out.println("*----------------------------------------------------------------------*\n\n\n");


	}


	public void displayuser() throws Exception
	{
		int spacecount=20;
		Connection cn = ConnectionManager.getconnection();
		String query="select *  from RUSER ";
		Statement st=cn.createStatement();
		ResultSet rs=st.executeQuery(query);
		System.out.println("\n\n*--------------------------------------------------------------------------*");
		System.out.println("\t\t\tEMPLOYEE  DETAILS OF RESTAURANT");
		System.out.println("*--------------------------------------------------------------------------*");
		System.out.println("USERNAME\t    PASSWORD");
		System.out.println("*--------------------------------------------------------------------------*");
		while(rs.next())
		{
			String name=rs.getString("USERNAME");
			String pass=rs.getString("PASSWORD");
			System.out.print(name);
			int len=name.length();
			for(int i=0;i<spacecount-len;i++)
			{
				System.out.print("-");
			}
			System.out.println(pass);
		
		}
		System.out.println("*--------------------------------------------------------------------------\n\n*");
	}
}
