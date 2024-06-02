import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {TransferDto} from "../common/transfer-dto";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TransferService {
  private transferUrl = ' http://localhost:8080/api/v1/transfers';



  constructor(private http :HttpClient) { }

  listOfTransfers():Observable<TransferDto[]>{
    return this.http.get<TransferDto[]>(this.transferUrl)
  }
  createTransfer(transferDto: TransferDto): Observable<any> {
    return this.http.post<TransferDto>(this.transferUrl, transferDto);
  }
  editTransfer(id: number, transferDTO: TransferDto): Observable<any> {
    return this.http.put<any>(`${this.transferUrl}/${id}`, transferDTO);
  }

}
