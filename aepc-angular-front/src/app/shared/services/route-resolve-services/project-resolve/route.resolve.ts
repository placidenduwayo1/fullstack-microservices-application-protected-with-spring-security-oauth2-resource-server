import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, ResolveFn } from "@angular/router";
import { Project } from "src/app/shared/models/project/project.model";
import { ProjectService } from "../../rest-services/projects.service";

export const GetAllProjectsResolve : ResolveFn<Array<Project>> = ()=>{
  return inject(ProjectService).getAllProjects();
}
export const GetProjectByIDResolve: ResolveFn<Project> = (route: ActivatedRouteSnapshot) =>{
  return inject(ProjectService).getProject(route.paramMap.get('projectID'));
}