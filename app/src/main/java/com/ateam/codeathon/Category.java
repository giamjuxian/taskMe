package com.ateam.codeathon;

public enum Category {
    ALL("All"), WALK_PETS("Walk pets"), CLEANING("Cleaning"), COOKING("Cooking"), BABY_SITTING("Baby Sitting"),
    GROCERY_SHOPPING("Grocery Shopping"), OTHERS("Others");

    String name;
    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
