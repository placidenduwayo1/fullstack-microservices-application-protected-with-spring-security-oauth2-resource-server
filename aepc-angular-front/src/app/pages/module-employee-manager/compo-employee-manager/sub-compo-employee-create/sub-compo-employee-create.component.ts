import { AddressService } from '../../../../shared/services/rest-services/addresses.service';
import { Employee } from '../../../../shared/models/employee/employee.model';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmployeeEvent } from 'src/app/shared/models/events.model';
import { Address } from 'src/app/shared/models/address/address.model';
import { State } from 'src/app/shared/models/employee/employee.state';
import { EmpRole } from 'src/app/shared/models/employee/employee.role';
import { EmployeeService } from 'src/app/shared/services/rest-services/employees.service';
import { EmployeeEventPublisher } from 'src/app/shared/services/publisher-events-services/employee.events.publisher';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-sub-compo-employee-create',
  templateUrl: './sub-compo-employee-create.component.html',
  styleUrls: ['./sub-compo-employee-create.component.scss'],
})
export class SubCompoEmployeeCreateComponent implements OnInit {
  employeeForm!: FormGroup;
  private fb: FormBuilder = inject(FormBuilder);
  private employEventPublisher: EmployeeEventPublisher = inject(EmployeeEventPublisher);
  private employeeService: EmployeeService = inject(EmployeeService);
  private addressService: AddressService = inject(AddressService);
  private confirmationService: ConfirmationService = inject(ConfirmationService);
  private messageService: MessageService = inject(MessageService);



  states = State;
  roles = EmpRole;

  addresses!: Address[];
  addressesMap: Map<string, string> = new Map();

  ngOnInit(): void {
    this.addressService.getAllAddresses().subscribe(data => {
      this.addresses = data;
      this.addresses.forEach(address => {
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
      })
    });
    this.employeeForm = this.fb.group({
      employeeID: [0, Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      state: [, Validators.required],
      role: [, Validators.required],
      addressId: [, Validators.required],
    });

    this.employEventPublisher.employeeEnventObservable.subscribe((event: EmployeeEvent) => {
      switch (event) {
        case EmployeeEvent.SAVE_EMPLOYEE_FORM_DATA:
          this.confirmationService.confirm({
            acceptLabel: 'Yes',
            rejectLabel: 'No',
            message: 'Do you realy want to add this employee??',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
              this.employeeService.createEmployee(this.employeeForm.value).subscribe((newEmployee: Employee) => {
                console.log(newEmployee);
                this.messageService.add({
                  key: 'added',
                  severity: 'success',
                  detail: 'succesfully added',
                  sticky: true
                });
                return newEmployee;
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
    })
  }

  onSaveEmployeeFormData() {
    this.employEventPublisher.publishEmployeeEvent(EmployeeEvent.SAVE_EMPLOYEE_FORM_DATA);
  }
}
