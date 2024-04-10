import { Type } from "./company.type";

export class Company {
  companyId!: string;
  name!: string;
  agency!: string;
  type!: Type;
  connectedDate!: Date;
  addressId!: string
}
