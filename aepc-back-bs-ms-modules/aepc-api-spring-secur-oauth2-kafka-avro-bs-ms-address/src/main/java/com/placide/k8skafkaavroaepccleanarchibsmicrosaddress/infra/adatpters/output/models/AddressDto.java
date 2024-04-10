package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;
    @Override
    public String toString() {
        return "Address [" +
                "num=" + num +
                ", street='" + street + '\'' +
                ", poBox=" + pb +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ']';
    }
}
