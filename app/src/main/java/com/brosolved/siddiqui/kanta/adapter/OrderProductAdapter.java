package com.brosolved.siddiqui.kanta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.MSProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

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
    private OnRatingChange onRatingChange;

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

        int total = Integer.parseInt(products.get(position).getQuantity() ) * Integer.parseInt(products.get(position).getProduct().getPrice());
        holder.name.setText(products.get(position).getProduct().getName());
        holder.price.setText(products.get(position).getProduct().getPrice()+" BDT");
        holder.quaintly.setText(" x "+ String.valueOf(products.get(position).getQuantity())+" Total: "+total);
        holder.orderDate.setText("Order Date & Time: "+ products.get(position).getCreatedAt());

        if (MainActivity.userInfo.getRememberToken().equals("0")){

            holder.buyerContact.setText(products.get(position).getUserInfo().getMobile());
            holder.buyerName.setText(products.get(position).getUserInfo().getName());
        }

        holder.buyerContact.setVisibility(GONE);
        holder.buyerName.setVisibility(GONE);
        holder.ratingBar.setVisibility(GONE);

        if (products.get(position).getStatus().equals("0") && MainActivity.userInfo.getRememberToken().equals("1")) {
            holder.update.setVisibility(GONE);
        }
        else if (products.get(position).getStatus().equals("0") && MainActivity.userInfo.getRememberToken().equals("0")) {
            holder.update.setText("Accept");
            holder.buyerName.setVisibility(View.VISIBLE);
            holder.buyerContact.setVisibility(View.VISIBLE);
        }
        else if (products.get(position).getStatus().equals("1") && MainActivity.userInfo.getRememberToken().equals("1")) {
            holder.update.setText("Receive");
        }
        else if (products.get(position).getStatus().equals("1") && MainActivity.userInfo.getRememberToken().equals("0")) {
            holder.update.setVisibility(GONE);

            holder.buyerName.setVisibility(View.VISIBLE);
            holder.buyerContact.setVisibility(View.VISIBLE);
        }
        else if (products.get(position).getStatus().equals("2") && MainActivity.userInfo.getRememberToken().equals("1")){

            holder.update.setVisibility(GONE);
            holder.ratingBar.setVisibility(View.VISIBLE);
        }
        else {
            holder.update.setVisibility(GONE);
            holder.buyerName.setVisibility(View.VISIBLE);
            holder.buyerContact.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, price, quaintly, orderDate, buyerName, buyerContact;
        Button update;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quaintly = itemView.findViewById(R.id.quantity);
            update = itemView.findViewById(R.id.theButton);
            orderDate = itemView.findViewById(R.id.orderDate);
            buyerName = itemView.findViewById(R.id.buyerName);
            buyerContact = itemView.findViewById(R.id.buyerContact);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClick.onUpdateClick(v, getAdapterPosition());
                }
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    onRatingChange.OnRatingChange(ratingBar, rating, getAdapterPosition());
                    ratingBar.setRating(rating);
                }
            });
        }

    }

    public void setOnUpdateClickListener(OnUpdateClick onUpdateClick){
        this.onUpdateClick = onUpdateClick;

    }

    public void  setOnRatingChange(OnRatingChange onRatingChange){
        this.onRatingChange = onRatingChange;
    }

    public interface OnUpdateClick {
        void onUpdateClick(View view, int position);
    }

    public interface  OnRatingChange{
        void  OnRatingChange(RatingBar ratingBar, float rating, int position);
    }

}
