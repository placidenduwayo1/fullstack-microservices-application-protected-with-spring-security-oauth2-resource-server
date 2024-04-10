package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.input.controller;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressAlreadyAssignedCompanyException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressAlreadyAssignedEmployeeException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressAlreadyExistsException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.input.InputAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.controller.AddressController;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.models.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

class AddressControllerTest {
    @Mock
    private InputAddressService inputAddressServiceMock;
    @InjectMocks
    private AddressController underTest;
    private final String addressId = "1L";
    private final Address address = new Address.AddressBuilder()
            .addressId(addressId)
            .num(184)
            .street("Avenue de Liège")
            .pb(59300)
            .city("Valenciennes")
            .country("France")
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWelcome() {
        String msg = underTest.getWelcome();
        Assertions.assertNotNull(msg);
    }

    @Test
    void produceAndConsumeAddress() throws AddressAlreadyExistsException {
        //PREPARE
        AddressDto addressDto = AddressMapper.mapBeanToDto(address);
        //EXECUTE
        Mockito.when(inputAddressServiceMock.produceAndConsumeAddressAdd(addressDto)).thenReturn(address);
        List<String> consumedAndSaved = underTest.produceAndConsumeAddress(addressDto);
        //VERIFY
        Assertions.assertAll("props", () -> {
            Mockito.verify(inputAddressServiceMock, Mockito.atLeast(1)).produceAndConsumeAddressAdd(addressDto);
            Mockito.verify(inputAddressServiceMock, Mockito.atLeast(1)).saveInDbConsumedAddress(address);
            Assertions.assertEquals(2, consumedAndSaved.size());
        });
    }

    @Test
    void getAllAddresses() {
        //PREPARE
        Address address1 = new Address.AddressBuilder()
                .addressId(addressId)
                .num(184)
                .street("Avenue de Liège")
                .pb(59300)
                .city("Valenciennes")
                .country("France")
                .build();
        Address address2 = new Address.AddressBuilder()
                .addressId("2L")
                .num(44)
                .street("Rue Notre Dame des Victoires")
                .pb(75002)
                .city("Paris")
                .country("France")
                .build();
        Address address3 = new Address.AddressBuilder()
                .addressId("3L")
                .num(55)
                .street("Avenue Vendredi")
                .pb(91000)
                .city("Kibenga")
                .country("Burundi")
                .build();
        List<Address> addresses = List.of(address1, address2, address3);
        //EXECUTE
        Mockito.when(inputAddressServiceMock.getAllAddresses()).thenReturn(addresses);
        List<Address> actualAddresses = underTest.getAllAddresses();
        //VERIFY
        Assertions.assertAll("props", () -> {
            Assertions.assertEquals(addresses.size(), actualAddresses.size());
            Mockito.verify(inputAddressServiceMock, Mockito.atLeast(1)).getAllAddresses();
        });
    }

    @Test
    void getAddress() throws AddressNotFoundException {
        //PREPARE
        Address expectedAddress = new Address.AddressBuilder()
                .addressId(addressId)
                .num(44)
                .street("Rue Notre Dame des Victoires")
                .pb(75002)
                .city("Paris")
                .country("France")
                .build();
        //EXECUTE
        Mockito.when(inputAddressServiceMock.getAddress(addressId)).thenReturn(expectedAddress);
        Address actualAddress = underTest.getAddress(addressId);
        //VERIFY
        Assertions.assertAll("props", () -> {
            Mockito.verify(inputAddressServiceMock, Mockito.atLeast(1)).getAddress(addressId);
            Assertions.assertEquals(expectedAddress, actualAddress);
            Assertions.assertEquals(44, actualAddress.getNum());
            Assertions.assertSame(expectedAddress.getCity(), actualAddress.getCity());
        });
    }

    @Test
    void deleteAddress() throws AddressNotFoundException, AddressAlreadyAssignedCompanyException, AddressAlreadyAssignedEmployeeException {
        //PREPARE
        Address expectedAddress = new Address.AddressBuilder()
                .addressId(addressId)
                .num(44)
                .street("Rue Notre Dame des Victoires")
                .pb(75002)
                .city("Paris")
                .country("France")
                .build();
        //EXECUTE
        Mockito.when(inputAddressServiceMock.produceAndConsumeAddressDelete(addressId)).thenReturn(expectedAddress);
        underTest.deleteAddress(addressId);
        //VERIFY
        Assertions.assertAll("props", () -> Mockito
                .verify(inputAddressServiceMock, Mockito.atLeast(1))
                .deleteAddress(expectedAddress.getAddressId()));
    }

    @Test
    void editAddress() throws AddressNotFoundException {
        //PREPARE
        Address expectedAddress = new Address.AddressBuilder()
                .addressId(addressId)
                .num(44)
                .street("Rue Notre Dame des Victoires")
                .pb(75002)
                .city("Paris")
                .country("France")
                .build();

        Address consumedAddress = new Address.AddressBuilder()
                .addressId(addressId)
                .num(44)
                .street("Rue Notre Dame des Victoires")
                .pb(75002)
                .city("Paris")
                .country("France")
                .build();
        AddressDto addressDto = new AddressDto(
                44, "Rue Notre Dame des Victoires",
                74002, "Paris", "France");
        //EXECUTE
        Mockito.when(inputAddressServiceMock.produceAndConsumeAddressEdit(addressDto, addressId)).thenReturn(consumedAddress);
        Mockito.when(inputAddressServiceMock.editAddress(consumedAddress)).thenReturn(expectedAddress);
        List<String> response = underTest.editAddress(addressDto, addressId);

        //VERIFY
        Assertions.assertAll("props", () -> {
            Assertions.assertEquals(2, response.size());
            Mockito.verify(inputAddressServiceMock, Mockito.atLeast(1)).editAddress(consumedAddress);
        });
    }
}