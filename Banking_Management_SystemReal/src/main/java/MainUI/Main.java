package MainUI;

import java.util.InputMismatchException;
import java.util.Scanner;

import Entities.Accountant;
import Exception.AccountantException;
import Operation.AccountantOperation;
import Operation.CustomerOperation;
import ServiceImp.AccountantServiceImpl;
import Services.AccountantService;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		/*---Initializing a boolean variable f with value true, used as a flag for looping--*/
		boolean f = true;

		while (f) {

			System.out.println("----------------WELCOME TO ONLINE BANKING SYSTEM-----------------");
			System.out.println();
			System.out.println(" 1.  ACCOUNTANT REGISTRATION ");
			System.out.println(" 2.  ACCOUNTANT LOGIN ");
			System.out.println(" 3.  CUSTOMER LOGIN ");
			System.out.println(" 4. EXIT ");

			try {
				System.out.print("  Choose Your Option : ");
				int choice = sc.nextInt();
				System.out.println();

				switch (choice) {
				case 1:
					// Create an instance of AccountantDao
					AccountantService accountantDao = new AccountantServiceImpl();

					System.out.print("  Enter the Accountant FirstName  : ");
					String customerFirstName = sc.next();
					System.out.print("  Enter the Accountant LastName  : ");
					String customerLastName = sc.next();
					System.out.print("  Enter the Accountant Mail  : ");
					String customerMail = sc.next();
					System.out.print("  Enter the Password : ");
					String password = sc.next();

					// Create an instance of Accountant with sample data
					Accountant accountant = new Accountant(customerFirstName, customerLastName, customerMail, password);

					try {
						// Register the accountant
						accountantDao.registerAccountant(accountant);

						System.out.println("  Accountant registered successfully.");
						System.out.println();
						System.out.println("----------------------------------------------------------");
					} catch (AccountantException e) {
						System.out.println("----------------------------------------------------------");
						System.out.println();
						System.out.println("  Error registering accountant: " + e.getMessage());
					}
					break;

				case 2:
					// Creating an instance of AccountantOperation and invoking the
					// accountantOperation method
					AccountantOperation acc_opera = new AccountantOperation();
					acc_opera.accountantOperation();
					break;

				case 3:
					// Creating an instance of CustomerOperation and invoking the customerOperation
					// method
					CustomerOperation co = new CustomerOperation();
					co.customerOperation();
					break;

				case 4:

					if (choice == 4) {

						// Logout
						//y = false; // Exit the inner loop
						f = false; // Exit the main loop

						System.out.println();
						System.out.println(" Exit successfully..");
						
					}

					break;

				default:
					System.out.println("This Option is Not Exist...Please Choose Correct Option !!");
				}

			} catch (InputMismatchException e) {
				// Handling any unexpected exceptions and printing the error message
				System.out.println("Error Occurred: Invalid input! Please enter a valid integer.");
				// Consume the invalid input to avoid an infinite loop
				sc.next();
			}
			/*---Exit the Loop---*/
			if (!f) {
				break;
			}

		}
		sc.close();
	}

}
