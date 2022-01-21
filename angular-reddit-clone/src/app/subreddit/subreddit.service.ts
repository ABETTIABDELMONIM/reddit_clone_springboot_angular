import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubRedditResponse } from './subRedditResponse';

@Injectable({
  providedIn: 'root'
})
export class SubredditService {
  constructor(private httpClient:HttpClient) { }

  getAllSubReddit():Observable<Array<SubRedditResponse>>{
    return this.httpClient.get<Array<SubRedditResponse>>('http://localhost:8080/api/reddit');
  }

  createSubReddit(subredditModel: SubRedditResponse):Observable<SubRedditResponse> {
   return this.httpClient.post<SubRedditResponse>('http://localhost:8080/api/reddit',subredditModel);
  }
}
