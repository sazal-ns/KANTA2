package com.brosolved.siddiqui.kanta.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.adapter.ProductAdapter;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.viewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShopProductFragment extends Fragment {

    private static final String TAG = "ShopProductFragment";
    
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;

    public ShopProductFragment() {
        // Required empty public constructor
    }


    public static ShopProductFragment newInstance() {

        return new ShopProductFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_product, container, false);

        recyclerView = view.findViewById(R.id.productRecycler);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);

        viewModel.getProducts(MainActivity.userInfo.getId()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null) {
                    productList.addAll(products);
                    productAdapter.notifyDataSetChanged();
                }
            }
        });

        productAdapter.setOnUpdateClickListener(new ProductAdapter.OnUpdateClick() {
            @Override
            public void onUpdateClick(View view, int position) {
                Log.d(TAG, "onUpdateClick: ");
            }
        });
        
        productAdapter.setOnDelteClickListener(new ProductAdapter.OnDeleteClick() {
            @Override
            public void onDeleteClick(View view, int position) {
                Log.d(TAG, "onDeleteClick: ");
            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
