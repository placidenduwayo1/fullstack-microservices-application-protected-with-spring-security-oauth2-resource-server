package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.ports.output;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.exceptions.RemoteApiAddressNotLoadedException;

import java.util.List;

public interface OutputRemoteAddressService {
    Address getRemoteAddressById(String addressId) throws RemoteApiAddressNotLoadedException;
    List<Address> getRemoteAddressesByCity(String city);
}
