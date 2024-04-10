import { EmpRole } from './employee.role';
import { State } from './employee.state';

export class Employee {
  employeeId!: string;
  firstname!:string;
  lastname!: string;
  email!: string;
  hireDate!: Date;
  state!: State;
  role!: EmpRole;
  addressId!: string;
}
