package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.domain.beans.Company;
import com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.models.CompanyModel;
import org.springframework.beans.BeanUtils;

public class CompanyMapper {
    private CompanyMapper(){}
    public static Company toBean(CompanyModel model){
        Company bean = Company.builder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
}
