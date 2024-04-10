import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ProjectEvent } from 'src/app/shared/models/events.model';
import { Project } from 'src/app/shared/models/project/project.model';
import { ProjectService } from 'src/app/shared/services/rest-services/projects.service';
import { ProjectEventPublisher } from 'src/app/shared/services/publisher-events-services/project.events.publisher';

@Component({
  selector: 'app-compo-project-manager',
  templateUrl: './compo-project-manager.component.html',
  styleUrls: ['./compo-project-manager.component.scss']
})
export class CompoProjectManagerComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute,
    private projectEventPublisher: ProjectEventPublisher,
    private projectService: ProjectService,
    private router: Router) { }

  projects!: Array<Project>;
  nbrOfProjects!: number;

  printNumberOfProjects($event: number) {
   console.log("nombre de projets------------ ", $event);
   this.nbrOfProjects = $event;
   }

  ngOnInit(): void {
    this.projectEventPublisher.projectEventObservable.subscribe((projectEvent: ProjectEvent) => {
      switch (projectEvent) {
        case ProjectEvent.GET_ALL_PROJECTS:
          this.activatedRoute.data.subscribe(data => {
            this.projects = data['getAllProjectsResolve']
            console.log(this.projects.length)
          })
          console.log(projectEvent);
          break;
        case ProjectEvent.REFRESH:
          this.projectService.getAllProjects().subscribe((data: Array<Project>) => {
            this.projects = data;
            console.log(projectEvent);
          });
          break;
      }
    })
  }

  onPrintProjects() {
    this.projectEventPublisher.publishProjectEvent(ProjectEvent.GET_ALL_PROJECTS);
  }
}
