package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.exceptions.AddressNotFoundException;

public interface OutputRemoteCompanyService {
    Company getRemoteCompanyOnGivenAddress(String addressId) throws AddressNotFoundException;

}
