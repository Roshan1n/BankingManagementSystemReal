package Entities;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
 @SequenceGenerator(name = "account_generator", sequenceName = "account_number_sequence", allocationSize = 1000001)
 @Column(name = "AccountNumber")
 private Long accountNumber; // Unique account number

 @Column(name = "Balance")
 private float balance; // Balance amount in the account

 @ManyToOne
 @JoinColumn(name = "UID")
 private Users user; // User associated with the account

 // Parameterized constructor to initialize an account with provided values
 public Account(float balance, Users user) {
     this.balance = balance;
     this.user = user;
 }

 // Default constructor
 public Account() {
     super();
 }

 // Getter and setter methods for balance
 public float getBalance() {
     return balance;
 }

 public void setBalance(float balance) {
     this.balance = balance;
 }

 // Getter and setter methods for user
 public Users getUser() {
     return user;
 }

 public void setUser(Users user) {
     this.user = user;
 }

 // toString method to represent Account object as a string
 @Override
 public String toString() {
     return "Account [accountNumber=" + accountNumber + ", balance=" + balance + ", user=" + user + "]";
 }
}
