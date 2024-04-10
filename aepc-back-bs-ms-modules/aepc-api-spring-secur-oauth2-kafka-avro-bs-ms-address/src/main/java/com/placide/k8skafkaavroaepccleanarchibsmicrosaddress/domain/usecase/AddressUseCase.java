package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.usecase;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.avrobean.AddressAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.input.InputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputKafkaProducerAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputRemoteCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output.OutputRemoteEmployeeService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;

import java.util.List;

public class AddressUseCase implements InputAddressService {
    private final OutputAddressService outputAddressService;
    private final OutputKafkaProducerAddressService outputKafkaProducerAddressService;
    private final OutputRemoteCompanyService outputRemoteCompanyService;
    private final OutputRemoteEmployeeService outputRemoteEmployeeService;

    public AddressUseCase(
            OutputKafkaProducerAddressService outputKafkaProducerAddressService,
            OutputAddressService outputAddressService, OutputRemoteCompanyService outputRemoteCompanyService,
            OutputRemoteEmployeeService outputRemoteEmployeeService) {

        this.outputKafkaProducerAddressService = outputKafkaProducerAddressService;
        this.outputAddressService = outputAddressService;
        this.outputRemoteCompanyService = outputRemoteCompanyService;
        this.outputRemoteEmployeeService = outputRemoteEmployeeService;
    }

    private void checkAddressValidity(AddressDto addressDto) throws AddressFieldsInvalidException {
        if (Validator.isInvalidAddress(addressDto)) {
            throw new AddressFieldsInvalidException(ExceptionMgs.ADDRESS_FIELDS_INVALID_EXCEPTION);
        }
    }

    private void checkAddressAlreadyExists(AddressDto addressDto) throws AddressAlreadyExistsException {
        if (!findAddressByInfo(addressDto).isEmpty()) {
            throw new AddressAlreadyExistsException(ExceptionMgs.ADDRESS_ALREADY_EXISTS_EXCEPTION);
        }
    }

    /*send address event to kafka topic and consumer consumes it*/
    @Override
    public Address produceAndConsumeAddressAdd(AddressDto addressDto) throws
            AddressFieldsInvalidException, AddressAlreadyExistsException {
        checkAddressValidity(addressDto);
        checkAddressAlreadyExists(addressDto);
        Address address = AddressMapper.mapDtoToBean(addressDto);
        Validator.addIdToAddress(address);
        AddressAvro addressAvro = AddressMapper.mapBeanToAvro(address);
        return AddressMapper.mapAvroToBean(outputKafkaProducerAddressService.sendKafkaAddressAddEvent(addressAvro));
    }

    @Override
    public Address saveInDbConsumedAddress(Address address) {
        return outputAddressService.saveInDbConsumedAddress(address);
    }

    @Override
    public List<Address> findAddressByInfo(AddressDto addressDto) {
        return outputAddressService.findAddressByInfo(addressDto);
    }

    @Override
    public List<Address> getAllAddresses() {
        return outputAddressService.getAllAddresses();
    }

    @Override
    public Address getAddress(String addressID) throws AddressNotFoundException {
        Address address = outputAddressService.getAddress(addressID);
        if(address==null)
            throw new AddressNotFoundException(ExceptionMgs.ADDRESS_NOT_FOUND_EXCEPTION);
        return address;
    }

    @Override
    public List<Address> getAddressesOfGivenCity(String city) throws AddressCityNotFoundException {

        List<Address> addresses = outputAddressService.getAddressesOfGivenCity(city);
        if (addresses.isEmpty()) {
            throw new AddressCityNotFoundException(ExceptionMgs.ADDRESS_CITY_NOT_EXCEPTION);
        }
        return addresses;
    }

    /*send addressId event to kafka topic and consumer consumes it*/
    @Override
    public Address produceAndConsumeAddressDelete(String addressId) throws AddressNotFoundException, AddressAlreadyAssignedCompanyException,
            AddressAlreadyAssignedEmployeeException {
        Address address = getAddress(addressId);
        Company company = outputRemoteCompanyService.getRemoteCompanyOnGivenAddress(address.getAddressId());
        if (company!=null) {
            throw new AddressAlreadyAssignedCompanyException(
                    ExceptionMgs.ADDRESS_ASSIGNED_COMPANY_EXCEPTION+": "+ company);
        }
        List<Employee> employees = outputRemoteEmployeeService.getRemoteEmployeesLivingAtAddress(address.getAddressId());
        if(!employees.isEmpty()){
            throw new AddressAlreadyAssignedEmployeeException(ExceptionMgs.ADDRESS_ASSIGNED_EMPLOYEE_EXCEPTION+": "+ employees);
        }
        AddressAvro addressAvro = AddressMapper.mapBeanToAvro(address);
        return AddressMapper.mapAvroToBean(outputKafkaProducerAddressService.sendKafkaAddressDeleteEvent(addressAvro));
    }

    @Override
    public String deleteAddress(String addressId) throws AddressNotFoundException {
        Address address = getAddress(addressId);
        outputAddressService.deleteAddress(address.getAddressId());
        return "Address <" + address + "> successfully deleted";
    }

    @Override
    public Address produceAndConsumeAddressEdit(AddressDto payload, String addressId) throws AddressNotFoundException {
        checkAddressValidity(payload);
        Address address = getAddress(addressId);
        address.setNum(payload.getNum());
        address.setStreet(payload.getStreet());
        address.setPb(payload.getPb());
        address.setCity(payload.getCity());
        address.setCountry(payload.getCountry());
        AddressAvro addressAvro = AddressMapper.mapBeanToAvro(address);
        return AddressMapper.mapAvroToBean(outputKafkaProducerAddressService.sendKafkaAddressEditEvent(addressAvro));
    }

    @Override
    public Address editAddress(Address address) {
        return outputAddressService.updateAddress(address);
    }

    @Override
    public List<Employee> getRemoteEmployeesLivingAtAddress(String addressId) throws AddressNotFoundException {
        return outputRemoteEmployeeService.getRemoteEmployeesLivingAtAddress(addressId);
    }
}
