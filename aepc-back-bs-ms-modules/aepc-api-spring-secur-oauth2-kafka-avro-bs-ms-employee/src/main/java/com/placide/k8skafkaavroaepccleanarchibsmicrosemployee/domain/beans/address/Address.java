package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.address;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Address {
    private String addressId;
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;
    @Override
    public String toString() {
        return "address-api [" +
                "id:'" + addressId + '\'' +
                ", num:" + num +
                ", street:'" + street + '\'' +
                ", po-box:" + pb +
                ", city:'" + city + '\'' +
                ", country:'" + country + '\'' +
                ']';
    }
}
