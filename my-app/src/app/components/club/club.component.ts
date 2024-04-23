import { Component, OnInit } from '@angular/core';
import { ClubDto } from '../../common/club-dto';
import { ClubService } from '../../services/club.service';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-club',
  templateUrl: './club.component.html',
  styleUrls: ['./club.component.css']
})
export class ClubComponent implements OnInit {
  club!: ClubDto;
  editMode: boolean = false;
  editedClubData!: Partial<ClubDto>;
  userId!: number | null;

  constructor(
      private clubService: ClubService,
      private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.userId = this.authService.getUserIdFromToken();

    if (this.userId !== null) { // Check if userId is not null
      this.loadClubData(this.userId);
    } else {
      console.error('User ID not available.');
    }
  }

  loadClubData(userId: number) {
    this.clubService.getClubDataByUserId(userId).subscribe(
        (data) => {
          this.club = data;
          // Initialize editedClubData with a copy of club data
          this.editedClubData = { ...this.club };
        },
        (error) => {
          console.error('Failed to load club data:', error);
        }
    );
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
    if (!this.editMode) {
      // If canceling edit mode, reset editedClubData
      this.editedClubData = { ...this.club };
    }
  }

  saveChanges() {
    if (this.userId !== null && this.club) { // Check if userId and club are not null
      const clubId = this.club.id; // Assuming you have club id
      this.clubService.updateClub(clubId, this.editedClubData).subscribe(
          () => {
            console.log('Club updated successfully.');
            this.editMode = false;
            this.loadClubData(this.userId!);
          },
          (error) => {
            console.error('Failed to update club:', error);
          }
      );
    } else {
      console.error('User ID or club data is null.');
    }
  }

}
