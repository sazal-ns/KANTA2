package com.brosolved.siddiqui.kanta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.MSProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;

/*
 * com.brosolved.siddiqui.kanta.adapter is created by Noor Nabiul Alam Siddiqui on 2/16/2019
 *
 * BroSolved (c) 2019.
 */
public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {

    private static final String TAG = "CategoryAdapter";

    private Context context;
    private List<MSProduct> products;

    private OnUpdateClick onUpdateClick;

    public OrderProductAdapter(Context context, List<MSProduct> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Glide.with(context)
                .asBitmap()
                .load("http://dev.brosolved.com/kanta/pic/product/"+products.get(position).getProduct().getImageUrl1())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView);

        holder.name.setText(products.get(position).getProduct().getName());
        holder.price.setText(products.get(position).getProduct().getPrice()+" BDT");
        holder.quaintly.setText(" x "+ String.valueOf(products.get(position).getQuantity()));

        if (products.get(position).getStatus().equals("0") && MainActivity.userInfo.getRememberToken().equals("1"))
            holder.update.setVisibility(GONE);
        else if (products.get(position).getStatus().equals("0") && MainActivity.userInfo.getRememberToken().equals("0"))
            holder.update.setText("Accept");
        else if (products.get(position).getStatus().equals("1") && MainActivity.userInfo.getRememberToken().equals("1"))
            holder.update.setText("Receive");
        else if (products.get(position).getStatus().equals("1") && MainActivity.userInfo.getRememberToken().equals("0"))
            holder.update.setVisibility(GONE);
        else
            holder.update.setVisibility(GONE);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, quaintly;
        Button update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quaintly = itemView.findViewById(R.id.quantity);
            update = itemView.findViewById(R.id.theButton);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClick.onUpdateClick(v, getAdapterPosition());
                }
            });
        }

    }

    public void setOnUpdateClickListener(OnUpdateClick onUpdateClick){
        this.onUpdateClick = onUpdateClick;

    }

    public interface OnUpdateClick {
        void onUpdateClick(View view, int position);
    }

}
