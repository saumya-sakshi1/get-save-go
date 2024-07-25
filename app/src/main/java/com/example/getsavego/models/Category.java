package com.example.getsavego.models;

public class Category {
    private String categoryName;
    private int categoryImg;
    private int categoryColor;

    public Category(String categoryName, int categoryImg, int categoryColor) {
        this.categoryName = categoryName;
        this.categoryImg = categoryImg;
        this.categoryColor = categoryColor;
    }

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(int categoryImg) {
        this.categoryImg = categoryImg;
    }
}
