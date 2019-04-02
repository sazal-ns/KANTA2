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
import com.brosolved.siddiqui.kanta.adapter.OrderProductAdapter;
import com.brosolved.siddiqui.kanta.models.CartProduct;
import com.brosolved.siddiqui.kanta.models.MSProduct;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.viewModel.DetailsViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OrderFragment extends Fragment {

    private static final String TAG = "OrderFragment";

    private static final String ARG_PARAM1 = "param1";


    private int mParam1;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private List<MSProduct> msProducts= new ArrayList<>();
    private OrderProductAdapter adapter;

    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(int param1) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order, container, false);

        recyclerView = view.findViewById(R.id.orderRecycler);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DetailsViewModel viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new OrderProductAdapter(getContext(),msProducts);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        if (Integer.parseInt(MainActivity.userInfo.getRememberToken()) == 0)
        viewModel.orderStatus(MainActivity.userInfo.getId()).observe(getViewLifecycleOwner(), new Observer<List<MSProduct>>() {
            @Override
            public void onChanged(List<MSProduct> cartProduct) {
                if (cartProduct != null)
                {
                   // msProducts.addAll(cartProduct.getData());
                    Log.i(TAG, "onChanged: "+mParam1);

                    msProducts.clear();
                    for (MSProduct msproduct :
                            cartProduct) {
                        if (Integer.parseInt(msproduct.getStatus()) == mParam1)
                            msProducts.add(msproduct);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        else
            viewModel.orderBuyer(MainActivity.userInfo.getId()).observe(getViewLifecycleOwner(), new Observer<CartProduct>() {
                @Override
                public void onChanged(CartProduct cartProduct) {
                    msProducts.clear();
                    for (MSProduct msproduct :
                            cartProduct.getData()) {
                        if (Integer.parseInt(msproduct.getStatus()) == mParam1)
                            msProducts.add(msproduct);
                    }
                    adapter.notifyDataSetChanged();
                }
            });

        adapter.setOnUpdateClickListener(new OrderProductAdapter.OnUpdateClick() {
            @Override
            public void onUpdateClick(View view, final int position) {
                int a=0;
                if (Integer.parseInt(MainActivity.userInfo.getRememberToken())==1)
                    a=2;
                else
                    a=1;

                viewModel.updateStatus(msProducts.get(position).getId(), a).observe(getViewLifecycleOwner(), new Observer<CartProduct>() {
                    @Override
                    public void onChanged(CartProduct cartProduct) {
                        if (cartProduct != null) {
                            CommonTask.dialogShow(getContext(), "Status Update Done");
                           msProducts.remove(position);
                           adapter.notifyDataSetChanged();
                        }
                        else
                            CommonTask.showToast(getContext(), "Something is wrong");

                    }
                });
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
