import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {LeagueDto} from "../common/league-dto";
import {SeasonDto} from "../common/season-dto";
import {PageResponseDto} from "../common/page-response-dto";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class LeagueService {
  private leagueUrl = `${environment.api.baseUrl + environment.api.leagueUrl}`
  constructor(private http:HttpClient) { }

  private getHeaders(): HttpHeaders {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    });
    return headers;
  }
  returnAllLeagues(pageNumber: number, pageSize: number):Observable<PageResponseDto<LeagueDto>> {
    console.log(this.leagueUrl)
    return this.http.get<PageResponseDto<LeagueDto>>(`${this.leagueUrl}?pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }

  createLeague(leagueDTO: LeagueDto,file: File): Observable<any> {
    let headers = this.getHeaders();
    const formData = new FormData();
    formData.append("file",file);
    formData.append('leagueDto', JSON.stringify(leagueDTO));
    return this.http.post<any>(`${this.leagueUrl}`, formData, {headers});
  }

  returnLeagueById(id: number): Observable<LeagueDto> {
    return this.http.get<LeagueDto>(`${this.leagueUrl}/${id}`);
  }

  deleteLeague(id: number): Observable<any> {
    return this.http.delete<any>(`${this.leagueUrl}/${id}`);
  }

  editLeague(leagueDto:LeagueDto, file: File|null): Observable<any> {
    let headers= this.getHeaders();
    const formData = new FormData();

    if(file != null){
      formData.append("file",file!);
    }
    formData.append("leagueDto",JSON.stringify(leagueDto));
    return this.http.put<any>(`${this.leagueUrl}/${leagueDto.id}`, formData,{headers});
  }
  public getImageUrl(imagePath: string): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`http://localhost:8080/images/${imagePath}`,{headers,responseType: 'blob'});
  }

  getSeasonsForLeague(leagueId: number): Observable<SeasonDto[]> {
    return this.http.get<SeasonDto[]>(`${this.leagueUrl}/${leagueId}/seasons`);
  }
  matchAllLeagues(): Observable<LeagueDto[]> {
    return this.http.get<LeagueDto[]>(`${this.leagueUrl}/matchAllLeagues`);
  }

  searchLeaguesByName(name: string, pageNumber: number, pageSize: number): Observable<PageResponseDto<LeagueDto>> {
    return this.http.get<PageResponseDto<LeagueDto>>(`${this.leagueUrl}/search?name=${name}&pageNumber=${pageNumber}&pageSize=${pageSize}`);
  }
  // searchLeaguesByName(name: string): Observable<LeagueDto[]> {
  //   return this.http.get<LeagueDto[]>(`${this.leagueUrl}/matchAllLeagues/${name}`);
  // }

}
