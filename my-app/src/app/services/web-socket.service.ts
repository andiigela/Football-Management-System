import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {Stomp, StompConfig, Message} from "@stomp/stompjs";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient:any;
  private messageSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');
  private serverUrl = 'http://localhost:8080/our-websocket';
  constructor() {
  }
  connect(){
    const headers = this.getHeaders();
    const socket = new SockJS(this.serverUrl);

    this.stompClient = Stomp.over(socket);
    var headers2 = {
      'Authorization': headers.get('Authorization')
    }
    this.stompClient.connect(headers2, () => {
      this.stompClient.subscribe('/topic/playerDeleted', (message: any) => {
        this.messageSubject.next(message.body);
      });
    });
  }
  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("-Disconnected-")
  }
  getMessages(): Observable<any> {
    return this.messageSubject.asObservable();
  }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
}
