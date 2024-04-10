import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sub-compo-print-projects-for-company',
  templateUrl: './sub-compo-print-projects-for-company.component.html',
  styleUrls: ['./sub-compo-print-projects-for-company.component.scss']
})
export class SubCompoPrintProjectsForCompanyComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }
  projects!: Array<any>

  ngOnInit(): void {
    this.route.data.subscribe(data=>{
      this.projects = data['getProjectsAssignedToCompanyResolve'];
    })
  }

}
