package com.example.getsavego.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.getsavego.R;
import com.example.getsavego.adapters.TransactionsAdapter;
import com.example.getsavego.databinding.FragmentTransactionsBinding;
import com.example.getsavego.models.Transaction;
import com.example.getsavego.utils.Constants;
import com.example.getsavego.utils.Helper;
import com.example.getsavego.viewmodel.MainViewModel;
import com.example.getsavego.views.activities.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import io.realm.RealmResults;


public class TransactionsFragment extends Fragment {



    public TransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentTransactionsBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(inflater);
        viewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar= Calendar.getInstance();
        updateDate();
        String rs= getResources().getString(R.string.rupee_symbol);

        binding.prevDate.setOnClickListener(c->{
            if(Constants.selectedTab==Constants.DAILY)
                calendar.add(Calendar.DATE, -1);
            else if(Constants.selectedTab==Constants.MONTHLY)
                calendar.add(Calendar.MONTH, -1);
            updateDate();
        });
        binding.nextDate.setOnClickListener(c->{
            if(Constants.selectedTab==Constants.DAILY)
                calendar.add(Calendar.DATE, 1);
            else if(Constants.selectedTab==Constants.MONTHLY)
                calendar.add(Calendar.MONTH, 1);
            updateDate();
        });



        binding.floatingActionButton.setOnClickListener( c-> {
                    new AddTransactionFragment().show(getParentFragmentManager(), null);
                }
        );
        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));


        viewModel.transactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionsAdapter adapter= new TransactionsAdapter(getActivity(), transactions);
                binding.recyclerView1.setAdapter(adapter);
                if(transactions.size()>0)
                {binding.emptyState.setVisibility(View.GONE);}
                else
                    binding.emptyState.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getTransactions(calendar);

        viewModel.totalAmount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalValue.setText(rs+String.valueOf(aDouble));
            }
        });
        viewModel.totalExpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseValue.setText(rs+String.valueOf(aDouble));
            }
        });
        viewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeValue.setText(rs+String.valueOf(aDouble));
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Toast.makeText(MainActivity.this, tab.getText().toString(), Toast.LENGTH_SHORT).show();
                if(tab.getText().equals("Daily")) {
                    Constants.selectedTab=Constants.DAILY;
                    updateDate();}
                else if(tab.getText().equals("Monthly")){
                    Constants.selectedTab=Constants.MONTHLY;
                    updateDate();}
                else if(tab.getText().equals("Calendar")) {
                    Constants.selectedTab=Constants.CALENDAR;
                }
                else if(tab.getText().equals("Sumnmary"))
                    Constants.selectedTab=Constants.SUMMARY;
                else if(tab.getText().equals("Notes"))
                    Constants.selectedTab=Constants.NOTES;

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });
      viewModel.getTransactions(calendar);

        return binding.getRoot();
    }
    public void updateDate()
    {  if(Constants.selectedTab==Constants.DAILY) {
        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));}
    else if(Constants.selectedTab==Constants.MONTHLY) {
        binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
    }
        viewModel.getTransactions(calendar);
    }
}