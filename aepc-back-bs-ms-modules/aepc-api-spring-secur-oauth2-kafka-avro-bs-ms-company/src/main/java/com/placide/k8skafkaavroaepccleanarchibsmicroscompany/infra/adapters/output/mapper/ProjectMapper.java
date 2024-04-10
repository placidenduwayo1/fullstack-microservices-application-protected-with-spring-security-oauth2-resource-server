package com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicroscompany.infra.adapters.input.feignclient.models.ProjectModel;
import org.springframework.beans.BeanUtils;

public class ProjectMapper {
    private ProjectMapper(){}
    public static Project toBean(ProjectModel model){
        Project bean = Project.builder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
}
