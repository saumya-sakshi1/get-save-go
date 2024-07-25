package com.example.getsavego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getsavego.R;
import com.example.getsavego.databinding.ListDialogBinding;
import com.example.getsavego.databinding.RowAccountsBinding;
import com.example.getsavego.models.Account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {

    ArrayList<Account> accounts;
    Context context;

    public interface AccountClickListener{
        void onAccountClicked(Account account);
    }
    AccountClickListener accountClickListener;

    public AccountAdapter(ArrayList<Account> accounts, Context context, AccountClickListener accountClickListener) {
        this.accounts = accounts;
        this.context = context;
        this.accountClickListener = accountClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_accounts,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Account account= accounts.get(position);
          holder.binding.accountName.setText(account.getAccountName());
          holder.itemView.setOnClickListener(c->{
              accountClickListener.onAccountClicked(account);
          });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
       RowAccountsBinding binding;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountsBinding.bind(itemView);

        }
    }
}
