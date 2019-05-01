package com.brosolved.siddiqui.kanta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.Account;

import java.util.List;

/*
 * com.brosolved.siddiqui.kanta.adapter is created by Noor Nabiul Alam Siddiqui on 5/1/2019
 *
 * BroSolved (c) 2019.
 */
public class AccountAdapter extends BaseAdapter {

    private Context context;
    private List<Account> accounts;

    public AccountAdapter(Context context, List<Account> accounts) {
        this.context = context;
        this.accounts = accounts;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_account, null, true);

            holder.month =  convertView.findViewById(R.id.month);
            holder.amount = convertView.findViewById(R.id.amount);
            holder.product = convertView.findViewById(R.id.product);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.month.setText(accounts.get(position).getMonth());
        holder.amount.setText(accounts.get(position).getAmount()+" BDT");
        holder.product.setText(accounts.get(position).getProduct()+" Items");

        return convertView;
    }

    private class ViewHolder{
        TextView month, amount, product;
    }
}
