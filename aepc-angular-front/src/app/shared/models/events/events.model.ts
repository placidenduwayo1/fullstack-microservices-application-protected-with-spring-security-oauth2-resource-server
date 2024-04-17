export enum EmployeeEvent {
  GET_ALL_EMPLOYEES = '[Employee] GET ALL EMPLOYEES EVENT',
  CREATE_EMPLOYEE_FORM = '[Employee] CREATE EMPLOYEE FORM EVENT',
  SAVE_EMPLOYEE_FORM_DATA = '[Employee] SAVE EMPLOYEE FORM DATA EVENT',
  EMPLOYEE_DELETED = '[Employee] EMPLOYEE DELETED EVENT',
  REFRESH = '[Employee] REFRESH LIST OF EMPLOYEES EVENT',
  DELETE_EMPLOYEE = '[Employee] DELETE EMPLOYEE EVENT',
  UPDATE_EMPLOYEE_FORM = '[Employee] UPDATE EMPLOYEE FORM EVENT',
  UPDATE_EMPLOYEE = '[Employee] UPDATE EMPLOYEE EVENT',
  PROJECTS_ASSIGNEDTO_EMPLOYEE = "[Project] PROJECTS ASSIGNEDTO EMPLOYEE"
}

export enum AddressEvent {
  GET_ALL_ADDRESSES = '[Address] GET ALL ADDRESSES EVENT',
  CREATE_ADDRESS_FORM = '[Address] CREATE ADDRESS FORM EVENT',
  SAVE_ADDRESS_FORM_DATA = '[Address] SAVE ADDRESS FORM DATA EVENT',
  GET_ADDRESS_BY_ADDRESS_INFOS = '[Address] GET ADDRESS BY ADDRESS INFOS EVENT',
  ADDRESS_DELETED = '[Address] ADDRESS DELETED EVENT',
  DELETE_ADDRESS = '[Address] DELETE ADDRESS EVENT',
  UPDATE_ADDRESS = '[Address] UPDATE ADDRESS EVENT',
  REFRESH = '[Address] REFRESH LIST OF ADDRESSES EVENT',
  EMPLOYEES_AT_ADDRESS = "[Employees] EMPLOYEES LIVING AT ADDRESS"
}

export enum CompanyEvent {
  GET_ALL_COMPANIES = '[Company] GET ALL COMPANIES EVENT',
  CREATE_COMPANY_FORM = '[Company] CREATE COMPANY FORM EVENT',
  UPDATE_COMPANY_FORM = '[Company]  UPDATE COMPANY FORM EVENT',
  UPDATE_COMPANY = '[Company]  UPDATE COMPANY EVENT',
  COMPANY_DELETED = '[Company] COMPANY DELETED EVENT"',
  REFRESH = '[Company] REFRESH LLIST OF COMPANIES EVENT',
  PROJECTS_ASSIGNEDTO_COMPANY = "[Project] PROJECTS ASSIGNED TO COMPANY",
  CREATE_COMPANY = "[Company] CREATE_COMPANY"
}

export enum ProjectEvent {
  GET_ALL_PROJECTS = '[Project] GET ALL PROJECTS EVENT',
  CREATE_PROJECT_FORM = '[Project] CREATE PROJECT FORM EVENT',
  UPDATE_PROJECT_FORM = '[Project]  UPDATE PROJECT FORM EVENT',
  UPDATE_PROJECT = '[Project]  UPDATE PROJECT EVENT',
  PROJECT_DELETED = '[Project] PROJECT DELETED EVENT"',
  REFRESH = '[Project] REFRESH LIST OF PROJECTS EVENT',
}

export enum UserEvent {
  LOGIN = "LOGIN EVENT",
  SAVE_USER = "[User] SAVE USER EVENT",
  DELETE_USER = "[User] DELETE USER",
  REFRESH = "REFRESH EVENT",
  MANAGE_USER = "MANAGE USER EVENT",
  ADD_ROLE_USER = "[User] ADD ROLE USER EVENT",
  REMOVE_ROLE_USER = "REMOVE_ROLE_USER EVENT",
  OPEN_PWD_CHANGE_UI = "[User] OPEN PWD CHANGE UI EVENT",
  CHANGE_PWD = "[User] CHANGE PWD EVENT",
}
