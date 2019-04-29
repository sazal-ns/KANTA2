package com.brosolved.siddiqui.kanta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/*
 * com.brosolved.siddiqui.kanta.adapter is created by Noor Nabiul Alam Siddiqui on 2/18/2019
 *
 * BroSolved (c) 2019.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private   Context context;
    private List<Product> products;

    private OnProductClick onProductClick;

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
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.productImage);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.name.setText(products.get(position).getName());
        holder.price.setText(products.get(position).getPrice()+" BDT");
        holder.ratingBar.setRating(products.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView name, price;
        RatingBar ratingBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            productImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductClick.onClick(v, getAdapterPosition());
        }
    }

    public void setOnProductClickListener(OnProductClick onProductClick){
        this.onProductClick = onProductClick;
    }

    public interface OnProductClick{
        void onClick(View view, int position);
    }
}
