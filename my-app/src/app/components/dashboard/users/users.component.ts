import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import { HttpClient } from "@angular/common/http";
import { AuthService } from "../../../services/auth.service";
import { User } from "../../../common/user";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
    users: User[] = [];
    currentUserId: number | null = null;

    constructor(private http: HttpClient, private userService: UserService, private authService: AuthService) { }

    ngOnInit(): void {
        this.getCurrentUserId();
        this.loadUsers();
    }

    getCurrentUserId(): void {
        this.currentUserId = this.authService.getUserIdFromToken();
    }

    loadUsers(): void {
        if (this.currentUserId !== null) {
            this.userService.getUsers(this.currentUserId).subscribe(
                (users: User[]) => {
                    // Filter out users with isDeleted=true
                    this.users = users.filter(user => !user.isDeleted);
                },
                (error: any) => {
                    console.error('Error loading users:', error);
                }
            );
        } else {
            console.log("User ID not available in token.");
        }
    }

    updateUserStatus(userId: number, enabled: boolean): void {
        this.userService.updateUserStatus(userId, enabled).subscribe(
            () => {
                const userToUpdate = this.users.find(user => user.id === userId);
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
                this.users = this.users.filter(user => user.id !== userId);
            },
            (error: any) => {
                console.error('Error deleting user:', error);
            }
        );
    }

}
