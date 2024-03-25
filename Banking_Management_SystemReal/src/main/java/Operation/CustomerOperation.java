package Operation;
import java.util.Scanner;

import Entities.Users;
import Exception.AccountNumberException;
import Exception.CustomerBalanceException;
import Exception.CustomerException;
import ServiceImp.CustomerServiceImp;
import Services.CustomerService;

/*---Declaring a class named CustomerOperation----*/
public class CustomerOperation {
	
	/*---Initializing a Scanner object for user input----*/
	    Scanner sc = new Scanner(System.in); 

	    public void customerOperation() { 
	        
	    	/*---Initializing a boolean variable f with value true, used as a flag for looping--*/
	        boolean f = true; 
	        
	        while (f) { 
            System.out.println("----------------------------------------------------------------");
            System.out.println("-------------------CUSTOMER LOGIN CREDENTIALS-------------------");
            System.out.println("----------------------------------------------------------------");
            System.out.print("  Enter the Email Here : ");
            String email1 = sc.next();
            System.out.print("  Enter the Password Here : ");
            String password1 = sc.next();
            System.out.println();

            System.out.println("-----------------------------------------------------------------");
            CustomerService cs = new CustomerServiceImp();

            try {
                Users user = cs.LoginCustomer(email1, password1);
                System.out.println("-----------------------------------------------------------------");
                System.out.println();
                System.out.println("  WELCOME : " + user.getFirstName() + " " + user.getLastName() + " IN BANKING SYSTEM");

                boolean x = true;

                while (x) {
                	
                	/*-----------This is menu Drive for Customer---------*/
                    System.out.println("-----------------------------------------------------------------\r\n"
                            + "  1.  View Total Balance\r\n" 
                            + "  2.  Deposit Money\r\n"
                            + "  3.  Withdraw Money\r\n" 
                            + "  4.  Transfer Money\r\n"
                            + "  5.  Logout \r\n");
                    System.out.println("-----------------------------------------------------------------");
                    System.out.print("  Choose Your Option : ");
                    int n = sc.nextInt();
                    System.out.println();

                    try {
                        
                    	/*--------------Customer View Own Balanace Here-----------*/
                    	if (n == 1) {
                            System.out.println("------------------------VIEW TOTAL BALANCE----------------------");
                            System.out.println("----------------------------------------------------------------");
                            System.out.print("  Enter Your Account Number : ");
                            long accNum = sc.nextLong();
                            System.out.println("----------------------------------------------------------------");
                            System.out.print("  Current Account Balance : " + cs.viewBalance(accNum));
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                        }
                        
                    	/*--------------Customer Deposite Money Here-----------*/
                        if (n == 2) {
                            System.out.println("--------------------------DEPOSIT MONEY-------------------------");
                            System.out.println("-----------------------------------------------------------------");
                            System.out.print("  Enter Your Account Number : ");
                            long accNum = sc.nextLong();
                            System.out.print("  Enter the Amount to Deposit : ");
                            int amount = sc.nextInt();
                            System.out.println();
                            System.out.println("----------------------------------------------------------------");
                            
                            /*-----call the methods of CustomerServiceImp class-----*/
                            cs.depositMoney(accNum, amount);
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("  Your Amount Deposited Successfully..!!");
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("  Current Account Balance : " + cs.viewBalance(accNum));
                            System.out.println();
                            System.out.println("-----------------------------------------------------------------");
                        }
                        
                        /*---------------Customer Withdraw Money Here-------------*/
                        if (n == 3) {
                            System.out.println("--------------------------WITHDRAW MONEY-------------------------");
                            System.out.println("-----------------------------------------------------------------");
                            System.out.print("  Enter Your Account Number : ");
                            long accNum = sc.nextLong();
                            System.out.print("  Enter the Amount to Withdraw : ");
                            int amount = sc.nextInt();
                            System.out.println();
                            System.out.println("-----------------------------------------------------------------");
                            /*-----call the methods of CustomerServiceImp class-----*/
                            cs.withdrawMoney(accNum, amount);
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("  Your Amount Withdrawn Successfully..!!");
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("  Current Account Balance : " + cs.viewBalance(accNum));
                            System.out.println();
                            System.out.println("-----------------------------------------------------------------");
                        }
                        
                        /*---------------Customer Transfer Money Here-------------*/
                        if (n == 4) {
                            System.out.println("--------------------------TRANSFER MONEY-------------------------");
                            System.out.println("-----------------------------------------------------------------");
                            System.out.print("  Enter Your Account Number : ");
                            long accNum = sc.nextLong();
                            System.out.print("  Enter the Amount to Transfer : ");
                            int amount = sc.nextInt();
                            System.out.print("  Enter the Reciever Account Number : ");
                            long accNum1 = sc.nextLong();
                            System.out.println();
                            System.out.println("-----------------------------------------------------------------");
                            /*-----call the methods of CustomerServiceImp class-----*/
                            if(accNum!=accNum1) {
                            	cs.transferMoney(accNum, amount, accNum1);
                                System.out.println("-----------------------------------------------------------------");
                                System.out.println("  Money Transferred Successfully..!!");
                                System.out.println("-----------------------------------------------------------------");
                                System.out.print("  Current Account Balance : " + cs.viewBalance(accNum));
                                System.out.println();
                                System.out.println("-----------------------------------------------------------------");
                            }else {
                            	System.out.println("Money is Not Transfered in the same Account Number..");
                            	System.out.println();
                            }
                            
                        }
                        
                        /*---------------Customer  Logout  Here-------------*/
                        if (n == 5) {
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("  Customer Logout Successfully..!!");
                            System.out.println();
                            System.out.println("------------------------------------------------------------------");
                            System.out.println("  Thank You For Using Our Banking Services..!!!!!");
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            x = false;
                            f = false;
                        }
                    } catch (CustomerBalanceException cbe) {
                    	// Handle exceptions related to insufficient balance
                        System.out.println("Insufficient balance "+cbe.getMessage());
                        System.out.println();
                    } catch (AccountNumberException ane) {
                        // Handle exceptions related to invalid account number
                        System.out.println(ane.getMessage());
                        System.out.println();
                    }
                }
            
            } catch (CustomerException ce) {
                // Handle exceptions related to customer authentication
                System.out.println("customer authentication "+ce.getMessage());
                System.out.println();
            
            } catch (Exception e) {
                // Handle any other unexpected exceptions
                System.out.println("  Unexpected error: " + e.getMessage());
            }
            
            /*------exit the loop---*/
            if (!f) {
                break;
            }
        }
    }
}
