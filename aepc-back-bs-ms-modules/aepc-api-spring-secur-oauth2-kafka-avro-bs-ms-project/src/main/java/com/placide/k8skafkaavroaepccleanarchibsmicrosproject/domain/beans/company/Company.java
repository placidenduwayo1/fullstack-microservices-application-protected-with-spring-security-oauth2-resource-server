package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company;

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
        return "Company-API [" +
                "id:'" + companyId + '\'' +
                ", name:'" + name + '\'' +
                ", agency:'" + agency + '\'' +
                ", type:'" + type + '\'' +
                ", connected-date:" + connectedDate +
                ']';
    }
}
