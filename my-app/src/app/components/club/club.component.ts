import { Component, OnInit } from '@angular/core';
import { ClubDto } from '../../common/club-dto';
import { ClubService } from '../../services/club.service';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-club',
  templateUrl: './club.component.html',
  styleUrls: ['./club.component.css']
})
export class ClubComponent implements OnInit {
  club!: ClubDto;
  editMode: boolean = false;
  clubForm!: FormGroup;
  userId!: number | null;

  constructor(
    private clubService: ClubService,
    private authService: AuthService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.userId = this.authService.getUserIdFromToken();

    if (this.userId !== null) { // Check if userId is not null
      this.loadClubData(this.userId);
    } else {
      console.error('User ID not available.');
    }

    this.initializeForm();
  }

  initializeForm() {
    this.clubForm = this.fb.group({
      name: ['', Validators.required],
      foundedYear: ['', [Validators.required, Validators.min(1800), Validators.max(new Date().getFullYear())]],
      city: ['', Validators.required],
      website: ['', [Validators.required, Validators.pattern('https?://.+')]]
    });
  }

  loadClubData(userId: number) {
    this.clubService.getClubDataByUserId(userId).subscribe(
      (data) => {
        this.club = data;
        // Patch the form with club data
        this.clubForm.patchValue(this.club);
      },
      (error) => {
        console.error('Failed to load club data:', error);
      }
    );
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
    if (!this.editMode) {
      // If canceling edit mode, reset form data to original club data
      this.clubForm.patchValue(this.club);
    }
  }

  saveChanges() {
    if (this.userId !== null && this.clubForm.valid) { // Check if userId is not null and form is valid
      const clubId = this.club.id; // Assuming you have club id
      this.clubService.updateClub(clubId, this.clubForm.value).subscribe(
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
      console.error('User ID is null or form is invalid.');
    }
  }

  get formControls() {
    return this.clubForm.controls;
  }
}
