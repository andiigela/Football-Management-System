import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {ContractDto} from "../common/contract-dto";

@Injectable({
  providedIn: 'root'
})
export class ContractService {
  contractUrl: string = "http://localhost:8080/api/contracts"
  constructor(private http: HttpClient) { }
  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    })
    return headers;
  }
  public createContract(playerId: number,contractDto: ContractDto): Observable<any>{
    const headers = this.getHeaders()
    return this.http.post(`${this.contractUrl}/${playerId}/create`,contractDto,{headers})
  }

}
