import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Issue } from '../model/issue';
import { IssueService } from '../services/issue.service';
import { ShoppingCartService } from '../services/shopping-cart.service';
import { ShoppingItem } from '../model/shopping-cart';

@Component({
  selector: 'app-issue-list',
  templateUrl: './issue-list.component.html',
  styleUrls: ['./issue-list.component.css']
})
export class IssueListComponent implements OnInit {

  magazineId: number;
  issueList: Issue[] = [];

  constructor(private actrouter: ActivatedRoute,
              private issueService: IssueService,
              private shoppingCartService: ShoppingCartService) { }

  ngOnInit() {
    this.actrouter.paramMap.subscribe(
      params => {
        this.magazineId = +params.get('id');
      });

    this.issueService.getMagazineIssues(this.magazineId).subscribe(
      data => {
        this.issueList = data;
      },
      error => {
        alert('An error occurred. Please try again.');
      }
    );
  }

  addToCart(issue: Issue) {
    const shoppingItem: ShoppingItem = {
      id: issue.id,
      name: 'Issue number ' +  issue.number,
      price: issue.price,
      type: 'issue'
    };

    this.shoppingCartService.addToShoppingCart(shoppingItem);
  }

}
