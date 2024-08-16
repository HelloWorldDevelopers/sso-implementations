package com.javatechie.spring.cloud.security.api;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_master")
public class EmployeeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    @Column(name = "f_name", nullable = false)
    private String firstName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    // Getters and Setters

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
