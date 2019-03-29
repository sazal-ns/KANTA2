package com.brosolved.siddiqui.kanta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*
 * com.brosolved.siddiqui.kanta.adapter is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private static final String TAG = "CategoryAdapter";

    private Context context;
    private List<Product> products;

    private OnUpdateClick onUpdateClick;
    private OnDeleteClick onDelteClick;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_product_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Glide.with(context)
                .asBitmap()
                .load("http://dev.brosolved.com/kanta/pic/product/"+products.get(position).getImageUrl1())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView);

        holder.name.setText(products.get(position).getName());
        holder.price.setText(products.get(position).getPrice());
        holder.quaintly.setText(String.valueOf(products.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, quaintly;
        Button update, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            quaintly = itemView.findViewById(R.id.quantity);
            update = itemView.findViewById(R.id.updateProduct);
            delete = itemView.findViewById(R.id.deleteProduct);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClick.onUpdateClick(v, getAdapterPosition());
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDelteClick.onDeleteClick(v, getAdapterPosition());
                }
            });

        }

    }

    public void setOnUpdateClickListener(OnUpdateClick onUpdateClick){
        this.onUpdateClick = onUpdateClick;

    }

    public void setOnDelteClickListener(OnDeleteClick onDelteClick){
        this.onDelteClick = onDelteClick;
    }

    public interface OnUpdateClick {
        void onUpdateClick(View view, int position);
    }

    public interface OnDeleteClick {
        void onDeleteClick(View view, int position);
    }
}
