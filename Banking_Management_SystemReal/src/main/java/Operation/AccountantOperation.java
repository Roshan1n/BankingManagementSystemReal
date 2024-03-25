package Operation;

import java.util.List;
import java.util.Scanner;

import Entities.Accountant;
import Entities.Users;
import Exception.AccountantException;
import Exception.AddAccountException;
import Exception.CustomerException;
import Exception.DeleteCustomerException;
import Exception.UpdateCustomerException;
import ServiceImp.AccountantServiceImpl;
import Services.AccountantService;

/*-------Declaring a class named AccountantOperation----*/
public class AccountantOperation {

	/*---Initializing a Scanner object for user input----*/
	Scanner sc = new Scanner(System.in);

	public void accountantOperation() {

		boolean f = true; // Flag for controlling the main loop

		while (f) {

			System.out.println("-----------------ACCOUNTANT LOGIN CREDENTIALS--------------------");
			System.out.println("-----------------------------------------------------------------");
			System.out.print("  Enter Your Email Here : ");
			String email = sc.next();
			System.out.print("  Enter Your Password Here : ");
			String password = sc.next();

			/*---------Service to perform accountant operations--------*/
			AccountantService as = new AccountantServiceImpl();

			try {
				// Attempt to login with provided credentials
				Accountant ac = as.LoginAccountant(email, password);

				if (ac == null) {
					// If login fails, inform the user and exit the loop
					System.out.println("-----------------------------------------------------------------");
					System.out.println("Wrong Credentials..");
					break;
				}
				System.out.println("-----------------------------------------------------------------");
				System.out.println("  Login Successfully Done..!!!  ");
				System.out.println("-----------------------------------------------------------------");

				boolean y = true; // Flag for controlling the inner loop

				while (y) {
					System.out.println("-----------------------------------------------------------------");
					System.out.println("  WELCOME " + ac.getFirstName() + " " + ac.getLastName()
							+ " AS ACCOUNTANT OF ONLINE BANKING SYSTEM");
					System.out.println();
					System.out.println("-----------------------------------------------------------------\r\n"
							+ "  1.  Add New Customer Account\r\n" + "  2.  Update Customer Address\r\n"
							+ "  3.  Delete Customer Account By Account Number\r\n"
							+ "  4.  View Particular Account Details By Account Number\r\n"
							+ "  5.  View All Account Details By Account Number\r\n" + "  6.  Logout\r\n");
					System.out.println("-----------------------------------------------------------------");
					System.out.print("  Choose Your Option : ");

					int x = sc.nextInt(); // Get the user's choice
					System.out.println();

					/*------------If the user chooses option 1 Add a new customer account---*/
					if (x == 1) {

						try {
							System.out.println("-----------------------------------------------------------------");
							System.out.println("---------------------ADD NEW CUSTOMER ACCOUNT--------------------");
							System.out.println("-----------------------------------------------------------------");

							System.out.print("  Enter the Customer Id(Number) : ");
							int customerId = sc.nextInt();
							System.out.print("  Enter the Customer FirstName  : ");
							String customerFirstName = sc.next();
							System.out.print("  Enter the Customer LastName  : ");
							String customerLastName = sc.next();
							System.out.print("  Enter the Customer Mail  : ");
							String customerMail = sc.next();
							System.out.print("  Enter the Customer Password  : ");
							String customerPass = sc.next();
							System.out.print("  Enter the Customer Mobile No.  : ");
							String customerMobile = sc.next();
							System.out.print("  Enter the Customer Address  : ");
							String customerAddress = sc.next();
							System.out.print("  Enter the Opening Balance  : ");
							float balance = sc.nextFloat();
							System.out.println("-----------------------------------------------------------------");

							/*-----Add the customer to the database-----*/
							as.addCustomer(customerId, customerAddress, customerMail, customerFirstName,
									customerLastName, customerMobile, customerPass);

							try {
								as.addAccount(balance, customerId); // Add account with the provided opening balance and
																	// customer Id
								System.out.println("-----------------------------------------------------------------");
								System.out.println("  Customer Account Added Successfully..!!!"); // Inform user about
																									// successful
																									// account addition
								System.out.println();
							} catch (AddAccountException aae) {
								System.out.println("-----------------------------------------------------------------");
								System.out.println(aae); // Handle exception related to adding account
							}

						} catch (Exception e) {
							System.out.println("-----------------------------------------------------------------");
							System.out.println("  Invalid Input ...Please Try Again !!" + e.getMessage()); // Inform
																											// user
																											// about
																											// invalid
																											// input
							System.out.println();
						}
					} // End of the if statement

					/*------ If the user chooses option 2 Update customer address----*/
					if (x == 2) {
						try {
							System.out.println("-----------------------------------------------------------------");
							System.out.println("--------------------UPDATE CUSTOMER ADDRESS----------------------");
							System.out.println("-----------------------------------------------------------------");

							System.out.print("  Enter the Customer User ID  : ");
							int userID = sc.nextInt();
							System.out.print("  Enter the New Address  : ");
							String customerAddress = sc.next();
							System.out.println();

							try {
								System.out.println("-----------------------------------------------------------------");
								// Update customer address using the provided User ID and new address
								as.updateCustomer(userID, customerAddress);
								System.out.println("-----------------------------------------------------------------");
								System.out.println("  Customer Address Updated Successfully..!!!");
								System.out.println();
							} catch (UpdateCustomerException uce) {
								System.out.println("-----------------------------------------------------------------");
								System.out.println(uce); // Handle exception related to customer address update
							}
						} catch (Exception e) {
							System.out.println("-----------------------------------------------------------------");
							// Inform user about any other exceptions
							System.out.println("  Invalid Input ...Please Try Again !!" + e.getMessage());
							System.out.println();
						}
					} // End of the if statement

					/*------If the user chooses option 3 Delete customer account by account number----*/
					if (x == 3) {
						try {
							System.out.println("-----------------------------------------------------------------");
							System.out.println("---------------------REMOVE CUSTOMER ACCOUNT---------------------");
							System.out.println("-----------------------------------------------------------------");

							System.out.print("  Enter the Customer User Id  : ");
							int userId = sc.nextInt(); // Read the customer's User Id
							System.out.println();

							try {
								System.out.println("-----------------------------------------------------------------");
								// Delete customer account using the provided User Id
								as.deleteCustomer(userId);
								System.out.println("-----------------------------------------------------------------");
								System.out.println("  Customer Account Deleted Successfully..!!!");
								System.out.println();
							} catch (DeleteCustomerException dce) {
								System.out.println("-----------------------------------------------------------------");
								System.out.println(dce); // Handle exception related to customer deletion
							}
						} catch (Exception e) {
							System.out.println("-----------------------------------------------------------------");
							System.out.println(e.getMessage()); // Inform user about any other exceptions
							System.out.println();
						}
					} // End of the if statement

					/*------- If the user chooses option 4 View particular customer account details---*/
					if (x == 4) {
						try {
							System.out.println("-----------------------------------------------------------------");
							System.out.println("---------------VIEW PARTICULAR CUSTOMER ACCOUNT------------------");
							System.out.println("-----------------------------------------------------------------");

							System.out.print("  Enter the Customer Account Number  : ");
							Long accNum = sc.nextLong(); // Read the customer's account number
							System.out.println();
							System.out.println("-----------------------------------------------------------------");

							try {

								/*-------Retrieve customer information based on the account number----*/
								Object[] result = as.viewCustomerInfo(accNum);
								System.out.println();
								System.out.println("-----------------------------------------------------------------");
								if (result != null) {
									// Extract user details
									Users user = (Users) result[0];
									// Extract account number
									Long fetchedAccountNumber = (Long) result[1];
									// Extract account balance
									float balance = (float) result[2];

									// Display customer account details
									System.out.println("  Customer User Id : " + user.getUID());
									System.out.println(
											"  First Name : " + user.getFirstName() + " " + user.getLastName());
									System.out.println("  Account Number : " + fetchedAccountNumber);
									System.out.println("  Balance : " + balance);
									System.out.println("  Mobile No. : " + user.getMobile());
									System.out.println("  Email : " + user.getEmail());
									System.out.println("  Adderss : " + user.getAddress());
									System.out.println();
									System.out.println(
											"----------------------------------------------------------------");
								} else {
									// Inform user if no customer found with given account number
									System.out.println("  No customer found with account number: " + accNum);
									System.out.println();
								}
							} catch (CustomerException e) {
								// Handle customer-related exceptions
								e.printStackTrace();
							}
						} catch (Exception e) {
							System.out.println("----------------------------------------------------------------");
							System.out.println("  Invalid Input ...Please Try Again !! :" + e.getMessage());
							System.out.println();
						}
					} // End of the if statement

					/*----------If the user chooses option View all customer account details-----*/
					if (x == 5) {
						try {
							System.out.println("--------------------VIEW ALL CUSTOMER ACCOUNT--------------------");
							System.out.println(" ");

							try {

								/*---- Retrieve all customer account details----*/
								List<Object[]> customerDetailsList = as.viewAllCustomerDetails();
								System.out.println();
								System.out.println("-----------------------------------------------------------------");

								/*----Iterate through the list of customer details----*/
								for (Object[] customerDetails : customerDetailsList) {
									Users user = (Users) customerDetails[0];
									Long accountNumber = (Long) customerDetails[1];
									float balance = (float) customerDetails[2];

									// Print details of each customer account
									System.out.println(
											"----------------------------------------------------------------");
									System.out.println("  Account Number : " + accountNumber);
									System.out.println(
											"  Customer Name : " + user.getFirstName() + " " + user.getLastName());
									System.out.println("  Customer Email : " + user.getEmail());
									System.out.println("  Customer Password : " + user.getPassword());
									System.out.println("  Customer Mobile : " + user.getMobile());
									System.out.println("  Customer Address : " + user.getAddress());
									System.out.println("  Total Balance : " + balance);
									System.out.println(
											"----------------------------------------------------------------");
								}
								System.out.println("----------------------------------------------------------------");
							} catch (CustomerException ce) {
								// Handle customer-related exceptions
								System.out.println(ce);
							}
						} catch (Exception e) {
							// Handle any other unexpected exceptions
							System.out.println("----------------------------------------------------------------");
							System.out.println("  Invalid Input ...Please Try Again !!" + e.getMessage());
							System.out.println();
						}
					}

					/*------Logout Accountant Part------*/
					if (x == 6) {

						// Logout
						y = false; // Exit the inner loop
						f = false; // Exit the main loop

						System.out.println();
						System.out.println("  Accountant Logout Successfully..!!");
						System.out.println("----------------------------------------------------------------");
						System.out.println();
					}
				}
			} catch (AccountantException ae) {
				// Catch any accountant related exceptions
				System.out.println("----------------------------------------------------------------");
				System.out.println("  Accountant Exception : " + ae.getMessage());
				System.out.println();

				sc.next();

			} catch (Exception e) {
				System.out.println("----------------------------------------------------------------");
				// Catch any other unexpected exceptions
				System.out.println("  Other Error Occured : " + e.getMessage());
				System.out.println();
				sc.next();

			}

			/*---Exit the Loop---*/
			if (!f) {
				break;
			}
		}
	}
}
