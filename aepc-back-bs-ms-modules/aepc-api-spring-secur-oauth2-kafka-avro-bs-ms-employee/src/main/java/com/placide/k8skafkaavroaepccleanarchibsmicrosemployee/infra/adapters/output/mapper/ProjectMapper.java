package com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.output.mapper;

import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.domain.beans.project.Project;
import com.placide.k8skafkaavroaepccleanarchibsmicrosemployee.infra.adapters.input.feignclients.models.ProjectModel;
import org.springframework.beans.BeanUtils;

public class ProjectMapper {
    private ProjectMapper(){}
    public static Project toBean(ProjectModel model){
        Project bean = Project.builder().build();
        BeanUtils.copyProperties(model,bean);
        return bean;
    }
}
