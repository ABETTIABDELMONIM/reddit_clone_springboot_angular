import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-side-barn',
  templateUrl: './side-barn.component.html',
  styleUrls: ['./side-barn.component.css']
})
export class SideBarnComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  goToCreateSubreddit(){
      this.router.navigateByUrl('/create-subreddit')
  }
  goToCreatePost(){
    this.router.navigateByUrl('/create-post');
  }

}
