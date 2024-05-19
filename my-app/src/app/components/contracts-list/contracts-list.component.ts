import {Component, OnInit} from '@angular/core';
import {InjuryService} from "../../services/injury.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ContractService} from "../../services/contract.service";
import {ContractDto} from "../../common/contract-dto";

@Component({
  selector: 'app-contracts-list',
  templateUrl: './contracts-list.component.html',
  styleUrl: './contracts-list.component.css'
})
export class ContractsListComponent implements OnInit {
  playerContractsList: ContractDto[]=[];
  pageNumber=1;
  pageSize=10;
  totalElements: number = 0;
  currentPlayerId: number = 0;

  constructor(private contractService: ContractService,private router: Router,private activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params=>{
      this.currentPlayerId = +params['id']
      this.getContracts();
    })
  }
  public getContracts(){
    this.contractService.retrieveContracts(this.currentPlayerId,this.pageNumber-1,this.pageSize).subscribe((response)=>{
      this.playerContractsList = response.content;
      this.totalElements = response.totalElements;
    })
  }
  OnPageChange(pageNumber: number){
    this.pageNumber = pageNumber;
    this.getContracts();
  }
  public redirectToEditContract(contractId: number){
    this.router.navigate([`/players/${this.currentPlayerId}/contracts/${contractId}/edit`])
  }
  deleteContract(contractId: number){
    this.contractService.deleteContract(this.currentPlayerId,contractId).subscribe(()=>{
      this.playerContractsList = this.playerContractsList.filter((contract:ContractDto) => contract.id != contractId)
    });
  }
}
