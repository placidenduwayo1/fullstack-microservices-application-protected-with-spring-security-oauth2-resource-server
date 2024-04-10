
package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans;

public class Address {
  private String addressId;
  private int num;
  private String street;
  private int pb;
  private String city;
  private String country;

  private Address(AddressBuilder builder) {
    this.addressId = builder.addressId;
    this.num = builder.num;
    this.street = builder.street;
    this.pb = builder.pb;
    this.city = builder.city;
    this.country = builder.country;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public int getPb() {
    return pb;
  }

  public void setPb(int pb) {
    this.pb = pb;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "Address [" +
            "id:'" + addressId + '\'' +
            ", num:" + num +
            ", street:'" + street + '\'' +
            ", pb:" + pb +
            ", city:'" + city + '\'' +
            ", country:'" + country + '\'' +
            ']';
  }

  public static class AddressBuilder {
    private String addressId;
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;

    public AddressBuilder addressId(String addressId) {
      this.addressId = addressId;
      return this;
    }

    public AddressBuilder num(int num) {
      this.num = num;
      return this;
    }

    public AddressBuilder street(String street) {
      this.street = street;
      return this;
    }

    public AddressBuilder pb(int pb) {
      this.pb = pb;
      return this;
    }

    public AddressBuilder city(String city) {
      this.city = city;
      return this;
    }

    public AddressBuilder country(String country) {
      this.country = country;
      return this;
    }

    public Address build(){
      return new Address(this);
    }
  }
}









