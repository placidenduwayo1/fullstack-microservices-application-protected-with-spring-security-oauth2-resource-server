import { Company } from './../../../../shared/models/company/company.model';
import { Employee } from './../../../../shared/models/employee/employee.model';
import { State } from './../../../../shared/models/project/project.state';
import { Priority } from './../../../../shared/models/project/project.priority';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, inject } from '@angular/core';
import { Project } from 'src/app/shared/models/project/project.model';
import { CompanyService } from 'src/app/shared/services/rest-services/companies.service';
import { EmployeeService } from 'src/app/shared/services/rest-services/employees.service';
import { ProjectService } from 'src/app/shared/services/rest-services/projects.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ProjectEventPublisher } from 'src/app/shared/services/publisher-events-services/project.events.publisher';
import { ProjectEvent } from 'src/app/shared/models/events.model';

@Component({
  selector: 'app-sub-compo-project-create',
  templateUrl: './sub-compo-project-create.component.html',
  styleUrls: ['./sub-compo-project-create.component.scss'],
})
export class SubCompoProjectCreateComponent implements OnInit {
  projectForm!: FormGroup;

  private fBuilder: FormBuilder = inject(FormBuilder);
  private employeeService: EmployeeService = inject(EmployeeService);
  private companyService: CompanyService = inject(CompanyService);
  private projectService: ProjectService = inject(ProjectService);
  private confirmService: ConfirmationService = inject(ConfirmationService);
  private msgService: MessageService = inject(MessageService);
  private projectEventsPublisher: ProjectEventPublisher = inject(ProjectEventPublisher);


  priorities = Priority;
  states = State;

  employees!: Array<Employee>;
  employeesMap: Map<string, string> = new Map();
  companies!: Array<Company>;
  companiesMap: Map<string, string> = new Map();

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

    this.projectForm = this.fBuilder.group({
      projectId: ['', Validators.required],
      name: ['', [Validators.required, Validators.minLength(4)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      priority: ['', Validators.required],
      state: ['', Validators.required],
      employeeId: ['', Validators.required],
      companyId: ['', Validators.required],
    });

    this.projectEventsPublisher.projectEventObservable.subscribe((event: ProjectEvent) => {
      if (event == ProjectEvent.CREATE_PROJECT_FORM) {
        console.log(event);
        this.confirmService.confirm({
          acceptLabel: 'Yes',
          rejectLabel: 'No',
          message: 'Do you realy want to create the project??',
          accept: () => {
            this.projectService.createProject(this.projectForm.value).subscribe((newProject: Project) => {
              this.msgService.add({
                key: 'added',
                detail: 'project successfully added',
                severity: 'success',
                sticky: true
              });
            })
          },
          reject: () => {
            this.msgService.add({
              key: 'rejected',
              detail: 'rejected',
              severity: 'error',
              sticky: true
            });
          }
        });
      }
    })
  }

  onSaveProjectForm() {
    this.projectEventsPublisher.publishProjectEvent(ProjectEvent.CREATE_PROJECT_FORM);
  }
}
