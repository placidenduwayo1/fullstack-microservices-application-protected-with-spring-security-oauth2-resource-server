package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.employee;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address.Address;

public class Employee {
    private String employeeId;
    private String firstname;
    private String lastname;
    private String email;
    private String hireDate;
    private String state;
    private String role;
    private String addressId;
    private Address address;

    private Employee(EmployeeBuilder builder) {
        this.employeeId = builder.employeeId;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.hireDate = builder.hireDate;
        this.state = builder.state;
        this.role = builder.role;
        this.addressId = builder.addressId;
        this.address = builder.address;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeID) {
        this.employeeId = employeeID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee [" +
                "id:'" + employeeId + '\'' +
                ", firstname:'" + firstname + '\'' +
                ", lastname:'" + lastname + '\'' +
                ", email:'" + email + '\'' +
                ", hire-date:'" + hireDate + '\'' +
                ", state:'" + state + '\'' +
                ", role:'" + role + '\'' +
                ", address:" + address +
                ']';
    }
    //Java Builder pattern
    public static class EmployeeBuilder {
        private String employeeId;
        private String firstname;
        private String lastname;
        private String email;
        private String hireDate;
        private String state;
        private String role;
        private String addressId;
        private Address address;
        public EmployeeBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public EmployeeBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public EmployeeBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public EmployeeBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmployeeBuilder hireDate(String hireDate) {
            this.hireDate = hireDate;
            return this;
        }

        public EmployeeBuilder state(String state) {
            this.state = state;
            return this;
        }

        public EmployeeBuilder type(String type) {
            this.role = type;
            return this;
        }

        public EmployeeBuilder addressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public EmployeeBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Employee build(){
            return new Employee(this);
        }
    }
}
