package com.example.pritul.ictquerygenerator;

public  class ICT {
    private String Info;
    private String Name;
    private String image;


    public ICT(){}
    public ICT(String Name, String image, String Info) {
        this.Name = Name;
        this.image = image;
        this.Info = Info;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }

    public String getimage() {
        return image;
    }





    public void setimage(String image) {
        this.image = image;
    }


}