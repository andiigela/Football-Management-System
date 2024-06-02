export class LeagueDto {
  constructor(public id: number,public  name: string, public founded:number,public  description: string, public picture :string ) {
    this.id = id;
    this.name = name;
    this.founded=founded;
    this.description = description;
    this.picture=picture;
  }

}
