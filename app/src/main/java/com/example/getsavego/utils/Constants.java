package com.example.getsavego.utils;

import com.example.getsavego.R;
import com.example.getsavego.models.Category;

import java.util.ArrayList;

public class Constants {
    public  static String INCOME= "INCOME";
    public static String EXPENSE= "EXPENSE";
    public static ArrayList<Category> categories;
    public static int selectedTab=0;
    public static int selectedTabStats=0;
    public static String SELECTED_STATS_TYPE= Constants.INCOME;
    public static int DAILY=0;
    public static int MONTHLY=1;
    public static int CALENDAR=2;
    public static int SUMMARY=3;
    public static int NOTES=4;

    public static String CLIENT_ID="150738019373-549o2e7gr5t143cvpa0vv2od5lr5fa6b.apps.googleusercontent.com";
    public static  String APP_ID="get-save-go-uuwsema";

    public  static void setCategories()
    {   categories= new ArrayList<>();
        categories.add(new Category("Investments", R.drawable.investments,R.color.category1));
        categories.add(new Category("Business",R.drawable.business,R.color.category2));
        categories.add(new Category("Loan",R.drawable.loan,R.color.category3));
        categories.add(new Category("Salary",R.drawable.salary,R.color.category4));
        categories.add(new Category("Rent",R.drawable.rent,R.color.category5));
        categories.add(new Category("Recreation",R.drawable.recreation,R.color.category6));
        categories.add(new Category("Medical",R.drawable.medical,R.color.category7));
        categories.add(new Category("Other",R.drawable.miscellaneous,R.color.category8));
    }
    
    public static Category getCategory(String categoryName)
    {
        for (Category cat:
             categories) {
            if(cat.getCategoryName().equals(categoryName))
               return cat;
        }
        return null;
    }

    public static int getAccountsColor(String accountName)
    { switch (accountName) {
        case "Bank":
            return R.color.bank;
        case "Cash":
            return R.color.cash;
        case "Phonepe":
            return R.color.phonepe;
        case "Paytm":
            return R.color.paytm;
        case "Gpay":
            return R.color.gpay;
        case "Other":
            return R.color.other;
        default:
            return R.color.other;
    }



    }
}
