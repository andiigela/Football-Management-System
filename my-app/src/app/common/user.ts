export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    enabled: boolean;
    phone: string;
    country: string;
    birthDate: Date;
    profilePicture: string;
    address: string;
    city: string;
    postalCode: string;
    roleId: number;
    gender: string;
}
