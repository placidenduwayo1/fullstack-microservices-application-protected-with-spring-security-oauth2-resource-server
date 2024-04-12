import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
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
  
    private activatedRoute = inject(ActivatedRoute);
    private employeeEventPubliser= inject(EmployeeEventPublisher);
    private employeeService = inject(EmployeeService);
  

  employeesList!: Array<Employee>;

  nbrEmployees!: number;
  printNbrEmployees($event: number) {
    this.nbrEmployees = $event;
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data =>{
      this.employeesList = data['getAllEmployeesResolve'];
    });

    this.employeeEventPubliser.employeeEnventObservable.subscribe((event: EmployeeEvent) => {
       if(event==EmployeeEvent.REFRESH){
        this.employeeService.getAllEmployees().subscribe((employees: Array<Employee>) => {
          this.employeesList = employees;
        });
      }
    });
  }
}
