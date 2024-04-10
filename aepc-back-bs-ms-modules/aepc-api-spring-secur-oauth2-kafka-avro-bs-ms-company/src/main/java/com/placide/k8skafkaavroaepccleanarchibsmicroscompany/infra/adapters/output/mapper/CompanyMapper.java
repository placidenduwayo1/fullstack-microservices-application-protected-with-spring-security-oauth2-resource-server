package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.avrobeans.Address;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.avrobeans.CompanyAvro;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.company.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyDto;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.models.CompanyModel;
import org.springframework.beans.BeanUtils;

public class CompanyMapper {
    private CompanyMapper(){}
    public static CompanyModel fromBeanToModel(Company company){
        CompanyModel model = CompanyModel.builder().build();
        BeanUtils.copyProperties(company, model);
        return model;
    }
    public static Company fromModelToBean(CompanyModel model) {
        Company bean = new Company.CompanyBuilder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
    public static Company fromDtoToBean(CompanyDto dto){
        Company bean = new Company.CompanyBuilder().build();
        BeanUtils.copyProperties(dto,bean);
        return bean;
    }

    public static CompanyAvro fromBeanToAvro(Company company){
        Address addressAvro = Address.newBuilder()
                .setAddressId(company.getAddressId())
                .setNum(company.getAddress().getNum())
                .setStreet(company.getAddress().getStreet())
                .setPb(company.getAddress().getPb())
                .setCity(company.getAddress().getCity())
                .setCountry(company.getAddress().getCountry())
                .build();
        return CompanyAvro.newBuilder()
                .setCompanyId(company.getCompanyId())
                .setName(company.getName())
                .setAgency(company.getAgency())
                .setType(company.getType())
                .setConnectedDate(company.getConnectedDate())
                .setAddressId(company.getAddressId())
                .setAddress(addressAvro)
                .build();
    }

    public static Company fromAvroToBean(CompanyAvro companyAvro){
        com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address address =
                com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.address.Address.builder()
                        .addressId(companyAvro.getAddressId())
                        .num(companyAvro.getAddress().getNum())
                        .street(companyAvro.getAddress().getStreet())
                        .pb(companyAvro.getAddress().getPb())
                        .city(companyAvro.getAddress().getCity())
                        .country(companyAvro.getAddress().getCountry())
                        .build();

        return new Company.CompanyBuilder()
                .companyId(companyAvro.getCompanyId())
                .name(companyAvro.getName())
                .agency(companyAvro.getAgency())
                .type(companyAvro.getType())
                .connectedDate(companyAvro.getConnectedDate())
                .addressId(companyAvro.getAddressId())
                .address(address)
                .build();
    }
}
