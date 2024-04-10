package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CompanyModel {
    private String companyId;
    private String name;
    private String agency;
    private String type;
    private String connectedDate;
}
