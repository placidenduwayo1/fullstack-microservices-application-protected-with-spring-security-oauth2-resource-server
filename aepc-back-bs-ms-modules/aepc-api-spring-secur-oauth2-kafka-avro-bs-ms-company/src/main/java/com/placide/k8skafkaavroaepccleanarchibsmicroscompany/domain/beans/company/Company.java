package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address;

public class Company {
    private String companyId;
    private String name;
    private String agency;
    private String type;
    private String connectedDate;
    private String addressId;
    private Address address;


    private Company(CompanyBuilder builder) {
        this.companyId = builder.companyId;
        this.name = builder.name;
        this.agency = builder.agency;
        this.type = builder.type;
        this.connectedDate = builder.connectedDate;
        this.addressId = builder.addressId;
        this.address = builder.address;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConnectedDate() {
        return connectedDate;
    }

    public void setConnectedDate(String connectedDate) {
        this.connectedDate = connectedDate;
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
        return "Company[" +
                "id:'" + companyId + '\'' +
                ", name:'" + name + '\'' +
                ", agency:'" + agency + '\'' +
                ", type:'" + type + '\'' +
                ", connected-date:" + connectedDate +
                ", address:" + address +
                ']';
    }

    public static class CompanyBuilder {
        private String companyId;
        private String name;
        private String agency;
        private String type;
        private String connectedDate;
        private String addressId;
        private Address address;

        public CompanyBuilder companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        public CompanyBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyBuilder agency(String agency) {
            this.agency = agency;
            return this;
        }

        public CompanyBuilder type(String type) {
            this.type = type;
            return this;
        }

        public CompanyBuilder connectedDate(String connectedDate) {
            this.connectedDate = connectedDate;
            return this;
        }

        public CompanyBuilder addressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public CompanyBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Company build(){
            return new Company(this);
        }
    }
}
