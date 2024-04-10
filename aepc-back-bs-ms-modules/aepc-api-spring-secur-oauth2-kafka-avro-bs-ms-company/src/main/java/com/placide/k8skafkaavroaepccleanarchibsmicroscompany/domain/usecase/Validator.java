package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.usecase;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Type;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.ExceptionMsg;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyDto;

public class Validator {
    private Validator() {
    }

    public static boolean areValidCompanyFields(String name, String agency, String type, String addressId) {
        return !name.isBlank()
                && !agency.isBlank()
                && !type.isBlank()
                && !addressId.isBlank();
    }

    public static boolean checkTypeExists(String type) {
        boolean exists = false;
        for (Type it : Type.values()) {
            if (type.equals(it.getcType())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public static void format(CompanyDto dto) {
        dto.setName(dto.getName().strip().toUpperCase());
        dto.setAgency(dto.getAgency().strip().toUpperCase());
        dto.setType(dto.getType().strip());
        dto.setAddressId(dto.getAddressId().strip());
    }

    public static boolean remoteAddressApiUnreachable(String addressId) {
        return addressId.strip().equals(ExceptionMsg.REMOTE_ADDRESS_API_EXCEPTION);
    }
}
