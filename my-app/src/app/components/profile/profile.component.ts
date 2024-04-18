import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from "../../services/auth.service";
import {DatePipe} from "@angular/common";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    profileForm: FormGroup;
    userProfile: any;
    editMode: boolean = false;
    showMissingInfoMessage: boolean = false;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private formBuilder: FormBuilder,
        private authService: AuthService,
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
            gender: ['', Validators.required]
        });
    }
  genderOptions = [
    { label: 'MALE', value: 'MALE' },
    { label: 'FEMALE', value: 'FEMALE' }
  ];
    ngOnInit(): void {
        const userId: number | null = this.authService.getUserIdFromToken();

        if (userId !== null) {
            this.userService.getUserProfile(userId).subscribe(
                (data) => {
                    this.userProfile = data;
                    this.loadUserProfile();
                },
                (error) => {
                    console.log('Error fetching user profile:', error);
                }
            );
        } else {
            console.log('User ID is null');
        }
    }

  loadUserProfile() {
    if (this.userProfile) {
      // Format the birth date
      const formattedBirthDate = this.datePipe.transform(this.userProfile.birthDate, 'yyyy-MM-dd');

      // Populate the form with user profile data
      this.profileForm.patchValue({
        firstName: this.userProfile.firstName,
        lastName: this.userProfile.lastName,
        email: this.userProfile.email,
        phone: this.userProfile.phone,
        country: this.userProfile.country,
        birthDate: formattedBirthDate, // Use the formatted date here
        profile_picture: this.userProfile.profile_picture,
        address: this.userProfile.address,
        city: this.userProfile.city,
        postal_code: this.userProfile.postal_code,
        gender: this.userProfile.gender
      });

      // Check if any attribute is null in the user profile data
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
    toggleEditMode() {
        this.editMode = !this.editMode;
    }

    saveProfile() {
        if (this.profileForm.valid) {
            const userData = this.profileForm.value;
            const userId = this.userProfile.id;

            if (userId) {
                this.userService.updateUser(userId, userData).subscribe(
                    () => {
                        console.log('Profile updated successfully');
                        // Fetch updated user profile data after saving
                        this.userService.getUserProfile(userId).subscribe(
                            (data) => {
                                this.userProfile = data;
                                this.loadUserProfile();
                            },
                            (error) => {
                                console.log('Error fetching updated user profile:', error);
                            }
                        );
                        this.toggleEditMode(); // Exit edit mode after saving
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
