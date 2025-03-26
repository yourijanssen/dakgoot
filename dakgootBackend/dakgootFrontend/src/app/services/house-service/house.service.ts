// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
//
// @Injectable({
//   providedIn: 'root'
// })
// export class HouseService {
//   private apiUrl = 'http://localhost:8090/api/houses';
//
//   constructor(private http: HttpClient) {}
//
//   getHouses(): Observable<any> {
//     return this.http.get<any>(this.apiUrl);
//   }
//
//   getHouse(id: string | null): Observable<any> {
//     return this.http.get<any>(`${this.apiUrl}/${id}`);
//   }
//
//   updateHouse(id: string | null | undefined, house: any): Observable<any> {
//     return this.http.put<any>(`${this.apiUrl}/${id}`, house);
//   }
//
//   createHouse(house: any): Observable<any> {
//     return this.http.post<any>(this.apiUrl, house);
//   }
//
//   deleteHouse(id: number): Observable<any> {
//     return this.http.delete<any>(`${this.apiUrl}/${id}`);
//   }
// }
