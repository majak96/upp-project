import { PaymentService } from './../services/payment.service';
import { MagazineService } from './../services/magazine.service';
import { Magazine } from './../model/magazine';
import { Component, OnInit } from '@angular/core';

@Component({
  templateUrl: './magazine-list.component.html',
  styleUrls: ['./magazine-list.component.css']
})
export class MagazineListComponent implements OnInit {

  magazines: Magazine[];

  constructor(private magazineService: MagazineService,
              private paymentService: PaymentService) { }

  ngOnInit() {

    this.getAllMagazines();
  }

  getAllMagazines() {
    this.magazineService.getAllMagazines().subscribe(
      data => {
        this.magazines = data;
      },
      error => {
        if (error.status === 403) {
          alert('You must be logged in to see available magazines!');
        } else {
          alert('An error occurred. Please try again!');
        }
      }
    );
  }

  subscribe(magazine: Magazine) {
    this.paymentService.subscribeMagazine(magazine.id).subscribe(
      data => {
        document.location.href = data.url;
      },
      error => {
        alert('An error occurred. Please try again.');
      }
    );
  }
}
