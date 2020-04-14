/**
 * 
 */
package controller;

import java.io.*;

import dao.EmployeeAdd;
import dao.FoodData;
import dao.LoginDAO;
import model.*;

/**
 * @author haris
 *
 */




public class Main {


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		FoodData Fooddata=new FoodData();
		Food food =new Food();
		User user=new User();
		UserLogin userlogin= new UserLogin();
		LoginDAO logindao=new LoginDAO();
		EmployeeAdd employeeadd=new EmployeeAdd();
		
		int select;
		Boolean flag=false;
		Boolean eflag=false;

		do {
			Mainmenu();

			select=Integer.parseInt(br.readLine()); 
			switch(select)
			{
			case 1://admin menu display 
				do {
					if(flag!=true)
					{
						if(user.adminlogin())
						{
							flag=true;
							System.out.println("Login successfully\n\n");
						}
						else
						{
							System.out.println("Please Enter Correct username or password");
						}

					}	
					if(flag==true)
					{
						Adminmenu();
						select=Integer.parseInt(br.readLine()); 
						switch(select)
						{
						case 1:
							
							System.out.println("How Many Food Items Do You Want To Add?");
							int n=Integer.parseInt(br.readLine());
							
							for(int i=0;i<n;i++)
							{
								System.out.println("Enter Food Item Name");
								String Fname=br.readLine();
								System.out.println("Enter Price of "+ Fname);
								int price=Integer.parseInt(br.readLine());
								
								food.setFname(Fname);
								food.setPrice(price);
								Fooddata.additem(Fname,price);
							}
							
							
							
							break;
						case 2:
							Fooddata.UpdateFood();
							break;
						case 3:
							Fooddata.DeleteFood();
							break;
						case 4:
							Fooddata.getreport();
							break;
						case 5:
							System.out.println("Enter Employee name");
							String name=br.readLine();
							System.out.println("Enter Employee password");
							String pass=br.readLine();
							
							user.setUsername(name);
							user.setPassword(pass);
							
							
							employeeadd.signUp(user.getUsername(),user.getPassword());
							break;
						case 6:
							Fooddata.displayuser();
							break;
						case 0:System.out.println("back menu");
						break;

						default: break;

						}


					}

				}
				while (select !=0);
				break;
			case 2:
				
				
				System.out.println("Enter the user name");
				String name=br.readLine();
				System.out.println("Enter password");
				String pass=br.readLine();
				
				userlogin.setUsername(name);
				userlogin.setPassword(pass);
				System.out.println(userlogin.getUsername());
				
				

				
				do {
					if(eflag!=true)
					{
						if(logindao.Validate(userlogin)==true)
						{
							eflag=true;
							System.out.println("Login successfully\n\n");
						}
						else
						{
							System.out.println("Please Enter Correct username or password");
							break;
						}

					}	
					if(eflag==true)
					{

						usermenu();
						select=Integer.parseInt(br.readLine()); 
						switch(select)
						{
						case 1:
							Fooddata.displayfood();
							break;
						case 2:
							Fooddata.Bill();
							Fooddata.Generatepdf();
							//Fooddata.excel();
							break;
						case 0:System.out.println("Back to main menu");
						break;
						default:
							break;
						}

					}
				}

				while (select !=0);
				break;

			default:
				break;
			}
		}
		while (select !=3);
		System.out.println("Program Closed Thank You");



	}

	static void Mainmenu()
	{

		System.out.println("                              -----------> Welcom to Telugu Ruchulu <-----------");
		System.out.println("1. Admin");
		System.out.println("2. Employee");
		System.out.println("3. Exit");
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println("Select any option");
	}
	static void Adminmenu() {
		System.out.println("                                        *********** Admin Portal ************");
		System.out.println("1. Add Food Item");
		System.out.println("2. Update Food Item");
		System.out.println("3. Delete Fodd Item");
		System.out.println("4. Generate Report");
		System.out.println("5. Add Employee");
		System.out.println("6. Display Employee data");
		System.out.println("0. BACK TO menu main");
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println("Select any option");
	}
	static void usermenu() {
		System.out.println("                                       ************** Employee Portal ********");
		System.out.println("1. Print Menu");
		System.out.println("2. Take Order");
		System.out.println("0. BACK TO MAIN MENU");
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println("Select any option");
	}
	


}
