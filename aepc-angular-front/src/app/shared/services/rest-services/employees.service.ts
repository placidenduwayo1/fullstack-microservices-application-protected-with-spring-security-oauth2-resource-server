import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Employee } from '../../models/employee/employee.model';
import { myheaders } from './headers';

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private employeeServer: string = environment.gatewayService + '/api-employee';
  private httpclient: HttpClient = inject(HttpClient);

  getAllEmployees(): Observable<Array<Employee>> {
    return this.httpclient.get<Array<Employee>>(this.employeeServer + '/employees');
  }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.httpclient.post<Employee>(this.employeeServer + '/employees', employee, {
      headers: myheaders,
    });
  }

  deleteEmployee(employeeId: string): Observable<void> {
    return this.httpclient.delete<void>(this.employeeServer + '/employees/id/' + employeeId);
  }

  getEmployee(employeeId: string | any): Observable<Employee> {
    return this.httpclient.get<Employee>(this.employeeServer + '/employees/id/' + employeeId);
  }

  updateEmployee(employee: Employee): Observable<Employee> {
    return this.httpclient.put<Employee>(
      this.employeeServer + '/employees/id/' + employee.employeeId, employee, { headers: myheaders });
  }

  getProjectsAssignedToEmployee(employeeId: string | any): Observable<any[]> {
    return this.httpclient.get<Array<any>>(this.employeeServer + '/projects/employees/id/' + employeeId);
  }
}
