package com.abjlab.swipelist;


import static android.R.attr.description;

public class Beer {

    private int beerIcon;
    private String name;
    private String desc;

    public Beer(int icon, String name, String desc) {
        this.beerIcon = icon;
        this.name = name;
        this.desc = desc;
    }

    public int getBeerIcon() {
        return beerIcon;
    }

    public void setBeerIcon(int icon) {
        this.beerIcon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
