export class LeagueDto {
  constructor(public dbId: number,public  name: string,public startDate: Date,public endDate: Date,public  description: string) {
    this.dbId = dbId;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
  }

}
