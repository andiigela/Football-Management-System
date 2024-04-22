// profile.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from "../../services/auth.service";
import { DatePipe } from "@angular/common";
import { ClubDto } from '../../common/club-dto';
import {ClubService} from "../../services/club.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  providers: [DatePipe]
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  clubForm: FormGroup;
  userProfile: any;
  editMode: boolean = false;
  showMissingInfoMessage: boolean = false;
  userRole: string = '';
  clubData: ClubDto | null = null;


    constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private clubService: ClubService,
    private datePipe: DatePipe
  ) {
    this.profileForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      country: ['', Validators.required],
      birthDate: ['', Validators.required],
      profile_picture: [''],
      address: [''],
      city: [''],
      postal_code: [''],
      gender: ['', Validators.required],

    });
      this.clubForm = this.formBuilder.group({
          name: [''],
          foundedYear: [''],
          cityClub: [''],
          website: [''],
      });
  }

  genderOptions = [
    { label: 'MALE', value: 'MALE' },
    { label: 'FEMALE', value: 'FEMALE' }
  ];

  ngOnInit(): void {
        const userId: number | null = this.authService.getUserIdFromToken();

        if (userId !== null) {
            // Fetch user profile
            this.userService.getUserProfile(userId).subscribe(
                (data) => {
                    this.userProfile = data;
                    this.loadUserProfile();
                },
                (error) => {
                    console.log('Error fetching user profile:', error);
                }
            );

            // Fetch user role
            const userRole = this.authService.getRoleFromToken();
            this.userRole = userRole !== null ? userRole : '';

            // Fetch club data if user role is 'USER'
            if (this.userRole === 'USER') {
                this.clubService.getClubDataByUserId(userId).subscribe(
                    (clubData: any) => {
                        this.clubData = clubData;
                        this.loadClubProfile();
                    },
                    (error) => {
                        console.log('Error fetching club data:', error);
                    }
                );
            }
        } else {
            console.log('User ID is null');
        }
    }
    loadUserProfile() {
        if (this.userProfile) {
          const formattedBirthDate = this.datePipe.transform(this.userProfile.birthDate, 'yyyy-MM-dd');

          this.profileForm.patchValue({
            firstName: this.userProfile.firstName,
            lastName: this.userProfile.lastName,
            email: this.userProfile.email,
            phone: this.userProfile.phone,
            country: this.userProfile.country,
            birthDate: formattedBirthDate,
            profile_picture: this.userProfile.profile_picture,
            address: this.userProfile.address,
            city: this.userProfile.city,
            postal_code: this.userProfile.postal_code,
            gender: this.userProfile.gender
          });

          this.showMissingInfoMessage = !(
            this.userProfile.firstName &&
            this.userProfile.lastName &&
            this.userProfile.email &&
            this.userProfile.phone &&
            this.userProfile.country &&
            this.userProfile.birthDate &&
            this.userProfile.gender
          );
    } else {
      console.log('User profile is null');
    }
  }
    loadClubProfile() {
        if (this.userRole === 'USER') {
            const userId = this.userProfile.id;
            if (userId) {
                this.clubService.getClubIdByUserId(userId).subscribe(
                    (clubId: number) => {
                        if (clubId) {
                            this.clubService.getClubById(clubId).subscribe(
                                (clubData: any) => {
                                    this.clubData = clubData;
                                },
                                (error) => {
                                    console.log('Error fetching club data:', error);
                                }
                            );
                        } else {
                            console.log('Club ID is null');
                        }
                    },
                    (error) => {
                        console.log('Error fetching club ID:', error);
                    }
                );
            } else {
                console.log('User ID is null');
            }
        }
    }

    toggleEditMode() {
        this.editMode = !this.editMode;
    }

    saveProfile() {
        if (this.profileForm.valid) {
            const userData = this.profileForm.value;
            const userId = this.userProfile.id;

            if (userId) {
                // Update the user profile
                this.userService.updateUser(userId, userData).subscribe(
                    () => {
                        console.log('User profile updated successfully');
                        // Fetch the updated user profile data
                        this.userService.getUserProfile(userId).subscribe(
                            (data) => {
                                this.userProfile = data;
                                this.loadUserProfile();
                            },
                            (error) => {
                                console.log('Error fetching updated user profile:', error);
                            }
                        );

                        // Update club data if user is associated with a club
                        if (this.userRole === 'USER') {
                            // Fetch the clubId associated with the userId
                            this.clubService.getClubIdByUserId(userId).subscribe(
                                (clubId: number) => {
                                    console.log('Fetched clubId:', clubId);
                                    if (clubId) {
                                        const clubData = this.clubForm.value;

                                        this.clubService.updateClub(clubId, clubData).subscribe(
                                            () => {
                                                console.log('Club data updated successfully');
                                                this.loadClubProfile(); // Reload club profile after update
                                            },
                                            (error) => {
                                                console.log('Error updating club data:', error);
                                            }
                                        );
                                    } else {
                                        console.log('Club ID is null');
                                    }
                                },
                                (error) => {
                                    console.log('Error fetching club ID:', error);
                                }
                            );
                        }

                        this.toggleEditMode();
                    },
                    (error) => {
                        console.log('Error updating user profile:', error);
                    }
                );
            } else {
                console.log('User ID is null');
            }
        } else {
            console.log('Form is invalid. Please fill all required fields.');
        }
    }

}
