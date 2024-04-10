package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.usecase;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;

import java.util.UUID;

public class Validator {
    private Validator(){}
    public static boolean isInvalidAddress(AddressDto addressDto){
        return addressDto.getNum()<1
                || addressDto.getStreet().isBlank()
                || addressDto.getPb()<10000
                || addressDto.getCity().isBlank()
                || addressDto.getCountry().isBlank();
    }

    public static void addIdToAddress(Address address){
        address.setAddressId(UUID.randomUUID().toString());
    }
}
