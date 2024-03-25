package ServiceImp;
import Configuration.HibernateUtil;
import Entities.Account;
import Entities.Accountant;
import Entities.Users;
import Exception.*;
import Services.AccountantService;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.*;

public class AccountantServiceImpl implements AccountantService {

    // Method to register a new accountant
    @Override
    public void registerAccountant(Accountant accountant) throws AccountantException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
            transaction = session.beginTransaction();
            // Save the accountant object
            session.save(accountant);
            // Commit transaction
            transaction.commit();
        } catch (Exception e) {
            // Rollback transaction if there's an exception
            if (transaction != null) {
                transaction.rollback();
            }
            // Throw AccountantException with appropriate message
            throw new AccountantException("Error registering accountant: " + e.getMessage());
        }
    }

    // Method to log in an accountant
    @Override
    public Accountant LoginAccountant(String Email, String Password) throws AccountantException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Accountant> query = session.createQuery("FROM Accountant WHERE email = :email AND password = :password", Accountant.class);
            query.setParameter("email", Email);
            query.setParameter("password", Password);
            Accountant accountant = query.uniqueResult();
            transaction.commit();
            return accountant;
        } catch (HibernateException e) {
            // Handle HibernateException and throw AccountantException with appropriate message
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new AccountantException("  Error while logging in Accountant.");
        }
    }

    // Method to add a new customer
    @Override
    public void addCustomer(int UID, String Address, String Email, String FirstName, String LastName, String Mobile, String Password) throws CustomerException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // Create a new Users object with provided details
            Users user = new Users(UID, FirstName, LastName, Email, Password, Mobile, Address);
            // Save the user object
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            // Handle HibernateException and throw CustomerException with appropriate message
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new CustomerException("  Error while adding a new customer.");
        }
    }

    // Method to add an account for a customer
    @Override
    public void addAccount(float balance, int UID) throws AddAccountException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // Retrieve the user by UID
            Users user = session.get(Users.class, UID);
            if (user != null) {
                // Create a new Account object with provided balance and user
                Account account = new Account(balance, user);
                // Save the account object
                session.save(account);
            }
            transaction.commit();
        } catch (HibernateException e) {
            // Handle HibernateException and throw AddAccountException with appropriate message
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new AddAccountException("Error while adding a new account.");
        }
    }

    // Method to update customer address
    @Override
    public void updateCustomer(int UID, String address) throws UpdateCustomerException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            // Create and execute an update query to update customer address
            Query<?> query = session.createQuery("UPDATE Users SET address = :address WHERE UID = :UID");
            query.setParameter("address", address);
            query.setParameter("UID", UID);
            int result = query.executeUpdate();
            if (result <= 0) {
                // Throw UpdateCustomerException if no records were updated
                throw new UpdateCustomerException("Unable to update customer address.");
            }
            transaction.commit();
        } catch (HibernateException e) {
            // Handle HibernateException and throw UpdateCustomerException with appropriate message
            System.out.println("-----------------------------------------------------------------");
            System.out.println();
            throw new UpdateCustomerException("Error while updating customer address.");
        }
    }

    // Method to delete customer account
    @Override
    public void deleteCustomer(int UID) throws DeleteCustomerException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Delete associated records from the 'account' table
            Query<?> deleteAccountQuery = session.createQuery("DELETE FROM Account WHERE UID = :UID");
            deleteAccountQuery.setParameter("UID", UID);
            int deletedAccounts = deleteAccountQuery.executeUpdate();

            // If there were associated records in the 'account' table, proceed to delete the record from 'userinfo'
            if (deletedAccounts > 0) {
                Query<?> deleteUserInfoQuery = session.createQuery("DELETE FROM Users WHERE UID = :UID");
                deleteUserInfoQuery.setParameter("UID", UID);
                int deletedUserInfo = deleteUserInfoQuery.executeUpdate();

                if (deletedUserInfo <= 0) {
                    // Throw DeleteCustomerException if no records were deleted from 'userinfo'
                    throw new DeleteCustomerException("Unable to delete customer account.");
                }
            } else {
                // Throw DeleteCustomerException if no associated records were found in the 'account' table
                System.out.println("-----------------------------------------------------------------");
                System.out.println();
                throw new DeleteCustomerException("No associated records found in the account table.");
            }

            transaction.commit();
        } catch (HibernateException e) {
            // Handle HibernateException and throw DeleteCustomerException with appropriate message
            throw new DeleteCustomerException("Error while deleting customer account.");
        }
    }

    // Method to view customer information by account number
    @Override
    public Object[] viewCustomerInfo(Long accountNumber) throws CustomerException {
        Object[] result = null;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            // Query to fetch customer details for a given account number
            String hql = "SELECT u, a.accountNumber, a.balance FROM Users u JOIN u.accounts a WHERE a.accountNumber = :accountNumber";
            result = (Object[]) session.createQuery(hql)
                    .setParameter("accountNumber", accountNumber)
                    .uniqueResult();
        } catch (Exception e) {
            // Handle any exception and throw CustomerException with appropriate message
            e.printStackTrace();
            throw new CustomerException("Failed to fetch customer details for account number: " + accountNumber);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    // Method to view all customer details
    @Override
    public List<Object[]> viewAllCustomerDetails() throws CustomerException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Query to fetch all customer details
            String jpql = "SELECT u, a.accountNumber, a.balance FROM Users u JOIN u.accounts a";
            Query<Object[]> query = session.createQuery(jpql);
            return query.list();
        }
        catch (Exception e) {
            // Handle any exception and throw CustomerException with appropriate message
            throw new CustomerException("Error while fetching all customer details.");
        }
    }
}
