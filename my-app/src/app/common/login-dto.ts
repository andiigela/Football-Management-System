export class LoginDto {
    private username: string;
    private password: string;

    constructor(username: string, password: string) {
        this.username = username;
        this.password = password;
    }


    getUsername(): string {
        return this.username;
    }

    getPassword(): string {
        return this.password;
    }
}
