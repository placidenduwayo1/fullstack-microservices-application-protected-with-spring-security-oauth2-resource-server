import { Router } from '@angular/router';
import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { Project } from 'src/app/shared/models/project/project.model';
import { ProjectEvent } from 'src/app/shared/models/events/events.model';
import { ProjectService } from 'src/app/shared/services/rest-services/projects.service';
import { ProjectEventServicePublisher } from 'src/app/shared/services/publisher-events-services/project.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sub-compo-project-printer',
  templateUrl: './sub-compo-project-printer.component.html',
  styleUrls: ['./sub-compo-project-printer.component.scss']
})
export class SubCompoProjectPrinterComponent implements OnInit {

  private projectService: ProjectService = inject(ProjectService);
  private projectEventPublisher: ProjectEventServicePublisher = inject(ProjectEventServicePublisher);
  private router: Router = inject(Router);
  private confirmService: ConfirmationService = inject(ConfirmationService);
  private msgService: MessageService = inject(MessageService);

  @Input() projects!: Array<Project>;
  @Output() eventEmiter: EventEmitter<number> = new EventEmitter();


  ngOnInit(): void {
    this.projectEventPublisher.projectEventObservable.subscribe((event: ProjectEvent) => {
      switch (event) {
        case ProjectEvent.UPDATE_PROJECT_FORM:
          this.router.navigateByUrl('session/projects-management/project-update/' + this.projectId);
          console.log(event)
          break;
        case ProjectEvent.PROJECT_DELETED:
          this.confirmService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to delete the project??',
            accept: ()=>{
              this.projectService.deleteProject(this.projectId).subscribe(
                ()=>{
                  this.msgService.add({
                    key: 'deleted',
                    detail:'successfully deleted',
                    severity:'success',
                    sticky: true
                  });
                  return "deleted";
                }
              );
            },
            reject: ()=>{
              this.msgService.add({
                key: 'rejected',
                detail:'rejected',
                severity:'error',
                sticky: true
              });
              return null;
            }
          });
          break;
      }
    });
  }

  onPrintNumberOfProject(nbrOfProjects: number) {
    this.eventEmiter.emit(nbrOfProjects);
  }

  projectId!: string;
  onProjectUpdate(project: Project) {
    this.projectId = project.projectId;
    this.projectEventPublisher.publishProjectEvent(ProjectEvent.UPDATE_PROJECT_FORM);
  }
  onProjectDelete(projectId: string) {
    this.projectId = projectId;
    this.projectEventPublisher.publishProjectEvent(ProjectEvent.PROJECT_DELETED);
  }

}
