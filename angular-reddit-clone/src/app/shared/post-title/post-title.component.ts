import { Component, OnInit } from '@angular/core';
import { faArrowDown, faArrowUp, faComment } from '@fortawesome/free-solid-svg-icons';
import { PostModel } from 'src/app/home/post-model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-title',
  templateUrl: './post-title.component.html',
  styleUrls: ['./post-title.component.css']
})
export class PostTitleComponent implements OnInit {

posts$ : Array<PostModel> = [];
 faArrowUp =  faArrowUp
 faArrowDown = faArrowDown;
 faComment = faComment;
 upvoteColor = '';
 downvoteColor = '';

  constructor(private postService: PostService) {

    this.postService.getAllPosts().subscribe(data =>{
      console.log(data);
      this.posts$ = data;
   })
  }

  ngOnInit(): void {
  }

  goToPost(id:any):void{

  }
}
