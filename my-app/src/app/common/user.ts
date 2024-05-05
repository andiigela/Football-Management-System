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
  profile_picture: string;
  address: string;
  city: string;
  postal_code: string;
  roleId: number;
  gender: string;
  isDeleted: boolean;
}
