import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {HouseService} from "../../services/house-service/house.service";


@Component({
  selector: 'app-house-detail',
  templateUrl: './house-detail.component.html',
  styleUrls: ['./house-detail.component.css']
})
export class HouseDetailComponent implements OnInit {
  house: any;

  constructor(
    private route: ActivatedRoute,
    private houseService: HouseService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.houseService.getHouse(id).subscribe(data => {
      this.house = data;
    });
  }
}
