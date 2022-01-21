import { Component, Input, OnInit } from '@angular/core';
import { faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons';
import { PostModel } from 'src/app/home/post-model';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {
  @Input() post :PostModel|undefined ;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor:string='green'
  downvoteColor:string='green'
  constructor() {
   }

  ngOnInit(): void {
  }
  downvotePost():void{

  }
  upvotePost():void{}
}
