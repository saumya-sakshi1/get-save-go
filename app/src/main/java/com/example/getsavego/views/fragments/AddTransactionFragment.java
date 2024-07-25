package com.example.getsavego.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.getsavego.R;
import com.example.getsavego.adapters.AccountAdapter;
import com.example.getsavego.adapters.CategoryAdapter;
import com.example.getsavego.databinding.FragmentAddTransactionBinding;
import com.example.getsavego.databinding.ListDialogBinding;
import com.example.getsavego.models.Account;
import com.example.getsavego.models.Category;
import com.example.getsavego.models.Transaction;
import com.example.getsavego.utils.Constants;
import com.example.getsavego.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.blurry.Blurry;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentAddTransactionBinding binding;
    Transaction transaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater);
        View decorView = getActivity().getWindow().getDecorView().getRootView();
        transaction= new Transaction();

        binding.IncomeBtn.setOnClickListener(view->{
            binding.IncomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.ExpenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.IncomeBtn.setTextColor(getContext().getColor(R.color.green));
            binding.ExpenseBtn.setTextColor(getContext().getColor(R.color.unselectedTextColor));

            transaction.setType(Constants.INCOME);
        });

        binding.ExpenseBtn.setOnClickListener(view->{
            binding.IncomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.ExpenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.ExpenseBtn.setTextColor(getContext().getColor(R.color.red));
            binding.IncomeBtn.setTextColor(getContext().getColor(R.color.unselectedTextColor));
            transaction.setType(Constants.EXPENSE);
        });

        binding.selectDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());


            Blurry.with(getContext())
                    .radius(6)
                    .sampling(2)
                    .onto((ViewGroup) decorView);
            datePickerDialog.setOnDismissListener(dialog -> {
                Blurry.delete((ViewGroup) decorView);
            });


            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    calendar.set(calendar.MONTH, datePicker.getMonth());
                    calendar.set(calendar.YEAR, datePicker.getYear());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                    String dateToShow = simpleDateFormat.format(calendar.getTime());
                    binding.selectDate.setText(dateToShow);
                    transaction.setDate(calendar.getTime());
                    transaction.setId(calendar.getTime().getTime());

                }

            });
            datePickerDialog.show();
        });

            binding.selectCategory.setOnClickListener(c->
            {
                ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);
               // View decorView = getActivity().getWindow().getDecorView().getRootView();
               // View fragmentRootView = getView();
                Blurry.with(getContext())
                        .radius(6)
                        .sampling(2)
                        .onto((ViewGroup) decorView);
              //  if (fragmentRootView instanceof ViewGroup) {
                //    Blurry.with(getContext())
                  //          .radius(6)
                    //        .sampling(3)
                      //      .onto((ViewGroup) fragmentRootView);
                //}

                AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
               // categoryDialog.setTitle("Select a category:");
                categoryDialog.setView(listDialogBinding.getRoot());

//                ArrayList<Category> categories = new ArrayList<>();
//                categories.add(new Category("Investments",R.drawable.investments,R.color.category1));
//                categories.add(new Category("Business",R.drawable.business,R.color.category2));
//                categories.add(new Category("Loan",R.drawable.loan,R.color.category3));
//                categories.add(new Category("Salary",R.drawable.salary,R.color.category4));
//                categories.add(new Category("Rent",R.drawable.rent,R.color.category5));
//                categories.add(new Category("Recreation",R.drawable.recreation,R.color.category6));
//                categories.add(new Category("Medical",R.drawable.medical,R.color.category7));
//                categories.add(new Category("Other",R.drawable.miscellaneous,R.color.category8));


                CategoryAdapter adapter= new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListener() {
                    @Override
                    public void onCategoryClicked(Category category) {
                         binding.selectCategory.setText(category.getCategoryName());
                         transaction.setCategory(category.getCategoryName());
                         categoryDialog.dismiss();
                    }
                });
                listDialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                listDialogBinding.recyclerView.setAdapter(adapter);
                categoryDialog.show();

                // Remove the blur effect when the dialog is dismissed
                categoryDialog.setOnDismissListener(dialog -> {
                    Blurry.delete((ViewGroup) decorView);

                   // if (fragmentRootView instanceof ViewGroup) {
                     //   Blurry.delete((ViewGroup) fragmentRootView);
                    //}
                });
                });


        binding.selectAccount.setOnClickListener(c->
        {   ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);

            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();

           // View decorView = getActivity().getWindow().getDecorView().getRootView();
            Blurry.with(getContext())
                    .radius(6)
                    .sampling(2)
                    .onto((ViewGroup) decorView);
            accountsDialog.setView(listDialogBinding.getRoot());

            ArrayList<Account> accounts = new ArrayList<>();
            accounts.add(new Account(0, "Cash"));
            accounts.add(new Account(0, "Bank"));
            accounts.add(new Account(0, "PhonePe"));
            accounts.add(new Account(0, "Paytm"));
            accounts.add(new Account(0, "Gpay"));
            accounts.add(new Account(0, "Other"));
            AccountAdapter accountAdapter= new AccountAdapter(accounts, getContext(), new AccountAdapter.AccountClickListener() {
                @Override
                public void onAccountClicked(Account account) {
                    binding.selectAccount.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());
                    accountsDialog.dismiss();
                }
            });
            listDialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            listDialogBinding.recyclerView.setAdapter(accountAdapter);
            listDialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            accountsDialog.show();

            accountsDialog.setOnDismissListener(dialog -> {
                Blurry.delete((ViewGroup) decorView);
            });

        });

        binding.saveTransactionButton.setOnClickListener(c->{
            double amount = Double.parseDouble(binding.selectAmount.getText().toString());
            String note =binding.selectNote.getText().toString();
            if(transaction.getType().equals(Constants.EXPENSE))
            {transaction.setAmount(amount * -1);}
            else
            {transaction.setAmount(amount);}
            transaction.setNote(note);
            ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
            ((MainActivity)getActivity()).getTransactions();
            dismiss();
        });




        return binding.getRoot();
    }
}