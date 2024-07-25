package com.example.getsavego.adapters;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getsavego.R;
import com.example.getsavego.databinding.ActivityMainBinding;
import com.example.getsavego.databinding.RowTransactionBinding;
import com.example.getsavego.models.Category;
import com.example.getsavego.models.Transaction;
import com.example.getsavego.utils.Constants;
import com.example.getsavego.utils.Helper;
import com.example.getsavego.views.activities.MainActivity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

    Context context;
    RealmResults<Transaction> transactions;

    public TransactionsAdapter(Context context, RealmResults<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction= transactions.get(position);
        String rs= context.getResources().getString(R.string.rupee_symbol);

       holder.binding.transactionAmount.setText(rs+String.valueOf(transaction.getAmount()));
       // holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
       holder.binding.transactionType.setText(transaction.getCategory());
       holder.binding.transactionDate.setText(Helper.formatDate(transaction.getDate()));
       holder.binding.accountLabel.setText(transaction.getAccount());


       if(transaction.getType().equals(Constants.INCOME))
           holder.binding.transactionAmount.setTextColor(context.getColor(R.color.green));
       else if(transaction.getType().equals(Constants.EXPENSE))
           holder.binding.transactionAmount.setTextColor(context.getColor(R.color.red));
        Category category= Constants.getCategory(transaction.getCategory());
        holder.binding.categoryIcon.setImageResource(category.getCategoryImg());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColor()));
        holder.binding.accountLabel.setBackgroundTintList(context.getColorStateList(
                Constants.getAccountsColor(transaction.getAccount())));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deleteDialog= new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete transaction");
                deleteDialog.setMessage("Are you sure you want to delete this transaction?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                    ((MainActivity)context).viewModel.deleteTransaction(transaction);
                    deleteDialog.dismiss();
                });

                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {
                  deleteDialog.dismiss();
                });
                deleteDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        RowTransactionBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
