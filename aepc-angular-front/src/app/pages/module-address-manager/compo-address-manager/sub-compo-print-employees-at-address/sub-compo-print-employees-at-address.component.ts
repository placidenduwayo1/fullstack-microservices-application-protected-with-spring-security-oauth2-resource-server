import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sub-compo-print-employees-at-address',
  templateUrl: './sub-compo-print-employees-at-address.component.html',
  styleUrls: ['./sub-compo-print-employees-at-address.component.scss']
})
export class SubCompoPrintEmployeesAtAddressComponent implements OnInit{
  employeesAtAddress!: any[];
  constructor(private activatedRoute: ActivatedRoute){}
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data=>{
      this.employeesAtAddress = data['getEmployeesAtAddressResolve'];
      console.log(this.employeesAtAddress)
    });
  }
}
