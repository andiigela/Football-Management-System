export class LeagueDto {
  constructor(public dbId: number,public  name: string,public start_date: Date,public  end_date: Date,public  description: string) {
    this.dbId = dbId;
    this.name = name;
    this.start_date = start_date;
    this.end_date = end_date;
    this.description = description;
  }

}
