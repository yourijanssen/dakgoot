import { Component, OnInit } from '@angular/core';
import {HouseService} from "../../services/house-service/house.service";

@Component({
  selector: 'app-house-list',
  templateUrl: './house-list.component.html',
  styleUrls: ['./house-list.component.css']
})

export class HouseListComponent implements OnInit {
  houses: any[] = [];

  constructor(private houseService: HouseService) {}

  ngOnInit() {
  this.loadHouses()
  }

  loadHouses() {
    this.houseService.getHouses().subscribe(data => {
      this.houses = data;
    });
  }

  deleteHouse(id: number) {
    this.houseService.deleteHouse(id).subscribe(() => {
      this.loadHouses(); // Refresh the list after deletion
    });
  }
}
