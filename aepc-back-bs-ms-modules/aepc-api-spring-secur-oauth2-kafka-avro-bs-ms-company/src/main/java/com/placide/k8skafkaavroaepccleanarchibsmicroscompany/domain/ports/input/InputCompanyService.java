package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.input;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.*;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyDto;

import java.util.List;

public interface InputCompanyService {
    Company produceKafkaEventCompanyCreate(CompanyDto dto) throws CompanyTypeInvalidException,
            CompanyEmptyFieldsException, CompanyAlreadyExistsException, RemoteApiAddressNotLoadedException,
            RemoteAddressAlreadyHoldsCompanyException;
    Company createCompany(Company company) throws CompanyAlreadyExistsException, CompanyEmptyFieldsException,
            CompanyTypeInvalidException, RemoteApiAddressNotLoadedException;
    Company getCompanyById(String id) throws RemoteApiAddressNotLoadedException, CompanyNotFoundException;
    List<Company> loadCompanyByInfo(String name, String agency, String type);
    List<Company> loadAllCompanies();
    Company produceKafkaEventCompanyDelete(String id) throws CompanyNotFoundException, RemoteApiAddressNotLoadedException,
            CompanyAlreadyAssignedRemoteProjectsException;
    String deleteCompany(String id) throws CompanyNotFoundException, RemoteApiAddressNotLoadedException;
    Company produceKafkaEventCompanyEdit(CompanyDto payload, String id) throws CompanyNotFoundException, CompanyTypeInvalidException,
            CompanyEmptyFieldsException, RemoteApiAddressNotLoadedException,
            RemoteAddressAlreadyHoldsCompanyException;
    Company editCompany(Company payload) throws RemoteApiAddressNotLoadedException;
    List<Company> getCompaniesByName(String companyName);
    List<Company> getCompanyByAgency(String agency);
    Company getCompanyOfGivenAddressId(String addressId) throws RemoteApiAddressNotLoadedException, CompanyNotFoundException;
    List<Company> getCompaniesOfGivenAddressCity(String city) throws RemoteApiAddressNotLoadedException, CompanyNotFoundException;

    List<Project> getProjectsAssignedToRemoteCompany(String companyId) throws CompanyNotFoundException;
}
