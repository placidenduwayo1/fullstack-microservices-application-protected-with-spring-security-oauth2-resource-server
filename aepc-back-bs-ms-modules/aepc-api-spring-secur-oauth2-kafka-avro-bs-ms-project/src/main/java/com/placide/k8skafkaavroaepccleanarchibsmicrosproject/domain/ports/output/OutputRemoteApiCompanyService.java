package com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosproject.domain.exceptions.RemoteCompanyApiException;

import java.util.List;

public interface OutputRemoteApiCompanyService {
    Company getRemoteCompanyAPI(String companyId) throws RemoteCompanyApiException;
    List<Company> getRemoteCompaniesByName(String companyName);
    List<Company> getCompaniesByAgency(String agency);
}
