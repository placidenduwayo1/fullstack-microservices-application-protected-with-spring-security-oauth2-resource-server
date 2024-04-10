package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.usecase;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.avrobeans.CompanyAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.input.InputCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.input.InputRemoteAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputCompanyService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputKafkaProducerService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputRemoteAddressService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output.OutputRemoteProjectService;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper.CompanyMapper;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyDto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompanyUseCase implements InputCompanyService, InputRemoteAddressService {
    private final OutputKafkaProducerService kafkaProducerService;
    private final OutputCompanyService outputCompanyService;
    private final OutputRemoteAddressService outputRemoteAddressService;
    private final OutputRemoteProjectService outputRemoteProjectService;
    public CompanyUseCase(OutputKafkaProducerService kafkaProducerService, OutputCompanyService outputCompanyService,
                          OutputRemoteAddressService outputRemoteAddressService, OutputRemoteProjectService outputRemoteProjectService) {
        this.kafkaProducerService = kafkaProducerService;
        this.outputCompanyService = outputCompanyService;
        this.outputRemoteAddressService = outputRemoteAddressService;
        this.outputRemoteProjectService = outputRemoteProjectService;
    }
    private void checkPayloadValidity(CompanyDto dto) throws CompanyEmptyFieldsException, CompanyTypeInvalidException,
            RemoteApiAddressNotLoadedException {
        if (!Validator.areValidCompanyFields(dto.getName(), dto.getAgency(), dto.getType(), dto.getAddressId()))
            throw new CompanyEmptyFieldsException(ExceptionMsg.COMPANY_FIELDS_EMPTY_EXCEPTION);

        if (!Validator.checkTypeExists(dto.getType()))
            throw new CompanyTypeInvalidException(ExceptionMsg.COMPANY_TYPE_UNKNOWN_EXCEPTION);

        Address address = getRemoteAddressById(dto.getAddressId());
        if (Validator.remoteAddressApiUnreachable(address.getAddressId()))
            throw new RemoteApiAddressNotLoadedException(address.toString());
    }
    private void checkCompanyAlreadyExists(CompanyDto dto) throws CompanyAlreadyExistsException {
        if (!loadCompanyByInfo(dto.getName(), dto.getAgency(), dto.getType()).isEmpty())
            throw new CompanyAlreadyExistsException(ExceptionMsg.COMPANY_ALREADY_EXISTS_EXCEPTION);

    }

    private List<String> loadAddressesAssignedCompanies(){
        List<String> addressesIds = new ArrayList<>();
        loadAllCompanies().forEach(company -> addressesIds.add(company.getAddressId()));
        return addressesIds;
    }

    private void setCompanyDependency(Company company, String addressId) throws RemoteApiAddressNotLoadedException {
        Address address = getRemoteAddressById(addressId);
        company.setAddressId(addressId);
        company.setAddress(address);
    }

    @Override
    public Company produceKafkaEventCompanyCreate(CompanyDto dto) throws CompanyTypeInvalidException, CompanyEmptyFieldsException,
            CompanyAlreadyExistsException, RemoteApiAddressNotLoadedException, RemoteAddressAlreadyHoldsCompanyException {
        Validator.format(dto);
        checkPayloadValidity(dto);
        checkCompanyAlreadyExists(dto);

        List<String> addressesId = loadAddressesAssignedCompanies();
        if(addressesId.contains(dto.getAddressId())){
            throw new RemoteAddressAlreadyHoldsCompanyException(ExceptionMsg.REMOTE_ADDRESS_COMPANY_EXISTS_ON_ADDRESS_EXCEPTION);
        }
        Company bean = CompanyMapper.fromDtoToBean(dto);
        bean.setCompanyId(UUID.randomUUID().toString());
        bean.setConnectedDate(Timestamp.from(Instant.now()).toString());
        setCompanyDependency(bean, dto.getAddressId());
        CompanyAvro companyAvro = CompanyMapper.fromBeanToAvro(bean);
        return CompanyMapper.fromAvroToBean(kafkaProducerService.produceKafkaEventCompanyCreate(companyAvro));
    }

    @Override
    public Company createCompany(Company company) throws RemoteApiAddressNotLoadedException {
        Company saved = outputCompanyService.saveCompany(company);
        setCompanyDependency(saved, saved.getAddressId());
        return saved;
    }

    @Override
    public Company getCompanyById(String id) throws RemoteApiAddressNotLoadedException, CompanyNotFoundException {
        Company company = outputCompanyService.getCompanyById(id);
        setCompanyDependency(company, company.getAddressId());
        return company;
    }

    @Override
    public List<Company> loadCompanyByInfo(String name, String agency, String type) {
        List<Company> companies = outputCompanyService.loadCompanyByInfo(name, agency, type);
        companies.forEach(company -> {
            try {
                setCompanyDependency(company, company.getAddressId());
            } catch (RemoteApiAddressNotLoadedException e) {
                e.getMessage();
            }
        });
        return companies;
    }

    @Override
    public List<Company> loadAllCompanies() {
        List<Company> companies = outputCompanyService.loadAllCompanies();
        companies.forEach(company -> {
            try {
                setCompanyDependency(company, company.getAddressId());
            } catch (RemoteApiAddressNotLoadedException e) {
                e.getMessage();
            }
        });

        return companies;
    }

    @Override
    public Company produceKafkaEventCompanyDelete(String id) throws CompanyNotFoundException, CompanyAlreadyAssignedRemoteProjectsException,
            RemoteApiAddressNotLoadedException {
        Company company = getCompanyById(id);
        List<Project> projects = outputRemoteProjectService.getRemoteProjectsOfCompany(id);
        if(!projects.isEmpty()){
            throw new CompanyAlreadyAssignedRemoteProjectsException(ExceptionMsg.COMPANY_ASSIGNED_PROJECT_EXCEPTION+": "+ projects);
        }
        setCompanyDependency(company, company.getAddressId());
        CompanyAvro companyAvro = CompanyMapper.fromBeanToAvro(company);
        return CompanyMapper.fromAvroToBean(kafkaProducerService.produceKafkaEventCompanyDelete(companyAvro));
    }

    @Override
    public String deleteCompany(String id) throws CompanyNotFoundException, RemoteApiAddressNotLoadedException {
        Company company = getCompanyById(id);
        outputCompanyService.deleteCompany(company.getCompanyId());
        return "Company <" + company + "> successfully deleted";
    }

    @Override
    public Company produceKafkaEventCompanyEdit(CompanyDto payload, String id) throws CompanyNotFoundException,
            CompanyTypeInvalidException, CompanyEmptyFieldsException, RemoteApiAddressNotLoadedException,
            RemoteAddressAlreadyHoldsCompanyException {
        Validator.format(payload);
        checkPayloadValidity(payload);
        Company company = getCompanyById(id);

        List<String> addressesIds = loadAddressesAssignedCompanies();
        Company companyOnAddressId = null;
        if(addressesIds.contains(payload.getAddressId()) && !company.getAddressId().equals(payload.getAddressId())){
            companyOnAddressId = getCompanyOfGivenAddressId(payload.getAddressId());
        }
        if(companyOnAddressId!=null){
            throw new RemoteAddressAlreadyHoldsCompanyException(ExceptionMsg.REMOTE_ADDRESS_COMPANY_EXISTS_ON_ADDRESS_EXCEPTION
                    +": "+companyOnAddressId);
        }
        company.setName(payload.getName());
        company.setAgency(payload.getAgency());
        company.setType(payload.getType());
        setCompanyDependency(company, payload.getAddressId());
        CompanyAvro companyAvro = CompanyMapper.fromBeanToAvro(company);
        return CompanyMapper.fromAvroToBean(kafkaProducerService.produceKafkaEventCompanyEdit(companyAvro));
    }

    @Override
    public Company editCompany(Company payload) throws RemoteApiAddressNotLoadedException {
        Company company = outputCompanyService.editCompany(payload);
        setCompanyDependency(company, company.getAddressId());
        return company;
    }

    @Override
    public List<Company> getCompaniesByName(String companyName) {
        List<Company> companies = outputCompanyService.loadCompaniesByName(companyName);
        companies.forEach(company -> {
            try {
                setCompanyDependency(company, company.getAddressId());
            } catch (RemoteApiAddressNotLoadedException e) {
                e.getMessage();
            }
        });
        return companies;
    }

    @Override
    public List<Company> getCompanyByAgency(String agency) {
        List<Company> companies = outputCompanyService.getCompanyByAgency(agency);
        companies.forEach(company -> {
            try {
                setCompanyDependency(company, company.getAddressId());
            } catch (RemoteApiAddressNotLoadedException e) {
                e.getMessage();
            }
        });
        return companies;
    }

    @Override
    public Company getCompanyOfGivenAddressId(String addressId) throws RemoteApiAddressNotLoadedException, CompanyNotFoundException {
       Company company = outputCompanyService.getCompanyOfGivenAddressId(addressId);
       setCompanyDependency(company, company.getAddressId());
       return company;
    }

    @Override
    public List<Company> getCompaniesOfGivenAddressCity(String city) throws RemoteApiAddressNotLoadedException, CompanyNotFoundException {
        List<Address> addresses = getRemoteAddressesByCity(city);
        for(Address a: addresses){
            if (a.getAddressId().equals(ExceptionMsg.REMOTE_ADDRESS_API_EXCEPTION)) {
                throw new RemoteApiAddressNotLoadedException(a.toString());
            }
        }

        List<Company> companies = new ArrayList<>();
        for (Address a : addresses) {
            companies.add(getCompanyOfGivenAddressId(a.getAddressId()));
        }
        companies.forEach(company -> {
            try {
                setCompanyDependency(company, company.getAddressId());
            } catch (RemoteApiAddressNotLoadedException e) {
                e.getMessage();
            }
        });
        return companies;
    }

    @Override
    public List<Project> getProjectsAssignedToRemoteCompany(String companyId) throws CompanyNotFoundException {
        return outputRemoteProjectService.getRemoteProjectsOfCompany(companyId);
    }

    @Override
    public Address getRemoteAddressById(String addressId) throws RemoteApiAddressNotLoadedException{
        return outputRemoteAddressService.getRemoteAddressById(addressId);
    }

    @Override
    public List<Address> getRemoteAddressesByCity(String city) {
        return outputRemoteAddressService.getRemoteAddressesByCity(city);
    }
}
