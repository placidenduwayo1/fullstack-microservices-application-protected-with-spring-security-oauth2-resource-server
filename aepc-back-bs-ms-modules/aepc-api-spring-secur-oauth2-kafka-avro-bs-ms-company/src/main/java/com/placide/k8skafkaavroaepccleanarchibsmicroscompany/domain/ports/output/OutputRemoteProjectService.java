package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.CompanyNotFoundException;

import java.util.List;

public interface OutputRemoteProjectService {
    List<Project> getRemoteProjectsOfCompany(String companyId) throws CompanyNotFoundException;
}
