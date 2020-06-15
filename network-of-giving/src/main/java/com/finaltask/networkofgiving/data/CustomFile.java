package com.finaltask.networkofgiving.data;

import lombok.Data;

@Data
public class CustomFile {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String title;
   private String body;

   public CustomFile(String title, String body){
       this.title = title;
       this.body = body;
   }
}
