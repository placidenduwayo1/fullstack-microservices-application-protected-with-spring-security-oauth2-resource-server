package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.services;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.avrobeans.CompanyAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.CompanyNotFoundException;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.ExceptionMsg;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.RemoteApiAddressNotLoadedException;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputRemoteAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputRemoteProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models.AddressModel;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.proxies.AddressServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.proxies.ProjectServiceProxy;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper.AddressMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper.CompanyMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper.ProjectMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyModel;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OutputCompanyServiceImpl implements OutputCompanyService, OutputRemoteAddressService, OutputRemoteProjectService {
    private final CompanyRepository repository;
    private final AddressServiceProxy addressServiceProxy;
    private final ProjectServiceProxy projectServiceProxy;

    public OutputCompanyServiceImpl(CompanyRepository repository,
                                    @Qualifier(value = "addressserviceproxy") AddressServiceProxy addressServiceProxy,
                                    @Qualifier(value = "projectserviceproxy") ProjectServiceProxy projectServiceProxy) {
        this.repository = repository;
        this.addressServiceProxy = addressServiceProxy;
        this.projectServiceProxy = projectServiceProxy;
    }

    @Override
    @KafkaListener(topics = "avro-companies-created", groupId = "company-group-id")
    public Company consumeKafkaEventCompanyCreate(@Payload CompanyAvro companyAvro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Company company = CompanyMapper.fromAvroToBean(companyAvro);
        log.info("company to create:<{}> consumed from topic:<{}>", company, topic);
        return company;
    }

    @Override
    public Company saveCompany(Company company) {
        CompanyAvro companyAvro = CompanyMapper.fromBeanToAvro(company);
        Company consumed = consumeKafkaEventCompanyCreate(companyAvro, "company-group-id");
        CompanyModel savedModel = repository.save(CompanyMapper.fromBeanToModel(consumed));
        return CompanyMapper.fromModelToBean(savedModel);
    }

    @Override
    public Company getCompanyById(String id) throws CompanyNotFoundException {
        CompanyModel model = repository.findById(id).orElseThrow(
                ()-> new CompanyNotFoundException(ExceptionMsg.COMPANY_NOT_FOUND_EXCEPTION));
        return CompanyMapper.fromModelToBean(model);
    }

    @Override
    public List<Company> loadCompanyByInfo(String name, String agency, String type) {
        List<CompanyModel> models = repository.findByNameAndAgencyAndType(name, agency, type);
        return mapToBean(models);
    }

    @Override
    public List<Company> loadAllCompanies() {
        return mapToBean(repository.findAll());
    }

    @Override
    @KafkaListener(topics = "avro-companies-deleted", groupId = "company-group-id")
    public Company consumeKafkaEventCompanyDelete(@Payload CompanyAvro companyAvro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Company company = CompanyMapper.fromAvroToBean(companyAvro);
        log.info("company to delete:<{}> consumed from topic:<{}>", company, topic);
        return company;
    }

    @Override
    public String deleteCompany(String id) throws CompanyNotFoundException {
        Company company = getCompanyById(id);
        repository.deleteById(company.getCompanyId());
        return "company <" + company + "> deleted";
    }

    @Override
    @KafkaListener(topics = "avro-companies-edited", groupId = "company-group-id")
    public Company consumeKafkaEventCompanyEdit(@Payload CompanyAvro companyAvro, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Company company = CompanyMapper.fromAvroToBean(companyAvro);
        log.info("company to edit:<{}> consumed from topic:<{}>", company, topic);
        return company;
    }

    @Override
    public Company editCompany(Company company) {
        CompanyAvro companyAvro = CompanyMapper.fromBeanToAvro(company);
        Company consumed = consumeKafkaEventCompanyEdit(companyAvro, "${topics.names.topic3}");
        CompanyModel mapped = CompanyMapper.fromBeanToModel(consumed);
        return CompanyMapper.fromModelToBean(repository.save(mapped));
    }

    @Override
    public List<Company> loadCompaniesByName(String companyName) {
        return mapToBean(repository.findByName(companyName));
    }

    @Override
    public Company getCompanyOfGivenAddressId(String addressId) throws CompanyNotFoundException {
       CompanyModel model =repository.findByAddressId(addressId);
       if(model==null){
           throw new CompanyNotFoundException("Company not found Exception");
       }
        return CompanyMapper.fromModelToBean(model);
    }

    @Override
    public List<Company> getCompanyByAgency(String agency) {
        List<CompanyModel> companyModels = repository.findByAgency(agency);
        return companyModels.stream()
                .map(CompanyMapper::fromModelToBean)
                .toList();
    }

    @Override
    public Address getRemoteAddressById(String addressId) throws RemoteApiAddressNotLoadedException {
        return AddressMapper.toBean(addressServiceProxy.loadRemoteApiGetAddressById(addressId));
    }

    @Override
    public List<Address> getRemoteAddressesByCity(String city) {
        List<AddressModel> addresses = addressServiceProxy.loadRemoteApiAddressesByCity(city);
        return addresses.stream()
                .map(AddressMapper::toBean)
                .toList();
    }

    @Override
    public List<Project> getRemoteProjectsOfCompany(String companyId) throws CompanyNotFoundException {
        Company company =getCompanyById(companyId);
        return projectServiceProxy.loadRemoteProjectsOfCompany(company.getCompanyId())
                .stream()
                .map(ProjectMapper::toBean)
                .toList();
    }

    private List<Company> mapToBean(List<CompanyModel> models) {
        return models.stream()
                .map(CompanyMapper::fromModelToBean)
                .toList();
    }
}
