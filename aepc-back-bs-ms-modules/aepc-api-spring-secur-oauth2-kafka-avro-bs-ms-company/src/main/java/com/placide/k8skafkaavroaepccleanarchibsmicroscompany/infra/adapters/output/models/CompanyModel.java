package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models.AddressModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "companies")
public class CompanyModel {
    @Id
    @GenericGenerator(name = "uuid")
    private String companyId;
    private String name;
    private String agency;
    private String type;
    private String connectedDate;
    @Column(unique = true)
    private String addressId;
    @Transient
    private AddressModel addressModel;
}
