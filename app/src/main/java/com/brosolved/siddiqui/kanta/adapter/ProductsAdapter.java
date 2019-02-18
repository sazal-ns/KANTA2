package com.brosolved.siddiqui.kanta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * com.brosolved.siddiqui.kanta.adapter is created by Noor Nabiul Alam Siddiqui on 2/18/2019
 *
 * BroSolved (c) 2019.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private   Context context;
    private List<Product> products;

    public ProductsAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.product_card,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            Glide.with(context)
                    .asBitmap()
                    .load(products.get(position).getImageUrl1())
                    .into(holder.proudctImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.name.setText(products.get(position).getName());
        holder.price.setText(products.get(position).getPrice()+" BDT");



    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView proudctImage;
        TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proudctImage = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
        }
    }
}
