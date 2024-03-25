package Services;


import java.util.List;

import Entities.Accountant;
import Exception.AccountantException;
import Exception.AddAccountException;
import Exception.CustomerException;
import Exception.DeleteCustomerException;
import Exception.UpdateCustomerException;
//Interface defining operations for an Accountant service
public interface AccountantService {
 
 // Registers a new accountant
 public void registerAccountant(Accountant accountant) throws AccountantException;
 
 // Logs in an accountant using email and password
 public Accountant LoginAccountant(String Email, String Password) throws AccountantException;
 
 // Adds a new customer
 public void addCustomer(int UID, String Address, String Email, String FirstName, String LastName, String Mobile, String Password) throws CustomerException;
 
 // Adds an account for a customer
 public void addAccount(float Balance, int UID) throws AddAccountException;
 
 // Updates the address of a customer
 public void updateCustomer(int uid, String Address) throws UpdateCustomerException;
 
 // Deletes a customer
 public void deleteCustomer(int uid) throws DeleteCustomerException;
 
 // Retrieves customer information by account number
 public Object[] viewCustomerInfo(Long accountNumber) throws CustomerException;
 
 // Retrieves details of all customers
 public List<Object[]> viewAllCustomerDetails() throws CustomerException;
}
