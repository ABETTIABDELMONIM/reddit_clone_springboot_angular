import { Component, OnInit } from '@angular/core';
import { SubredditService } from 'src/app/subreddit/subreddit.service';
import { SubRedditResponse } from 'src/app/subreddit/subRedditResponse';

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {
  subReddits:Array<SubRedditResponse> | undefined ;
  displayViewAll:boolean = false;
  constructor(private subRedditService:SubredditService) {
    this.subRedditService.getAllSubReddit().subscribe(data =>{
      if(data?.length >= 4){
        this.subReddits = data.splice(0,3);
        this.displayViewAll=false;
      }else{
        this.subReddits = data;
        this.displayViewAll=true;
      }
    })
   }

  ngOnInit(): void {
  }

}
