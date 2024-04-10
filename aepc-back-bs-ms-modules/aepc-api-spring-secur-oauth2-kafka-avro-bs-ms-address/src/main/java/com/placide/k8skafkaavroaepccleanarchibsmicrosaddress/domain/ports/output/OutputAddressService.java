package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.avrobean.AddressAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressCityNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;

import java.util.List;

public interface OutputAddressService {
    Address consumeAddressAdd(AddressAvro addressAvro, String topic);
    Address saveInDbConsumedAddress(Address address);
    List<Address> findAddressByInfo(AddressDto addressDto);
    List<Address> getAllAddresses();
   Address getAddress(String addressID) throws AddressNotFoundException;
    List<Address> getAddressesOfGivenCity(String city) throws AddressCityNotFoundException;
   Address consumeAddressDelete(AddressAvro addressAvro, String topic);
   String deleteAddress(String addressId) throws AddressNotFoundException;
    Address consumeAddressEdit(AddressAvro addressAvro, String topic);
    Address updateAddress(Address address);
}
