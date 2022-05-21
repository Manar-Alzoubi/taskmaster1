package com.example.taskmaster1;


public class Task {

    private String title;
    private String body;
    private String state;

    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        setState(state);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setState(String state) {
        if(state == "new") {
            this.state = state;
        }else if (state == "assigned"){
            this.state = state;
        } else if (state == "in progress"){
            this.state = state;
        } else if (state == "complete"){
            this.state = state;
        }else System.out.println("state should be one of : new ,assigned, in progress, complete");
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }


    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
