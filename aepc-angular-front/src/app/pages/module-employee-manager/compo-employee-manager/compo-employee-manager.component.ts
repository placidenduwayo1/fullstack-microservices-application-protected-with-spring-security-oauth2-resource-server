import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { EmployeeEvent } from 'src/app/shared/models/events.model';
import { Employee } from 'src/app/shared/models/employee/employee.model';
import { EmployeeService } from 'src/app/shared/services/rest-services/employees.service';
import { test } from '@angular-devkit/core/src/virtual-fs/host';
import { EmployeeEventPublisher } from 'src/app/shared/services/publisher-events-services/employee.events.publisher';

@Component({
  selector: 'app-compo-employee-manager',
  templateUrl: './compo-employee-manager.component.html',
  styleUrls: ['./compo-employee-manager.component.scss'],
})
export class CompoEmployeeManagerComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private employeeEventPubliser: EmployeeEventPublisher,
    private employeeService: EmployeeService,
    private router : Router
  ) {}

  employeesList!: Array<Employee>;

  nbrEmployees!: number;
  printNbrEmployees($event: number){
    this.nbrEmployees = $event;
  }

  onPrintEmployees() {
    this.employeeEventPubliser.publishEmployeeEvent(
      EmployeeEvent.GET_ALL_EMPLOYEES
    );
  }

  ngOnInit(): void {

    this.employeeEventPubliser.employeeEnventObservable.subscribe(
      (employeeEvent: EmployeeEvent) => {

        switch (employeeEvent) {
          case EmployeeEvent.GET_ALL_EMPLOYEES:
            this.activatedRoute.data.subscribe((employees) => {
              this.employeesList = employees['getAllEmployeesResolve'];
            });
            console.log(employeeEvent);
            break;

          case EmployeeEvent.REFRESH:
            this.employeeService.getAllEmployees().subscribe((employees: Array<Employee>)=>{
              this.employeesList = employees;
            })
            console.log(employeeEvent);
            break;
        }
      }
    );
  }
}
