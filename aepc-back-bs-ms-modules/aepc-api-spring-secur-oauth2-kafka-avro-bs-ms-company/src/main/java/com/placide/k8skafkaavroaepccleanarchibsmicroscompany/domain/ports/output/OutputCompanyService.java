package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.avrobeans.CompanyAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.CompanyNotFoundException;

import java.util.List;

public interface OutputCompanyService {
    Company consumeKafkaEventCompanyCreate(CompanyAvro companyAvro, String topic);
    Company saveCompany(Company company);
    Company getCompanyById(String id) throws CompanyNotFoundException;
    List<Company> loadCompanyByInfo(String name, String agency,String type);
    List<Company> loadAllCompanies();
    Company consumeKafkaEventCompanyDelete(CompanyAvro companyAvro, String topic);
    String deleteCompany(String id) throws CompanyNotFoundException;
    Company consumeKafkaEventCompanyEdit(CompanyAvro companyAvro, String topic);
    Company editCompany(Company company);
    List<Company> loadCompaniesByName(String companyName);
    Company getCompanyOfGivenAddressId(String addressId) throws CompanyNotFoundException;
    List<Company> getCompanyByAgency(String agency);
}
