import { Router } from '@angular/router';
import { Employee } from '../../../../shared/models/employee/employee.model';
import { Component, EventEmitter, Input, OnInit, Output, inject } from '@angular/core';
import { EmployeeEvent } from 'src/app/shared/models/events.model';
import { EmployeeService } from 'src/app/shared/services/rest-services/employees.service';
import { EmployeeEventPublisher } from 'src/app/shared/services/publisher-events-services/employee.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sub-compo-employee-printer',
  templateUrl: './sub-compo-employee-printer.component.html',
  styleUrls: ['./sub-compo-employee-printer.component.scss']
})
export class SubCompoEmployeePrinterComponent implements OnInit {

  @Input() employeesList!: Array<Employee>;
  @Output() nbEmployeesEventEmitter: EventEmitter<number> = new EventEmitter();

  private employeeService: EmployeeService = inject(EmployeeService);
  private employeeEventPublisher: EmployeeEventPublisher = inject(EmployeeEventPublisher);
  private router: Router = inject(Router);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);



  onPrintNumberOfEmployeesEventEmitter(nbEmployees: number) {
    this.nbEmployeesEventEmitter.emit(nbEmployees);
  }

  ngOnInit(): void {
    this.employeeEventPublisher.employeeEnventObservable.subscribe((event: EmployeeEvent) => {

      switch (event) {

        case EmployeeEvent.UPDATE_EMPLOYEE_FORM:
          this.router.navigateByUrl('session/employees-management/employee-form-update/' + this.employeeId);
          console.log(event)
          break;
        case EmployeeEvent.PROJECTS_ASSIGNEDTO_EMPLOYEE:
          this.router.navigateByUrl('session/employees-management/projects-assignedto-employees/' + this.employeeIDProjectsRelated);
          console.log(event);
          break;
        case EmployeeEvent.EMPLOYEE_DELETED:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to delete this employee??',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.employeeService.deleteEmployee(this.employeeId).subscribe(() => {
                this.employeeEventPublisher.publishEmployeeEvent(EmployeeEvent.REFRESH);
                this.messageService.add({
                  key: 'deleted',
                  severity: 'success',
                  detail: 'succesfully deleted',
                  sticky: true
                });
                return "deleted";
              });
            },
            reject: () => {
              this.messageService.add({
                key: 'rejected',
                severity: 'error',
                detail: 'rejected',
                sticky: true
              });
              return null;
            }
          });
          break;
      }
    });
  }


  employeeId!: string;
  onEmployeeUpdate(employee: Employee) {
    this.employeeId = employee.employeeId;
    this.employeeEventPublisher.publishEmployeeEvent(EmployeeEvent.UPDATE_EMPLOYEE_FORM);
  }

  onEmployeeDelete(employeeId: string) {
    this.employeeId = employeeId;
    this.employeeEventPublisher.publishEmployeeEvent(EmployeeEvent.EMPLOYEE_DELETED);
  }

  employeeIDProjectsRelated!: string;
  onPrintProjectsAssigned(employeeId: string) {
    this.employeeIDProjectsRelated = employeeId;
    this.employeeEventPublisher.publishEmployeeEvent(EmployeeEvent.PROJECTS_ASSIGNEDTO_EMPLOYEE);
  }

}
