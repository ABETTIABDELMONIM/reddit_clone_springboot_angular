import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { SubredditService } from '../subreddit.service';
import { SubRedditResponse } from '../subRedditResponse';

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent implements OnInit {
  createSubredditForm:FormGroup;
  subredditModel:SubRedditResponse;
  title = new FormControl();
  description = new FormControl();

  constructor(private router:Router,private subRedditService:SubredditService) {
    this.createSubredditForm = new FormGroup({
      title : new FormControl('',Validators.required),
      description: new FormControl('',Validators.required)
    });
    this.subredditModel = {
      name:'',
      description:''
    };
   }

  ngOnInit(): void {
  }
  createSubreddit(){
    this.subredditModel.name = this.createSubredditForm.get('title')?.value;
    this.subredditModel.description = this.createSubredditForm.get('description')?.value;
    this.subRedditService.createSubReddit(this.subredditModel).subscribe(data =>{
      console.log(data);
      this.router.navigateByUrl('/list-subreddits');
    },error =>{
      console.log(error);
      throwError(error);
    })
  }
  discard(){
    this.router.navigateByUrl('/');
  }


}
