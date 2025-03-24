import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-resident',
  templateUrl: './resident.component.html',
  styleUrls: ['./resident.component.css']
})

export class ResidentComponent {
  @Input() resident: any;
}
