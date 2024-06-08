import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {PlayerDto} from "../common/player-dto";
import {PageResponseDto} from "../common/page-response-dto";

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  playerUrl: string = "http://localhost:8080/api/players"
  constructor(private http: HttpClient) { }
  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
    });
  }
  public createPlayer(playerDto: PlayerDto,file: File): Observable<any>{
    let headers = this.getHeaders();
    const formData = new FormData();
    formData.append("file",file);
    formData.append('playerDto', JSON.stringify(playerDto)); // Convert playerDto to JSON string and append
    return this.http.post(`${this.playerUrl}/create`,formData,{headers});
  }
  public retrievePlayers(pageNumber: number, pageSize: number): Observable<PageResponseDto<PlayerDto>>{
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto<PlayerDto>>(`${this.playerUrl}/?page=${pageNumber}&size=${pageSize}`,{headers});
  }
  public getAllPLayers():Observable<PlayerDto[]>{
    return this.http.get<PlayerDto[]>(`${this.playerUrl}`);
  }
  public retrievePlayer(id: number): Observable<PlayerDto>{
    let headers = this.getHeaders();
    return this.http.get<PlayerDto>(`${this.playerUrl}/${id}`,{headers})
  }
    public updatePlayer(playerDto: PlayerDto, file: File|null): Observable<any> {
        let headers = this.getHeaders();
        const formData = new FormData();
        if (file != null) {
            formData.append("file", file);
        }
        formData.append("playerDto", JSON.stringify(playerDto));

        console.log('URL:', `${this.playerUrl}/edit/${playerDto.dbId}`); // Debugging log

        return this.http.post(`${this.playerUrl}/edit/${playerDto.dbId}`, formData, { headers });
    }

  public sendDeletePlayerPermission(id: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.post(`${this.playerUrl}/delete/permission/${id}`,{headers})
  }
  public acceptDeletePlayerPermission(id: number): Observable<any>{
    let headers = this.getHeaders();
    return this.http.delete(`${this.playerUrl}/delete/${id}`,{headers})
  }
  public getImageUrl(imagePath: string): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`http://localhost:8080/images/${imagePath}`,{headers,responseType: 'blob'});
  }

  public getPlayersSortedByHeight(pageNumber: number, pageSize: number): Observable<PageResponseDto<PlayerDto>> {
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto<PlayerDto>>(`${this.playerUrl}/sortedByHeight?pageNumber=${pageNumber}&pageSize=${pageSize}`, { headers });
  }

  public getPlayersSortedByHeightDesc(pageNumber: number, pageSize: number): Observable<PageResponseDto<PlayerDto>> {
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto<PlayerDto>>(`${this.playerUrl}/sortedByHeightDesc?pageNumber=${pageNumber}&pageSize=${pageSize}`, { headers });
  }

  public getPlayersSortedByWeight(pageNumber: number, pageSize: number): Observable<PageResponseDto<PlayerDto>> {
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto<PlayerDto>>(`${this.playerUrl}/sortedByWeight?pageNumber=${pageNumber}&pageSize=${pageSize}`, { headers });
  }

  public getPlayersSortedByWeightDesc(pageNumber: number, pageSize: number): Observable<PageResponseDto<PlayerDto>> {
    let headers = this.getHeaders();
    return this.http.get<PageResponseDto<PlayerDto>>(`${this.playerUrl}/sortedByWeightDesc?pageNumber=${pageNumber}&pageSize=${pageSize}`, {headers});
  }
  public getDeletedPlayerIds(): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`${this.playerUrl}/deleted`,{headers});
  }
  public getPlayerIdsWhoAskedPermissionFromCurrentUser(): Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`${this.playerUrl}/askedpermission/currentuser`,{headers});

  }

  public getPlayersByClubId(club_id:number):Observable<any>{
    let headers = this.getHeaders();
    return this.http.get(`${this.playerUrl}/club/${club_id}`,{headers});
  }
}

