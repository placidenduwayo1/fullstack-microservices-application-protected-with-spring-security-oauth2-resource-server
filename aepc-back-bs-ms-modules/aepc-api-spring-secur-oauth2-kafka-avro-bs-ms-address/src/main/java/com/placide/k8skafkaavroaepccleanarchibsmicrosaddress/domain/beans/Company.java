package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Company {
    private String companyId;
    private String name;
    private String agency;
    private String type;
    private String connectedDate;
    @Override
    public String toString() {
        return "Company-API:[" +
                "company-id:'" + companyId + '\'' +
                ", company-name:'" + name + '\'' +
                ", agency:'" + agency + '\'' +
                ", type:'" + type + '\'' +
                ", connected-date='" + connectedDate + '\'' +
                ']';
    }
}
