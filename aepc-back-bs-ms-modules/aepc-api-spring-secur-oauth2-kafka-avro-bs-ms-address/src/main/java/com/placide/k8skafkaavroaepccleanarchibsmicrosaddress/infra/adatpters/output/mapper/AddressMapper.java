package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.avrobean.AddressAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressModel;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;
import org.springframework.beans.BeanUtils;

public class AddressMapper {
    private AddressMapper() {
    }

    public static Address mapModelToBean(AddressModel addressModel) {
        Address address = new Address.AddressBuilder().build();
        BeanUtils.copyProperties(addressModel, address);
        return address;
    }

    public static AddressModel mapBeanToModel(Address address) {
        AddressModel addressModel = AddressModel.builder().build();
        BeanUtils.copyProperties(address, addressModel);
        return addressModel;
    }

    public static Address mapDtoToBean(AddressDto addressDto) {
        Address address = new Address.AddressBuilder().build();
        BeanUtils.copyProperties(addressDto, address);
        return address;
    }

    public static AddressDto mapBeanToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        BeanUtils.copyProperties(address, addressDto);
        return addressDto;
    }

    public static AddressAvro mapBeanToAvro(Address address) {
        return AddressAvro.newBuilder()
                .setAddressId(address.getAddressId())
                .setNum(address.getNum())
                .setStreet(address.getStreet())
                .setPb(address.getPb())
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .build();
    }

    public static Address mapAvroToBean(AddressAvro addressAvro) {
        return new Address.AddressBuilder()
                .addressId(addressAvro.getAddressId())
                .num(addressAvro.getNum())
                .street(addressAvro.getStreet())
                .pb(addressAvro.getPb())
                .city(addressAvro.getCity())
                .country(addressAvro.getCountry())
                .build();
    }
}
