export class UserDTO {
  dbId: number;
  firstName: string;
  lastName: string;
  email: string;
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

  constructor(
    dbId: number,
    firstName: string,
    lastName: string,
    email: string,
    enabled: boolean,
    phone: string,
    country: string,
    birthDate: Date,
    profile_picture: string,
    address: string,
    city: string,
    postal_code: string,
    roleId: number,
    gender: string,
    isDeleted: boolean
  ) {
    this.dbId = dbId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.enabled = enabled;
    this.phone = phone;
    this.country = country;
    this.birthDate = birthDate;
    this.profile_picture = profile_picture;
    this.address = address;
    this.city = city;
    this.postal_code = postal_code;
    this.roleId = roleId;
    this.gender = gender;
    this.isDeleted = isDeleted;
  }
}
