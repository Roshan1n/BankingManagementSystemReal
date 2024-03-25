package ServiceImp;

/*-----Importing Session class from Hibernate----*/
import org.hibernate.Session; 
/*----Importing Transaction class from Hibernate---*/
import org.hibernate.Transaction; 
import org.hibernate.query.Query;

import Configuration.HibernateUtil;
import Entities.Account;
import Entities.Users;
import Exception.AccountNumberException;
import Exception.CustomerBalanceException;
import Exception.CustomerException;
import Services.CustomerService; 

/*------Implementation class for CustomerService interface-----*/
public class CustomerServiceImp implements CustomerService {

  

    /*-----Method to log in a customer with provided email and password-----*/
    @Override
    public Users LoginCustomer(String Email, String Password) throws CustomerException {
    	// Opening a session using HibernateUtil
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { 
            Transaction transaction = session.beginTransaction(); // Starting a new transaction
            
         // Creating a query to fetch user by email and password
            Query<Users> query = session.createQuery("FROM Users u WHERE u.email = :email AND u.password = :password", Users.class); 
            
            query.setParameter("email", Email); 
            query.setParameter("password", Password); 

            Users user = query.uniqueResult(); 

            transaction.commit(); // Committing the transaction

            /*----If user is not found, throw CustomerException with error message-----*/
            if (user == null) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println();
                throw new CustomerException("  Invalid customer Email and Password! Please try again.");
            }
            
            // Print success message if login is successful
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            System.out.println("  Customer Login Successfully..!!");
            System.out.println();
            
            return user; // Return the logged in user
        } catch (Exception e) { // Catching any exception that occurs
            System.out.println();
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new CustomerException("  Error during customer login"); // Throw CustomerException with error message
        }
    }

    /*-----Method to view balance for a given account number-----*/
    @Override
    public int viewBalance(Long accountNumber ) throws CustomerBalanceException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { 
            Account account = session.get(Account.class, accountNumber); 
            if (account != null) { // If account exists
                return (int) account.getBalance(); // Return the balance of the account
            } else { // If account doesn't exist
                System.out.println(); 
                throw new CustomerBalanceException("  Account Number not found: " + accountNumber); 
            }
        } catch (Exception e) { // Catching any exception that occurs
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new CustomerBalanceException("  Error viewing balance"); // Throw CustomerBalanceException with error message
        }
    }

    /*-----Method to deposit money into a given account----*/
    @Override
    public int depositMoney(Long accountNumber, int amount) throws CustomerBalanceException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { 
            Transaction transaction = session.beginTransaction(); // Starting a new transaction

            Account account = session.get(Account.class, accountNumber); // Retrieving account by account number
            if (account != null) { // If account exists
                int currentBalance = (int) account.getBalance(); // Get the current balance of the account
                account.setBalance(currentBalance + amount); // Update the balance by adding the deposited amount

                session.update(account); // Update the account
                transaction.commit(); // Commit the transaction
            } else { // If account doesn't exist
                System.out.println("-----------------------------------------------------------------");
                System.out.println();
                throw new CustomerBalanceException("  Account Number not found: " + accountNumber); // Throw CustomerBalanceException with error message
            }
        } catch (Exception e) { // Catching any exception that occurs
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new CustomerBalanceException("  Error depositing money"); // Throw CustomerBalanceException with error message
        }
        return amount; // Return the deposited amount
    }

    /*----Method to withdraw money from a given account----*/
    @Override
    public void withdrawMoney(Long accountNumber, int amount) throws CustomerBalanceException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // Opening a session using HibernateUtil
            Transaction transaction = session.beginTransaction(); // Starting a new transaction

            Account account = session.get(Account.class, accountNumber); // Retrieving account by account number
            if (account != null) { // If account exists
                int currentBalance = (int) account.getBalance(); // Get the current balance of the account
                if (currentBalance >= amount) { // If there are sufficient funds for withdrawal
                    account.setBalance(currentBalance - amount); // Update the balance by subtracting the withdrawn amount
                    session.update(account); // Update the account
                    transaction.commit(); // Commit the transaction
                } else { // If there are insufficient funds for withdrawal
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println();
                    throw new CustomerBalanceException("  Insufficient balance"); // Throw CustomerBalanceException with error message
                }
            } else { // If account doesn't exist
                System.out.println("-----------------------------------------------------------------");
                System.out.println();
                throw new CustomerBalanceException("  Account Number not found: " + accountNumber); // Throw CustomerBalanceException with error message
            }
        } catch (Exception e) { // Catching any exception that occurs
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new CustomerBalanceException("  Error withdrawing money : "+e); // Throw CustomerBalanceException with error message
        }
    }

    // Method to transfer money between two accounts
    @Override
    public void transferMoney(Long accountNumber, int amount, Long accountNumber2) throws AccountNumberException, CustomerBalanceException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { // Opening a session using HibernateUtil
            Transaction transaction = session.beginTransaction(); // Starting a new transaction

            Account account1 = session.get(Account.class, accountNumber); // Retrieving first account by account number
            Account account2 = session.get(Account.class, accountNumber2); // Retrieving second account by account number

            if (account1 != null && account2 != null) { // If both accounts exist
                if (account1.getBalance() >= amount) { // If there are sufficient funds for transfer
                	
                // Update the balance of first account by subtracting the transferred amount
                    account1.setBalance(account1.getBalance() - amount); 
                 // Update the balance of second account by adding the transferred amount
                    account2.setBalance(account2.getBalance() + amount); 

                    session.update(account1); 
                    session.update(account2); 
                    transaction.commit(); // Commit the transaction
                    
                } else { // If there are insufficient funds for transfer
                    System.out.println("-----------------------------------------------------------------");
                    System.out.println();
                    throw new CustomerBalanceException("  Insufficient balance"); 
                }
            } else { // If one or both accounts don't exist
                System.out.println("-----------------------------------------------------------------");
                System.out.println();
                throw new AccountNumberException("  One or both of the account numbers are invalid"); 
            }
        } catch (Exception e) { // Catching any exception that occurs
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new CustomerBalanceException("  Error transferring money :"+e); 
        }
    }

	

}
