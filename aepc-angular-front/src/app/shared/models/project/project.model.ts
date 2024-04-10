import { State } from './project.state';
import { Priority } from "./project.priority";

export class Project {
  projectId!: string;
  name!: string;
  description!: string;
  priority!: Priority;
  state!: State;
  createdDate!: Date;
  employeeId!: string;
  companyId!: string;
}
