import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Project } from '../../models/project/project.model';
import { myheaders } from './headers';

@Injectable({ providedIn: 'root' })
export class ProjectService {

  dbServer: string = environment.gatewayService + '/api-project';
  private httpClient: HttpClient = inject(HttpClient);

  getAllProjects(): Observable<Array<Project>> {
    return this.httpClient.get<Array<Project>>(this.dbServer + '/projects');
  }

  createProject(project: Project): Observable<Project> {
    return this.httpClient.post<Project>(this.dbServer + '/projects', project, { headers: myheaders });
  }

  deleteProject(projectId: string): Observable<void> {
    return this.httpClient.delete<void>(this.dbServer + '/projects/' + projectId);
  }

  getProject(projectId: string | any): Observable<Project> {
    return this.httpClient.get<Project>(this.dbServer + '/projects/' + projectId);
  }

  updateProject(project: Project): Observable<Project> {
    return this.httpClient.put<Project>(this.dbServer + '/projects/' + project.projectId, project, { headers: myheaders });
  }
}
