import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-users-printer',
  templateUrl: './users-printer.component.html',
  styleUrls: ['./users-printer.component.scss']
})
export class UsersPrinterComponent {

  @Input() users!: Array<any>;
}
