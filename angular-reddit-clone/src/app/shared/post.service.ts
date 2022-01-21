import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostModel } from '../home/post-model';
import { AuthService } from "../auth/shared/auth.service";

@Injectable({
  providedIn: 'root'
})
export class PostService {


  constructor(private httpClient: HttpClient, private authService:AuthService) { }

  getAllPosts():Observable<Array<PostModel>>{
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/api/post');
  }

  createPost (postModel:PostModel):Observable<PostModel>{
    postModel.userName = this.authService.getUserName();
    return this.httpClient.post<PostModel>('http://localhost:8080/api/post',postModel);
  }
}
