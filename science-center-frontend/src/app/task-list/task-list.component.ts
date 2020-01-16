import { Task } from './../model/task';
import { TaskService } from '../services/task.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  tasks: Task[] = [];

  constructor(private taskService: TaskService) { }

  ngOnInit() {
    this.getUserTasks();
  }

  // get all available task for the logged in user
  getUserTasks() {
    this.taskService.getTasks().subscribe(
      data => {
        console.log('Successfully got tasks.');

        this.tasks = data;
      },
      error => {
        if (error.status === 403) {
          alert('You must be logged in to get tasks!');
        } else {
          alert('An error occured! Please try again.');
        }
      }
    );
  }

}
