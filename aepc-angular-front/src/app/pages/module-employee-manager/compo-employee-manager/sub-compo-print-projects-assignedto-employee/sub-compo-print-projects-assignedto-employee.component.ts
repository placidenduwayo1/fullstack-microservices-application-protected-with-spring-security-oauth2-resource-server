import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sub-compo-print-projects-assignedto-employee',
  templateUrl: './sub-compo-print-projects-assignedto-employee.component.html',
  styleUrls: ['./sub-compo-print-projects-assignedto-employee.component.scss']
})
export class SubCompoPrintProjectsAssignedtoEmployeeComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  projects!: Array<any>;

  ngOnInit(): void {
    this.route.data.subscribe(data=>{
      this.projects = data['getProjectsAssignedToEmployeeResolve'];
      console.log(this.projects);
    })
  }

}
