package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.input;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.RemoteCompanyApiException;

import java.util.List;

public interface InputRemoteApiCompanyService {
    Company getRemoteApiCompany(String companyId) throws RemoteCompanyApiException;
    List<Company> getRemoteCompaniesByName(String companyName);
    List<Company> getRemoteCompaniesByAgency(String agency);
}
