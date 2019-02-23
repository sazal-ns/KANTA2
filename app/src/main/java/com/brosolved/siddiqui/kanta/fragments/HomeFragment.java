package com.brosolved.siddiqui.kanta.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.adapter.CategoryAdapter;
import com.brosolved.siddiqui.kanta.adapter.ProductsAdapter;
import com.brosolved.siddiqui.kanta.models.Categories;
import com.brosolved.siddiqui.kanta.models.Products;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.brosolved.siddiqui.kanta.viewModel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private static final String TAG = "HomeFragment";

    private CategoryAdapter msCategoryAdapter;
    private ProductsAdapter msProductsAdapter;

    private LottieAnimationView msLottieAnimationView;
    private RecyclerView msProductRecyclerView;
    private SwipeRefreshLayout msRefreshLayout;
    private RecyclerView msRecycler;
    private TextView msTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        msLottieAnimationView = view.findViewById(R.id.lottie);
        msProductRecyclerView = view.findViewById(R.id.recyclerViewProduct);
        msRefreshLayout = view.findViewById(R.id.refresh);
        msRecycler = view.findViewById(R.id.recyclerViewCategories);
        msTextView = view.findViewById(R.id.textView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            msProductRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        else
            msProductRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        msProductRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        msRecycler.setLayoutManager(manager);

        loadCategoryData();

        loadProducts();

        msRefreshLayout.setOnRefreshListener(this);
        msRefreshLayout.setEnabled(false);

    }

    private void loadCategoryData() {
        mViewModel.getCategories().observe(this, new Observer<Categories>() {
            @Override
            public void onChanged(Categories categories) {
                if (categories != null) {
                    msCategoryAdapter = new CategoryAdapter(getContext(), categories.getData());
                    msRecycler.setAdapter(msCategoryAdapter);
                    msCategoryAdapter.notifyDataSetChanged();
                } else CommonTask.showToast(getContext(), _Constant.ERROR_TOAST);
                msTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadProducts() {
        mViewModel.getProducts().observe(this, new Observer<Products>() {
            @Override
            public void onChanged(Products products) {
                if (products != null) {
                    msProductsAdapter = new ProductsAdapter(getContext(), products.getData());
                    msProductRecyclerView.setAdapter(msProductsAdapter);
                    msLottieAnimationView.setVisibility(View.GONE);
                    msProductsAdapter.notifyDataSetChanged();
                } else CommonTask.showToast(getContext(), _Constant.ERROR_TOAST);

                if (msRefreshLayout.isRefreshing()) {
                    msRefreshLayout.setRefreshing(false);
                    msRefreshLayout.setEnabled(true);
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            msProductRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            msProductRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }

    }

    @Override
    public void onRefresh() {
        loadProducts();
    }

}
