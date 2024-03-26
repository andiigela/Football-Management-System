export class RefreshTokenRequestDto {
  constructor(private refreshToken: string) {
  }
  public getRefreshToken(): string {
    return this.refreshToken;
  }

}
