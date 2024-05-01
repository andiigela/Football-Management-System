import { Gender } from '../enums/gender';

export class UserDto {
    public userId = 0;
    public profile_picture: string = "";
    public enabled: boolean = true;
    constructor(
        public firstName: string,
        public lastName: string,
        public email: string,
        public phone: string,
        public country: string,
        public birthDate: Date,
        public address: string,
        public city: string,
        public postal_code: string,
        public gender: Gender
    ) {}
}

