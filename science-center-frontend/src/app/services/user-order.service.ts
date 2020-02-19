import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserOrder } from '../model/user-order';

@Injectable({
  providedIn: 'root'
})
export class UserOrderService {

  orderUrl = 'https://localhost:9997/orders/';

  constructor(private httpClient: HttpClient) { }

  public sendOrder(order: UserOrder) {
    return this.httpClient.post<any>(this.orderUrl + 'create', order);
  }

  public getAllPurchasedItems() {
    return this.httpClient.get<any>(this.orderUrl + 'user');
  }
}
