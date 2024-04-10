package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.input;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;

import java.util.List;
import java.util.Optional;


public interface InputAddressService {
    Address produceAndConsumeAddressAdd(AddressDto addressDto) throws
            AddressFieldsInvalidException,
            AddressAlreadyExistsException;
    Address saveInDbConsumedAddress(Address address);
    List<Address> findAddressByInfo(AddressDto addressDto);
    List<Address> getAllAddresses();
    Address getAddress(String addressID) throws AddressNotFoundException;
    List<Address> getAddressesOfGivenCity(String city) throws AddressCityNotFoundException;
    Address produceAndConsumeAddressDelete(String addressId) throws AddressNotFoundException,
            AddressAlreadyAssignedCompanyException, AddressAlreadyAssignedEmployeeException;
    String deleteAddress (String addressId) throws AddressNotFoundException;
    Address produceAndConsumeAddressEdit(AddressDto payload, String addressId) throws
            AddressNotFoundException;
    Address editAddress(Address address);

    List<Employee> getRemoteEmployeesLivingAtAddress(String addressId) throws AddressNotFoundException;
}
