package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CompanyDto {
    private String name;
    private String agency;
    private String type;
    private String addressId;
}
