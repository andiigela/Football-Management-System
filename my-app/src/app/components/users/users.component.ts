import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { HttpClient } from "@angular/common/http";
import { AuthService } from "../../services/auth.service";
import { User } from "../../common/user";
import {UserDTO} from "../../common/user-dto";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
    users: UserDTO[] = [];
    currentUserId: number | null = null;
    pageNumber: number = 1;
    pageSize: number = 5;
    totalElements: number = 0;
    searchQuery: string = '';
    constructor(private http: HttpClient, private userService: UserService, private authService: AuthService) { }

    ngOnInit(): void {
        this.getCurrentUserId();
        this.loadUsers();
    }

    getCurrentUserId(): void {
        this.currentUserId = this.authService.getUserIdFromToken();
    }
  searchUsers(): void {
    this.fetchUsers(this.searchQuery);
  }

    private fetchUsers(query: string): void {
        if (query.trim() !== '') {
            this.userService.searchUsersByEmail(query)
                .subscribe(
                    (users: UserDTO[]) => {
                        this.users = users;
                        console.log(users);
                    },
                    (error) => {
                        console.error('Error fetching users:', error);
                    }
                );
        } else {
            this.loadUsers();
        }
    }
    loadUsers(): void {
        this.userService.getUsersWithUserRole(this.pageNumber-1, this.pageSize).subscribe(
            (response) => {
              this.users = response.content;
              this.totalElements = response.totalElements;
            },
            (error: any) => {
                console.error('Error loading users:', error);
            }
        );
    }
  onPageChange(pageNumber: number): void {
    console.log('Page change to:', pageNumber);
    this.pageNumber = pageNumber;
    this.loadUsers();

  }
    updateUserStatus(userId: number, enabled: boolean): void {
        this.userService.updateUserStatus(userId, enabled).subscribe(
            () => {
                const userToUpdate = this.users.find(user => user.dbId === userId);
                if (userToUpdate) {
                    userToUpdate.enabled = enabled;
                }
            },
            (error: any) => {
                console.error('Error updating user status:', error);
            }
        );
    }

    deleteUser(userId: number): void {
        this.userService.deleteUser(userId).subscribe(
            () => {
                // Remove the deleted user from the users array
                this.users = this.users.filter(user => user.dbId !== userId);
            },
            (error: any) => {
                console.error('Error deleting user:', error);
            }
        );
    }

}
