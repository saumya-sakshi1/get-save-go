package com.example.getsavego.viewmodel;

import android.app.Application;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.getsavego.models.Transaction;
import com.example.getsavego.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
    Realm realm;
    Calendar calendar;
    public MutableLiveData<RealmResults<Transaction>> transactions = new MutableLiveData<>();
    public MutableLiveData<RealmResults<Transaction>> categoriesTransactions = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome= new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();
    public MainViewModel(@NonNull Application application) {
        super(application);
        //Realm.init(application);
        setupDatabase();
//        addTransactions();
    }

    void setupDatabase()
    {
        realm=Realm.getDefaultInstance();//getInstance() helps us customize whether we want to write on UI
        //thread or not,etc
    }

    public void deleteTransaction(Transaction transaction)
    {   realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
      getTransactions(calendar);
    }

    public void addTransactions(Transaction transaction)
    {
        realm.beginTransaction();   //We have to create a transaction before doing any work on realm!

        realm.copyToRealmOrUpdate(transaction);

        realm.commitTransaction();
    }

    public void getTransactions(Calendar calendar, String type)
    {  this.calendar=calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        RealmResults<Transaction> newTransactions =null;

        if(Constants.selectedTabStats==Constants.DAILY) {

            newTransactions = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", calendar.getTime()).
                    lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type" ,type)
                    .findAll();



        }
        else if(Constants.selectedTabStats== Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endTime = calendar.getTime();
            newTransactions = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", startTime).
                    lessThan("date", endTime)
                    .equalTo("type" ,type)
                    .findAll();

        }

        categoriesTransactions.setValue(newTransactions);
    }

    public void getTransactions(Calendar calendar)
    {  this.calendar=calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        double income=0;
        double expense=0;
        double total=0;
        RealmResults<Transaction> newTransactions =null;

        if(Constants.selectedTab==Constants.DAILY) {

            //Select * from transactions, but Realm is no-SQL based db
//        RealmResults<Transaction> newTransactions= realm.where(Transaction.class).
//                equalTo("date",calendar.getTime()).findAll();


            newTransactions = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", calendar.getTime()).
                    lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .findAll();

             income = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", calendar.getTime()).
                    lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.INCOME)
                    .sum("amount")
                    .doubleValue();
             expense = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", calendar.getTime()).
                    lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .equalTo("type", Constants.EXPENSE)
                    .sum("amount")
                    .doubleValue();
             total = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", calendar.getTime()).
                    lessThan("date", new Date(calendar.getTime().getTime() + (24 * 60 * 60 * 1000)))
                    .sum("amount")
                    .doubleValue();

        }
        else if(Constants.selectedTab== Constants.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            Date startTime = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endTime = calendar.getTime();
            newTransactions = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", startTime).
                    lessThan("date", endTime)
                    .findAll();
            income = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", startTime).
                    lessThan("date", endTime)
                    .equalTo("type", Constants.INCOME)
                    .sum("amount")
                    .doubleValue();
            expense = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", startTime).
                    lessThan("date", endTime)
                    .equalTo("type", Constants.EXPENSE)
                    .sum("amount")
                    .doubleValue();
            total = realm.where(Transaction.class).
                    greaterThanOrEqualTo("date", startTime).
                    lessThan("date", endTime)
                    .sum("amount")
                    .doubleValue();
        }
        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);
        transactions.setValue(newTransactions);
    }
}
