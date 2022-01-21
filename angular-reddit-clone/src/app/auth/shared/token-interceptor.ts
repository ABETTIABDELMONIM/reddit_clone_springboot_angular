import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, Observable, switchMap, throwError } from "rxjs";
import { LoginResponsePayLoad } from "../login/LoginResponsePayload";
import { AuthService } from "./auth.service";

@Injectable({
  providedIn:'root'
})

export class TokenInterceptor implements HttpInterceptor{
  private isTokenRefreshing:boolean = false;
  private refreshTokenSubject : BehaviorSubject<any> = new BehaviorSubject(null);
  constructor(private authService:AuthService){

  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      const jwtToken = this.authService.getJwtToken();
      let reqWithToken = req;
      if(jwtToken){
        reqWithToken = this.addToken(req,jwtToken);
      }
      return next.handle(reqWithToken).pipe(catchError( error =>{
        if(error instanceof HttpErrorResponse && error.status === 403){
          return this.handleAuthError(req,next);
        }else{
          return throwError(error);
        }
      }));
  }
  addToken(req: HttpRequest<any>, jwtToken: string): HttpRequest<any> {
    return  req.clone({ setHeaders: { Authorization: jwtToken } });
  }
  private handleAuthError(req: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>>{
    if(!this.isTokenRefreshing){
      this.isTokenRefreshing=true;
      this.refreshTokenSubject.next(null);
    }
    return this.authService.refreshToken().pipe(switchMap((refreshTokenResponse:LoginResponsePayLoad)=>{
      this.isTokenRefreshing=false;
      this.refreshTokenSubject.next(refreshTokenResponse.token);
      return next.handle(this.addToken(req,refreshTokenResponse.token));
    }));
  }
}
