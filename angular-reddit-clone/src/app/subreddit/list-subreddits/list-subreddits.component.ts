import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { SubredditService } from '../subreddit.service';
import { SubRedditResponse } from '../subRedditResponse';

@Component({
  selector: 'app-list-subreddits',
  templateUrl: './list-subreddits.component.html',
  styleUrls: ['./list-subreddits.component.css']
})
export class ListSubredditsComponent implements OnInit {
  subreddits:Array<SubRedditResponse>|any;
  constructor(private subredditService:SubredditService) {
    this.subredditService.getAllSubReddit().subscribe(data =>{
      this.subreddits = data;
    },error =>{
      throwError(error);
    });
   }

  ngOnInit(): void {
  }

}
