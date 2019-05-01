package com.brosolved.siddiqui.kanta.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.adapter.AccountAdapter;
import com.brosolved.siddiqui.kanta.models.Account;
import com.brosolved.siddiqui.kanta.models.MSProduct;
import com.brosolved.siddiqui.kanta.viewModel.DetailsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private AccountAdapter listAdapter;

    private List<Account> accounts = new ArrayList<>();
    private int[] product= new int[12];
    private int[] amount = new int[12];

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        listView = view.findViewById(R.id.list);

        Arrays.fill(amount, 0);
        Arrays.fill(product, 0);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Add Header View
        View headerView = inflater.inflate(R.layout.row_head, null, false);
        listView.addHeaderView(headerView);

        listAdapter = new AccountAdapter(getContext(), accounts);
        listView.setAdapter(listAdapter);

        final DetailsViewModel viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        viewModel.orderStatus(MainActivity.userInfo.getId()).observe(getViewLifecycleOwner(), new Observer<List<MSProduct>>() {
            @Override
            public void onChanged(List<MSProduct> cartProduct) {
                if (cartProduct != null)
                {
                    //Log.d("dadtaa", "onChanged: "+ cartProduct);
                    for (MSProduct msproduct :
                            cartProduct) {
                        if (Integer.parseInt(msproduct.getStatus()) == 2){
                            //Log.d("substring", "onChanged: "+msproduct.getCreatedAt().substring(5,7));
                            product[Integer.parseInt(msproduct.getCreatedAt().substring(5,7))] += Integer.parseInt(msproduct.getQuantity());
                            amount[Integer.parseInt(msproduct.getCreatedAt().substring(5,7))] += (Integer.parseInt(msproduct.getQuantity()) * Integer.parseInt(msproduct.getProduct().getPrice()));
                        }
                    }

                    loadData();
                }
            }
        });
    }

    private void loadData() {
        for (int i =0; i< product.length; i++){
            Account account = new Account();
            if (product[i] > 0){

                if (i==3){
                    account.setMonth("March");
                }else if (i==4)
                    account.setMonth("April");
                else if (i == 5)
                    account.setMonth("May");

                account.setAmount(amount[i]);
                account.setProduct(product[i]);
                accounts.add(account);
            }



            listAdapter.notifyDataSetChanged();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
