export class LeagueDto {
  constructor(public id: number,public  name: string,public start_date: Date,public  end_date: Date,public  description: string) {
    this.id = id;
    this.name = name;
    this.start_date = start_date;
    this.end_date = end_date;
    this.description = description;
  }

}
