export class NotificationDto {
    id: number=0;
    permissionGiven: boolean=false;
    constructor(public description: string,public playerId: number) {
    }
}
