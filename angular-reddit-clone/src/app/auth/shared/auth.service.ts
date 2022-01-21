import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { SignupRequestPayload } from '../signup/signup-request-payload';
import { map, Observable } from 'rxjs';
import { LoginRequestPayload } from '../login/LoginRequestPayload';
import { LoginResponsePayLoad } from '../login/LoginResponsePayload';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private localStorage:LocalStorageService) { }

  signup(signupRequestPayload:SignupRequestPayload):Observable<any>{
    return this.httpClient.post('http://localhost:8080/api/auth',signupRequestPayload,{responseType:'text'});
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.httpClient.post<LoginResponsePayLoad>('http://localhost:8080/api/auth/Login',loginRequestPayload)
                          .pipe(map(data =>{
                              this.localStorage.store('token',data.token);
                              this.localStorage.store('username',data.userName);
                              this.localStorage.store('refreshToken',data.refreshToken);
                              this.localStorage.store('expiresAt',data.expiredAt);
                              return true;
                          }));
  }

  refreshToken(): Observable<any> {
    const refreshTokenPayload = {
      refreshToken : this.getRefreshToken(),
      userName : this.getUserName()
    }
      return this.httpClient.post<LoginResponsePayLoad>('http://localhost:8080/api/auth/refresh',refreshTokenPayload)
    .pipe(map(data =>{
        this.localStorage.store('token',data.token);
        this.localStorage.store('expiresAt',data.expiredAt);
        return true;
    }));
  }
  getUserName() {
    return this.localStorage.retrieve('username');
  }
  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getJwtToken():string{
    return this.localStorage.retrieve('token');
  }
}
