import { AddressService } from '../../../../shared/services/rest-services/addresses.service';
import { Address } from '../../../../shared/models/address/address.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit, inject } from '@angular/core';
import { EmployeeEvent } from 'src/app/shared/models/events/events.model';
import { Employee } from 'src/app/shared/models/employee/employee.model';
import { State } from 'src/app/shared/models/employee/employee.state';
import { EmpRole } from 'src/app/shared/models/employee/employee.role';
import { EmployeeService } from 'src/app/shared/services/rest-services/employees.service';
import { EmployeeEventServicePublisher } from 'src/app/shared/services/publisher-events-services/employee.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sub-compo-employee-update',
  templateUrl: './sub-compo-employee-update.component.html',
  styleUrls: ['./sub-compo-employee-update.component.scss'],
})
export class SubCompoEmployeeUpdateComponent implements OnInit {

  private activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private employeeService: EmployeeService = inject(EmployeeService);
  private fbuilder: FormBuilder = inject(FormBuilder);
  private addressService: AddressService = inject(AddressService);
  private fb: FormBuilder = inject(FormBuilder);
  private employEventPublisher: EmployeeEventServicePublisher = inject(EmployeeEventServicePublisher);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);
  
  employeeForm!: FormGroup;
  roles = EmpRole;
  states = State;
  addresses!: Array<Address>;
  addressesMap: Map<string, string> = new Map();

  ngOnInit(): void {
    this.addressService.getAllAddresses().subscribe((data: Array<Address>) => {
      this.addresses = data;
      this.addresses.forEach((address: Address) => {
        this.addressesMap.set(
          address.addressId,
          address.num +
          ' ' +
          address.street +
          ', ' +
          address.pb +
          ' ' +
          address.city +
          ', ' +
          address.country
        );
      });
    });

    this.activatedRoute
      .data.subscribe((data) => {
        let employee: Employee = data['getEmployeeByIDResolve'];
        console.log(employee);
        this.employeeForm = this.fbuilder.group({
          employeeId: [employee.employeeId],
          firstname: [employee.firstname, Validators.required],
          lastname: [employee.lastname, Validators.required],
          state: [employee.state, Validators.required],
          role: [employee.role, Validators.required],
          addressId: [employee.addressId, Validators.required],
        });
      });

      this.employEventPublisher.employeeEnventObservable.subscribe((event:EmployeeEvent)=>{
        if(event==EmployeeEvent.UPDATE_EMPLOYEE){
          this.confirmationService.confirm({
            acceptLabel:'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to update this employee??',

            accept: ()=>{
              this.employeeService.updateEmployee(this.employeeForm.value).subscribe((employee: Employee)=>{
                this.messageService.add({
                  key: 'updated',
                  severity: 'success',
                  detail: 'succesfully added',
                  sticky: true
                });
                return employee;
              })
            }, 
            reject: ()=>{
              this.messageService.add({
                key:'rejected',
                severity: 'error',
                detail:'rejected',
                sticky: true
              });
              return null;
            }
          });
        }
      })
  }

  onSaveEmployeeFormData() {
    this.employEventPublisher.publishEmployeeEvent(EmployeeEvent.UPDATE_EMPLOYEE);
  }
}
