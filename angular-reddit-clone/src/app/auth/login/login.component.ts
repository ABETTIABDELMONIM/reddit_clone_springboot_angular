import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { LoginRequestPayload } from './LoginRequestPayload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm:FormGroup;
  isError : boolean;
  registerSuccessMessage:string;
  loginRequestPayload : LoginRequestPayload

  constructor(private authService:AuthService, private router:Router,
                private toastr: ToastrService, private activatedRoute:ActivatedRoute) {
    this.loginForm = new FormGroup({});
    this.loginRequestPayload = {
      userName:'',
      password:''
    };
    this.isError = false;
    this.registerSuccessMessage='';
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl('',Validators.required),
      password: new FormControl('',Validators.required)
    });
    this.activatedRoute.queryParams.subscribe(params=>{
      console.log(params);
      if(params['registered'] !== undefined && params['registered'] === 'true'){
        this.toastr.success('Signup Successfully');
        this.registerSuccessMessage='Please check your inbox for activate you account before login';
      }
    });
  }
  login(){
    this.loginRequestPayload.userName = this.loginForm.get('username')?.value;
    this.loginRequestPayload.password = this.loginForm.get('password')?.value;
    this.authService.login(this.loginRequestPayload)
                    .subscribe(data =>{
                      console.log(data);
                      if(data){
                        this.isError=false;
                        this.toastr.success('Login successful');
                        this.router.navigateByUrl('/');
                      }else{
                        this.isError=true;
                      }
                    });

  }

}
