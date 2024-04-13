import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
import { ProjectEvent } from 'src/app/shared/models/events/events.model';
import { Project } from 'src/app/shared/models/project/project.model';
import { ProjectService } from 'src/app/shared/services/rest-services/projects.service';
import { ProjectEventServicePublisher } from 'src/app/shared/services/publisher-events-services/project.events.publisher';

@Component({
  selector: 'app-compo-project-manager',
  templateUrl: './compo-project-manager.component.html',
  styleUrls: ['./compo-project-manager.component.scss']
})
export class CompoProjectManagerComponent implements OnInit {

  private activatedRoute = inject(ActivatedRoute);
  private projectEventPublisher = inject(ProjectEventServicePublisher);
  private projectService = inject(ProjectService);

  projects!: Array<Project>;
  nbrOfProjects!: number;

  printNumberOfProjects($event: number) {
    this.nbrOfProjects = $event;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.projects = data['getAllProjectsResolve']
    });

    this.projectEventPublisher.projectEventObservable.subscribe((event: ProjectEvent) => {
      if (event == ProjectEvent.REFRESH) {
        this.projectService.getAllProjects().subscribe((data: Array<Project>) => {
          this.projects = data;
        });
      }
    });
  }
}
