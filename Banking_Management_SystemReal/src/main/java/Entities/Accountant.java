package Entities;

import javax.persistence.*;

@Entity
@Table(name = "accountant")
public class Accountant {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "accountant_id")
 private Long accountantId; // Unique identifier for the accountant

 @Column(name = "FirstName", nullable = false)
 private String firstName; // First name of the accountant

 @Column(name = "LastName", nullable = false)
 private String lastName; // Last name of the accountant

 @Column(name = "Email", nullable = false, unique = true)
 private String email; // Email address of the accountant

 @Column(name = "Password", nullable = false)
 private String password; // Password of the accountant

 public Accountant() {
     // Default constructor required by Hibernate
 }

 // Parameterized constructor to initialize an accountant with provided values
 public Accountant(String firstName, String lastName, String email, String password) {
     this.firstName = firstName;
     this.lastName = lastName;
     this.email = email;
     this.password = password;
 }

 // Getter and setter methods for accountantId
 public Long getAccountantId() {
     return accountantId;
 }

 public void setAccountantId(Long accountantId) {
     this.accountantId = accountantId;
 }

 // Getter and setter methods for firstName
 public String getFirstName() {
     return firstName;
 }

 public void setFirstName(String firstName) {
     this.firstName = firstName;
 }

 // Getter and setter methods for lastName
 public String getLastName() {
     return lastName;
 }

 public void setLastName(String lastName) {
     this.lastName = lastName;
 }

 // Getter and setter methods for email
 public String getEmail() {
     return email;
 }

 public void setEmail(String email) {
     this.email = email;
 }

 // Getter and setter methods for password
 public String getPassword() {
     return password;
 }

 public void setPassword(String Password) {
     this.password = Password;
 }

 // toString method to represent Accountant object as a string
 @Override
 public String toString() {
     return "Accountant [accountantId=" + accountantId + ", firstName=" + firstName + ", lastName=" + lastName
             + ", email=" + email + ", password=" + password + "]";
 }
}
