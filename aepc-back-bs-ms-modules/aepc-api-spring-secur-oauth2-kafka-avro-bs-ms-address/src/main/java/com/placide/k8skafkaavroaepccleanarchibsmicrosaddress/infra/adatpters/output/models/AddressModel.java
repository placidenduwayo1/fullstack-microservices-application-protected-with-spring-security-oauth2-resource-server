package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
@Entity @Table(name = "addresses")
public class AddressModel implements Serializable {
    @Id
    @GenericGenerator(name = "uuid")
    private String addressId;
    private int num;
    private String street;
    private int pb;
    private String city;
    private String country;
}
