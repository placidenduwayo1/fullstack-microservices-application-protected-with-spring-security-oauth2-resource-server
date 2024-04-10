package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.controller;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Employee;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.input.InputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api-address")
public class AddressController {

    private final InputAddressService inputAddressService;
    @GetMapping(value = "")
    public String getWelcome(){
        return "Welcome to business microservice address that managing users' addresses";
    }
    @PostMapping(value = "/addresses")
    public List<String> produceAndConsumeAddress(@RequestBody AddressDto addressDto) throws
            AddressAlreadyExistsException, AddressFieldsInvalidException {

        Address producedConsumedAddress = inputAddressService.produceAndConsumeAddressAdd(addressDto);
        Address savedAddress = inputAddressService.saveInDbConsumedAddress(producedConsumedAddress);
        return List.of("produced consumed:"+producedConsumedAddress,"saved:"+savedAddress);
    }

    @GetMapping(value = "/addresses")
    public List<Address> getAllAddresses() {
        return inputAddressService.getAllAddresses();
    }

    @GetMapping(value = "/addresses/id/{addressId}")
    public Address getAddress(@PathVariable(name = "addressId") String addressId) throws
            AddressNotFoundException {
        return inputAddressService.getAddress(addressId);
    }

    @DeleteMapping(value = "/addresses/id/{addressId}")
    public void deleteAddress(@PathVariable(name = "addressId") String addressId) throws
            AddressNotFoundException, AddressAlreadyAssignedCompanyException, AddressAlreadyAssignedEmployeeException {
        Address consumedAddress = inputAddressService.produceAndConsumeAddressDelete(addressId);
        inputAddressService.deleteAddress(consumedAddress.getAddressId());
    }

    @PutMapping(value = "/addresses/id/{addressId}")
    public List<String> editAddress(@RequestBody AddressDto addressDto,
                                    @PathVariable(name = "addressId") String addressId) throws AddressNotFoundException {

        Address produceAndConsumeAddress = inputAddressService.produceAndConsumeAddressEdit(addressDto, addressId);
        Address savedAddress = inputAddressService.editAddress(produceAndConsumeAddress);
        return List.of("produced consumed:"+produceAndConsumeAddress,"saved:"+savedAddress);
    }
    @GetMapping(value = "/addresses/city/{city}")
    public List<Address> getAllAddressesForGivenCity(@PathVariable(name = "city") String city) throws
            AddressCityNotFoundException {
        return inputAddressService.getAddressesOfGivenCity(city);
    }
    @GetMapping(value = "/employees/addresses/id/{addressId}")
    public List<Employee> getRemoteEmployeesLivingAtAddress(@PathVariable(name = "addressId") String addressId) throws AddressNotFoundException {
        return inputAddressService.getRemoteEmployeesLivingAtAddress(addressId);
    }

}
