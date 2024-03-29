import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  users: any[] = [];
  static refreshToken = "";
  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.loadUsers();
  }
  loadUsers(): void{
    this.http.get("http://localhost:8080/api/users").subscribe((res:any)=>{
      this.users = res;
    })
  }
}
