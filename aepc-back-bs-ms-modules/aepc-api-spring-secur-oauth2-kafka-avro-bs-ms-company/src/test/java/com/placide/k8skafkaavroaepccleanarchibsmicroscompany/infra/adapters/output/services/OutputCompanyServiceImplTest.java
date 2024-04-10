package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.services;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.avrobeans.CompanyAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.CompanyNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models.AddressModel;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.proxies.AddressServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper.CompanyMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyModel;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
class OutputCompanyServiceImplTest {

    @Mock
    private CompanyRepository repository;
    @Mock
    private AddressServiceProxy addressServiceProxy;
    @InjectMocks
    private OutputCompanyServiceImpl underTest;
    private Company company;
    private static final String ADDRESS_ID = "1L";
    private Address address;
    private static final String TOPIC1 = "topic1";
    private static final String TOPIC2 = "topic2";
    private static final String TOPIC3 = "topic3";
    private CompanyAvro companyAvro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //PREPARE
        address = Address.builder()
                .addressId(ADDRESS_ID)
                .num(2)
                .street("Allée de la Haye du Temple")
                .pb(59160).city("Lomme")
                .country("France")
                .build();

        company = new Company.CompanyBuilder()
                .companyId("uuid")
                .name("natan")
                .agency("paris")
                .type("esn")
                .connectedDate(new Timestamp(System.currentTimeMillis()).toString())
                .addressId(ADDRESS_ID)
                .address(address)
                .build();
        companyAvro = CompanyMapper.fromBeanToAvro(company);
    }

    @Test
    void consumeKafkaEventCompanyCreate() {
        //PREPARE

        //EXECUTE
        Company consumed = underTest.consumeKafkaEventCompanyCreate(companyAvro, TOPIC1);
        log.info("consumed company to create <{}> ", consumed);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertNotNull(consumed),
                ()->Assertions.assertEquals(consumed.getCompanyId(), company.getCompanyId()),
                ()->Assertions.assertEquals(consumed.getName(), company.getName()),
                ()->Assertions.assertEquals(consumed.getAgency(), company.getAgency())
        );
    }

    @Test
    void saveCompany() {
        //PREPARE
        Company consumed = underTest.consumeKafkaEventCompanyCreate(companyAvro, TOPIC1);
        CompanyModel model = CompanyMapper.fromBeanToModel(consumed);
        AddressModel addressModel = AddressMapper.toModel(address);
        CompanyModel expectedModel = CompanyModel.builder()
                .companyId(UUID.randomUUID().toString())
                .name("natan")
                .agency("lille")
                .type("ens")
                .connectedDate(new Timestamp(System.currentTimeMillis()).toString())
                .addressId(ADDRESS_ID)
                .addressModel(addressModel)
                .build();
        //EXECUTE
        Mockito.when(repository.save(model)).thenReturn(expectedModel);
        Company saved = underTest.saveCompany(consumed);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertNotNull(saved),
                () -> Assertions.assertNotEquals(saved, consumed),
                () -> Mockito.verify(repository, Mockito.atLeast(1)).save(model));
    }

    @Test
    void getCompanyById() throws CompanyNotFoundException {
        //PREPARE
        String id = "uuid";
        CompanyModel model = CompanyMapper.fromBeanToModel(company);
        //EXECUTE
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(model));
        Company obtained = underTest.getCompanyById(id);
        //VERIFY
        Assertions.assertAll("grp of assertions",()->{
            Assertions.assertNotNull(obtained);
            Mockito.verify(repository, Mockito.atLeast(1)).findById(id);
            log.info("{}", obtained);
        });
    }

    @Test
    void loadCompanyByInfo() {
        //PREPARE
        List<Company> companies = List.of(company);
        String name = "natan";
        String agency = "paris";
        String type = "esn";
        List<CompanyModel> models = companies.stream().map(CompanyMapper::fromBeanToModel).toList();
        //EXECUTE
        Mockito.when(repository.findByNameAndAgencyAndType(name, agency, type)).thenReturn(models);
        List<Company> obtainedCompanies = underTest.loadCompanyByInfo(name, agency, type);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertEquals(1, obtainedCompanies.size()),
                () -> Mockito.verify(repository, Mockito.atLeast(1)).findByNameAndAgencyAndType(name, agency, type),
                () -> Assertions.assertFalse(obtainedCompanies.contains(company))

        );
    }

    @Test
    void loadAllCompanies() {
        //PREPARE
        List<Company> companies = List.of(company);
        List<CompanyModel> models = companies.stream().map(CompanyMapper::fromBeanToModel).toList();
        //EXECUTE
        Mockito.when(repository.findAll()).thenReturn(models);
        List<Company> obtained = underTest.loadAllCompanies();
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Assertions.assertEquals(1, obtained.size()),
                () -> Mockito.verify(repository, Mockito.atLeast(1)).findAll());
    }

    @Test
    void consumeKafkaEventCompanyDelete() {
        //PREPARE

        //EXECUTE
        Company consumed = underTest.consumeKafkaEventCompanyDelete(companyAvro, TOPIC2);
        //VERIFY
        Assertions.assertNotNull(consumed);
    }

    @Test
    void deleteCompany() throws CompanyNotFoundException, RemoteApiAddressNotLoadedException {
        //PREPARE
        String id = "uuid";
        CompanyModel model = CompanyMapper.fromBeanToModel(company);
        AddressModel addressModel = AddressMapper.toModel(address);
        //EXECUTE
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(model));
        Company obtained = underTest.getCompanyById(id);
        Mockito.when(addressServiceProxy.loadRemoteApiGetAddressById(ADDRESS_ID)).thenReturn(addressModel);
        String expectedMsg = underTest.deleteCompany(id);
        //VERIFY
        Assertions.assertAll("grp of assertions",()->{
            Mockito.verify(repository, Mockito.atLeast(1)).deleteById(id);
            Assertions.assertNotNull(obtained);
            Assertions.assertNotNull(expectedMsg);
        });
    }

    @Test
    void consumeKafkaEventCompanyEdit() {
        //PREPARE

        //EXECUTE
        Company consumed = underTest.consumeKafkaEventCompanyEdit(companyAvro, TOPIC3);
        //VERIFY
        Assertions.assertNotNull(consumed);
    }

    @Test
    void editCompany() throws CompanyNotFoundException {
        //PREPARE
        String id = "uuid";
        Company updated = new Company.CompanyBuilder()
                .companyId("uuid")
                .name("natan")
                .agency("Lille")
                .type("client")
                .connectedDate(new Timestamp(System.currentTimeMillis()).toString())
                .addressId(ADDRESS_ID)
                .address(address)
                .build();
        CompanyModel model = CompanyMapper.fromBeanToModel(updated);
        //EXECUTE
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(model));
        Company obtained = underTest.getCompanyById(id);
        Mockito.when(repository.findById(obtained.getCompanyId())).thenReturn(Optional.of(model));
        Company consumed = underTest.consumeKafkaEventCompanyEdit(companyAvro, TOPIC3);
        CompanyModel mapped = CompanyMapper.fromBeanToModel(consumed);
        Mockito.when(repository.save(mapped)).thenReturn(model);
        Company saved = underTest.editCompany(consumed);
        //VERIFY
        Assertions.assertAll("grp of assertions",
                () -> Mockito.verify(repository, Mockito.atLeast(1)).save(mapped),
                () -> Mockito.verify(repository, Mockito.atLeast(1)).findById(id),
                () -> Assertions.assertNotNull(saved),
                () -> Assertions.assertNotNull(consumed));

    }
}