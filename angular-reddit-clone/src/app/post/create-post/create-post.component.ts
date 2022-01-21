import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { PostModel } from 'src/app/home/post-model';
import { PostService } from 'src/app/shared/post.service';
import { SubredditService } from 'src/app/subreddit/subreddit.service';
import { SubRedditResponse } from 'src/app/subreddit/subRedditResponse';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  createPostForm:FormGroup;
  postName : FormControl|undefined;
  url:FormControl|undefined;
  description:FormControl|undefined;
  subredditName:FormControl|undefined;
  subreddits:Array<SubRedditResponse> |undefined;

  constructor(private subRedditService:SubredditService, private router:Router, private postservice:PostService) {
    this.createPostForm = new FormGroup({
      postName : new FormControl('',Validators.required),
      url : new FormControl('',Validators.required),
      description : new FormControl('',Validators.required),
      subredditName : new FormControl('',Validators.required),
    });
    this.subRedditService.getAllSubReddit().subscribe(data =>{
        this.subreddits = data;
    },
    error =>{
      throwError(error);
    })
   }

  ngOnInit(): void {
  }
  discardPost(){
    this.router.navigateByUrl('/');
  }
  createPost(){
    const postModel:PostModel =  {
      postName:this.createPostForm.get('postName')?.value,
      url:this.createPostForm.get('url')?.value,
      description:this.createPostForm.get('description')?.value,
      subredditName:this.createPostForm.get('subredditName')?.value
    };
    this.postservice.createPost(postModel).subscribe(data=>{
        this.router.navigateByUrl('');
    },error =>{
      throwError(error);
    });
  }

}
