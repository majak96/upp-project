import { ShoppingItem } from './../model/shopping-cart';
import { ShoppingCartService } from './../services/shopping-cart.service';
import { Paper } from './../model/paper';
import { PaperService } from './../services/paper.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-paper-list',
  templateUrl: './paper-list.component.html',
  styleUrls: ['./paper-list.component.css']
})
export class PaperListComponent implements OnInit {

  issueId: number;
  paperList: Paper[] = [];

  constructor(private actrouter: ActivatedRoute,
              private paperService: PaperService,
              private shoppingCartService: ShoppingCartService) { }

  ngOnInit() {
    this.actrouter.paramMap.subscribe(
      params => {
        this.issueId = +params.get('id');
      });

    this.paperService.getIssuePapers(this.issueId).subscribe(
      data => {
        this.paperList = data;
      },
      error => {
        alert('An error occurred. Please try again.');
      }
    );
  }

  addToCart(paper: Paper) {
    const shoppingItem: ShoppingItem = {
      id: paper.id,
      name: paper.title,
      price: paper.price,
      type: 'paper'
    };

    this.shoppingCartService.addToShoppingCart(shoppingItem);
  }

}
