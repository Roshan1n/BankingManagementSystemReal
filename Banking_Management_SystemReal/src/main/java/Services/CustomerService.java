package Services;

import Entities.Users;
import Exception.AccountNumberException;
import Exception.CustomerBalanceException;
import Exception.CustomerException; 

/*----Interface representing the services provided to customers----*/
public interface CustomerService {

	
    // Method to log in a customer with provided email and password
    public Users LoginCustomer(String Email, String Password) throws CustomerException;
    
    // Method to view balance for a given account number
    public int viewBalance(Long AccountNumber) throws CustomerBalanceException;
    
    // Method to deposit money into a given account
    public int depositMoney(Long AccountNumber, int amount) throws CustomerBalanceException;
    
    // Method to withdraw money from a given account
    public void withdrawMoney(Long AccountNumber, int amount) throws CustomerBalanceException;
    
    // Method to transfer money between two accounts
    public void transferMoney(Long AccountNumber, int amount, Long AccountNumber2) throws AccountNumberException, CustomerBalanceException;	
	
}
