package Entities;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "userinfo")
public class Users {

    @Id
    @Column(name = "UID")
    private int UID; // Unique identifier for the user
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts; // List of accounts associated with the user

    @Column(name = "FirstName", nullable = false)
    private String firstName; // First name of the user

    @Column(name = "LastName", nullable = false)
    private String lastName; // Last name of the user

    @Column(name = "Email", unique = true, nullable = false)
    private String email; // Email address of the user

    @Column(name = "Password", nullable = false)
    private String password; // Password of the user

    @Column(name = "Mobile", unique = true, nullable = false)
    private String mobile; // Mobile number of the user

    @Column(name = "Address", nullable = false)
    private String address; // Address of the user
    
    // Constructors, getters, setters, and other methods

    // Parameterized constructor to initialize user object with provided values
    public Users(int UID, String firstName, String lastName, String email, String password, String mobile, String address) {
        super();
        this.UID = UID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.address = address;
    }

    // Default constructor
    public Users() {
        super();
    }

    // Getter and setter methods for the UID field
    public int getUID() {
        return UID;
    }

    public void setUID(int uID) {
        UID = uID;
    }

    // Getter and setter methods for the firstName field
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and setter methods for the lastName field
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and setter methods for the email field
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter methods for the password field
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter methods for the mobile field
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    // Getter and setter methods for the address field
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString method to represent Users object as a string
    @Override
    public String toString() {
        return "Users [UID=" + UID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", password=" + password + ", mobile=" + mobile + ", address=" + address + "]";
    }
}

