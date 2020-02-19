import { Component, OnInit } from '@angular/core';
import { ShoppingItem } from '../model/shopping-cart';
import { ShoppingCartService } from '../services/shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  items: ShoppingItem[];

  constructor(private shoppingCart: ShoppingCartService) { }

  ngOnInit() {
    this.items = this.shoppingCart.getShoppingItemsList();
    console.log(this.items);
  }

  removeItem(id: number) {
    const success = this.shoppingCart.removeFromShoppingCart(id);

    if (success) {
      this.items = this.items.filter(item => item.id !== id );
    }
  }

}
