import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { DatePipe } from '@angular/common';
import { ClubDto } from '../../common/club-dto';
import { ClubService } from '../../services/club.service';
import { Gender } from '../../enums/gender';
import {User} from "../../common/user-dto";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css'],
    providers: [DatePipe],
})
export class ProfileComponent implements OnInit {
    profileForm: FormGroup;
    userProfile!: User;
    editMode: boolean = false;
    showMissingInfoMessage: boolean = false;
    userRole: string = '';
    file: File | null = null;

    // Define gender options using the enum
    genderOptions = [
        { label: Gender.MALE, value: Gender.MALE },
        { label: Gender.FEMALE, value: Gender.FEMALE },
    ];

    constructor(
        private userService: UserService,
        private router: Router,
        private formBuilder: FormBuilder,
        public authService: AuthService,
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
    }

    ngOnInit(): void {
        const userId: number | null = this.authService.getUserIdFromToken();

        if (userId !== null) {
            // Fetch user profile
            this.userService.getUserProfile(userId).subscribe(
                (data) => {
                    this.getEditProfileImageUrl(data.profile_picture);
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
        } else {
            console.log('User ID is null');
        }
    }

    loadUserProfile() {
        if (this.userProfile) {
            const formattedBirthDate = this.datePipe.transform(
                this.userProfile.birthDate,
                'yyyy-MM-dd'
            );

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
                gender: this.userProfile.gender,
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

    toggleEditMode() {
        this.editMode = !this.editMode;
    }

    saveProfile() {
        if (this.profileForm.valid) {
            const formValue = this.profileForm.value;
            let userData = {
                firstName: formValue.firstName,
                lastName: formValue.lastName,
                email: formValue.email,
                phone: formValue.phone,
                country: formValue.country,
                birthDate: formValue.birthDate,
                profile_picture: formValue.profile_picture,
                address: formValue.address,
                city: formValue.city,
                postal_code: formValue.postal_code,
                gender: formValue.gender
            };

            this.userService.updateUser(userData as User, this.file).subscribe({
                next: (data) => {
                    // Handle response if needed
                    console.log('User profile updated successfully');
                    console.log(data);
                },
                error: (error) => {
                    console.log('Error updating user profile:', error);
                }
            });
        } else {
            console.log('Form is invalid. Please fill all required fields.');
        }
    }

    getEditProfileImageUrl(profile_picture: string) {
        this.userService.getImageUrl(profile_picture).subscribe((blob: Blob) => {
            const profile_picture = URL.createObjectURL(blob);
            this.userProfile.profile_picture = profile_picture;
        });
    }

    onFileSelected(event: any) {
        const file: File = event.target.files[0];
        this.file = file;
        console.log('hey:' + file);
    }
}
