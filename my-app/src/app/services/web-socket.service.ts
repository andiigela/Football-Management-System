import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import {BehaviorSubject, Observable, Subject} from 'rxjs';
import {Stomp, StompConfig, Message} from "@stomp/stompjs";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: {[key: string]: any} = {};
  private messageSubject: { [key: string]: BehaviorSubject<string> } = {};
  private serverUrl = 'http://localhost:8080/our-websocket';
  constructor() {
  }
  connect(topicUrl: string,connectionId: string){
    const headers = this.getHeaders();
    const socket = new SockJS(this.serverUrl);

    this.stompClient[connectionId] = Stomp.over(socket);
    var headers2 = {
      'Authorization': headers.get('Authorization')
    }
    this.messageSubject[connectionId] = new BehaviorSubject<string>('');
    this.stompClient[connectionId].connect(headers2, () => {
      this.stompClient[connectionId].subscribe(topicUrl, (message: any) => {
        this.messageSubject[connectionId].next(message.body);
      });
    });
  }
  disconnect(connectionId: string) {
    if (this.stompClient[connectionId] !== null) {
      this.stompClient[connectionId].disconnect();
      delete this.stompClient[connectionId];
      delete this.messageSubject[connectionId];
    }
    console.log("-Disconnected-")
  }
  getMessages(connectionId: string): Observable<any> {
    return this.messageSubject[connectionId].asObservable();
  }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
}
