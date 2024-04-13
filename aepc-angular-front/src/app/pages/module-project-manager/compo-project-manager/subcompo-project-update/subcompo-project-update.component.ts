import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';
import { Project } from './../../../../shared/models/project/project.model';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Company } from 'src/app/shared/models/company/company.model';
import { Employee } from 'src/app/shared/models/employee/employee.model';
import { Priority } from 'src/app/shared/models/project/project.priority';
import { State } from 'src/app/shared/models/project/project.state';
import { EmployeeService } from 'src/app/shared/services/rest-services/employees.service';
import { ProjectService } from 'src/app/shared/services/rest-services/projects.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ProjectEventServicePublisher } from 'src/app/shared/services/publisher-events-services/project.events.publisher';
import { ProjectEvent } from 'src/app/shared/models/events/events.model';


@Component({
  selector: 'app-subcompo-project-update',
  templateUrl: './subcompo-project-update.component.html',
  styleUrls: ['./subcompo-project-update.component.scss']
})
export class SubcompoProjectUpdateComponent implements OnInit {

  projectForm!: FormGroup;

  private fBuilder: FormBuilder = inject(FormBuilder);
  private employeeService: EmployeeService = inject(EmployeeService);
  private companyService: CompanyService = inject(CompanyService);
  private activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private projectService: ProjectService = inject(ProjectService);
  private confirmService: ConfirmationService = inject(ConfirmationService);
  private msgService: MessageService = inject(MessageService);
  private projectEventPub: ProjectEventServicePublisher = inject(ProjectEventServicePublisher);
  priorities = Priority;
  states = State;

  employees!: Array<Employee>;
  employeesMap: Map<string, string> = new Map();
  companies!: Array<Company>
  companiesMap: Map<string, string> = new Map();
  project!: Project;

  ngOnInit(): void {
    this.employeeService
      .getAllEmployees()
      .subscribe((data: Array<Employee>) => {
        this.employees = data;
        this.employees.forEach((employee: Employee) => {
          this.employeesMap.set(
            employee.employeeId,
            employee.firstname +
            ' ' +
            employee.lastname +
            ' email: ' +
            employee.email
          );
        });
      });

    this.companyService.getAllCompanies().subscribe((data: Array<Company>) => {
      this.companies = data;
      this.companies.forEach((company: Company) => {
        this.companiesMap.set(
          company.companyId,
          company.name +
          ' ' +
          company.agency
        );
      });
    });

    this.activatedRoute.data.subscribe(data => {
      let project: Project = data['getProjectByIDResolve'];
      console.log(project);
      this.projectForm = this.fBuilder.group({
        projectId: [project.projectId, Validators.required],
        name: [project.name, [Validators.required, Validators.minLength(4)]],
        description: [project.description, [Validators.required, Validators.minLength(10)]],
        priority: [project.priority, Validators.required],
        state: [project.state, Validators.required],
        employeeId: [project.employeeId, Validators.required],
        companyId: [project.companyId, Validators.required],
      });

    });

    this.projectEventPub.projectEventObservable.subscribe((event: ProjectEvent)=>{
      if(event==ProjectEvent.UPDATE_PROJECT) {
        console.log(event);
        this.confirmService.confirm({
          acceptLabel: 'Yes',
          rejectLabel: 'No',
          message: 'Do you realy want to update the project??',
          accept: ()=>{
            this.projectService.updateProject(this.projectForm.value).subscribe(
              (updated: Project)=>{
                this.msgService.add({
                  key: 'updated',
                  detail:'project successfully updated',
                  severity:'success',
                  sticky: true
                });
                return updated;
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
      }
    })
  }

  onSaveProjectForm() {
    this.projectEventPub.publishProjectEvent(ProjectEvent.UPDATE_PROJECT);
  }
}
