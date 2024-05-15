import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {ContractDto} from "../common/contract-dto";
import {PageResponseDto} from "../common/page-response-dto";

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
  public retrieveContracts(playerId: number,pageNumber: number, pageSize: number): Observable<PageResponseDto>{
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto>(`${this.contractUrl}/${playerId}/?page=${pageNumber}&size=${pageSize}`,{headers});
  }

}
