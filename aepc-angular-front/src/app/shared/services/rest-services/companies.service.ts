import { environment } from 'src/environments/environment';
import { Observable, throwError, catchError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Company } from '../../models/company/company.model';
import { myheaders } from './headers';

@Injectable({ providedIn: 'root' })
export class CompanyService {

  private companyServer: string = environment.gatewayService + '/api-company';
  private httpClient: HttpClient = inject(HttpClient);

  getAllCompanies(): Observable<Array<Company>> {
    return this.httpClient.get<Array<Company>>(this.companyServer + '/companies');
  }

  createCompany(company: Company): Observable<Company> {
    return this.httpClient.post<Company>(this.companyServer + '/companies', company, { headers: myheaders });
  }

  getCompany(companyId: string | any): Observable<Company> {
    return this.httpClient.get<Company>(this.companyServer + '/companies/id/' + companyId);
  }

  updateCompany(company: Company): Observable<Company> {
    return this.httpClient.put<Company>(this.companyServer + '/companies/id/' + company.companyId, company, { headers: myheaders });
  }

  deleteCompany(companyId: string): Observable<void> {
    return this.httpClient.delete<void>(this.companyServer + '/companies/id/' + companyId);
  }

  getProjectsAssignedToCompany(companyId: string | any): Observable<Array<any>> {
    return this.httpClient.get<Array<any>>(this.companyServer + '/projects/companies/id/' + companyId);
  }

  getCompaniesOnAddressCity(city: string): Observable<Array<Company>> {
    return this.httpClient.get<Array<Company>>(this.companyServer + '/companies/addresses/city/' + city);
  }
}
