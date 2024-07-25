package com.example.getsavego.views.fragments;

import static com.example.getsavego.utils.Constants.SELECTED_STATS_TYPE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.getsavego.R;
import com.example.getsavego.databinding.FragmentStatsBinding;
import com.example.getsavego.models.Transaction;
import com.example.getsavego.utils.Constants;
import com.example.getsavego.utils.Helper;
import com.example.getsavego.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

public class StatsFragment extends Fragment {



    public StatsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentStatsBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =FragmentStatsBinding.inflate(inflater);

        viewModel= new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar= Calendar.getInstance();
        updateDate();
        Pie pie = AnyChart.pie();

        binding.IncomeBtn.setOnClickListener(view->{
            binding.IncomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.ExpenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.IncomeBtn.setTextColor(getContext().getColor(R.color.green));
            binding.ExpenseBtn.setTextColor(getContext().getColor(R.color.unselectedTextColor));
            pie.title("Income distribution:");

            SELECTED_STATS_TYPE=Constants.INCOME;
            updateDate();
        });

        binding.ExpenseBtn.setOnClickListener(view->{
            binding.IncomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.ExpenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.ExpenseBtn.setTextColor(getContext().getColor(R.color.red));
            binding.IncomeBtn.setTextColor(getContext().getColor(R.color.unselectedTextColor));
            SELECTED_STATS_TYPE=Constants.EXPENSE;
            pie.title("Expense distribution:");
            updateDate();
        });

        binding.prevDate.setOnClickListener(c->{
            if(Constants.selectedTabStats==Constants.DAILY)
                calendar.add(Calendar.DATE, -1);
            else if(Constants.selectedTabStats==Constants.MONTHLY)
                calendar.add(Calendar.MONTH, -1);
            updateDate();
        });
        binding.nextDate.setOnClickListener(c->{
            if(Constants.selectedTabStats==Constants.DAILY)
                calendar.add(Calendar.DATE, 1);
            else if(Constants.selectedTabStats==Constants.MONTHLY)
                calendar.add(Calendar.MONTH, 1);
            updateDate();
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Toast.makeText(MainActivity.this, tab.getText().toString(), Toast.LENGTH_SHORT).show();
                if(tab.getText().equals("Daily")) {
                    Constants.selectedTabStats=0;
                    updateDate();}
                else if(tab.getText().equals("Monthly")){
                    Constants.selectedTabStats=1;
                    updateDate();}

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });




        viewModel.categoriesTransactions.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                if(transactions.size()>0)
                {
                    binding.emptyState.setVisibility(View.GONE);
                    binding.anyChart.setVisibility(View.VISIBLE);
                    List<DataEntry> data = new ArrayList<>();
                    Map<String, Double> categoryMap= new HashMap<>();

                    for(Transaction transaction : transactions)
                    {
                        String category= transaction.getCategory();
                        double amount= transaction.getAmount();
                        if(categoryMap.containsKey(category))
                        {
                            double currentTotal=categoryMap.get(category).doubleValue();
                            currentTotal+=Math.abs(amount);
                            categoryMap.put(category,currentTotal);
                        }
                        else {categoryMap.put(category,Math.abs(amount));}
                    }

                    for(Map.Entry<String,Double> entry: categoryMap.entrySet())
                    {
                        data.add(new ValueDataEntry(entry.getKey(),entry.getValue()));
                    }
                    pie.data(data);

                }
                else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                    binding.anyChart.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getTransactions(calendar, SELECTED_STATS_TYPE);





//       pie.title("Income distribution:");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

        binding.anyChart.setChart(pie);
        return binding.getRoot();
    }

    public void updateDate()
    {  if(Constants.selectedTabStats==Constants.DAILY) {
        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));}
    else if(Constants.selectedTabStats==Constants.MONTHLY) {
        binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
    }
        viewModel.getTransactions(calendar,SELECTED_STATS_TYPE);
    }
}