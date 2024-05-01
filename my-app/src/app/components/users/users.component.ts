import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { HttpClient } from "@angular/common/http";
import { AuthService } from "../../services/auth.service";
import { UserDto } from "../../common/user-dto";

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
    users: UserDto[] = [];
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
        this.userService.getUsersWithUserRole().subscribe(
            (users: UserDto[]) => {
                this.users = users;
            },
            (error: any) => {
                console.error('Error loading users:', error);
            }
        );
    }

    updateUserStatus(userId: number, enabled: boolean): void {
        this.userService.updateUserStatus(userId, enabled).subscribe(
            () => {
                const userToUpdate = this.users.find(user => user.userId === userId);
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
                this.users = this.users.filter(user => user.userId !== userId);
            },
            (error: any) => {
                console.error('Error deleting user:', error);
            }
        );
    }

}
