package com.example.android.simplefeeder;

/**
 * Created by lenovo on 4/8/2018.
 */

public class Details {
   private String title;
   private String description;
    private String image;
    private String link;
    private String date;
    public Details(String title,String description,String image,String link,String date)
    {
       this.title=title;
        this.description=description;
        this.image=image;
        this.link=link;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
     public String getImage(){return image;}
    public String getLink(){return link;}

}
