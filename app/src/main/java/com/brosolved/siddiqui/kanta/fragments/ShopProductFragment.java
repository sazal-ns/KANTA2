package com.brosolved.siddiqui.kanta.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ViewSwitcher;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.adapter.ProductAdapter;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.models.Status;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.viewModel.ProductViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private ViewSwitcher viewSwitcher;
    private View listView, updateView;

    private Spinner categorySpinner;
    private ImageButton imageButton;
    private Button saveButton;
    private TextInputLayout product, price, desc, quantity;

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
        viewSwitcher = view.findViewById(R.id.viewSwitcher);
        listView = view.findViewById(R.id.recyclerConst);
        updateView = view.findViewById(R.id.updateProductCon);

        categorySpinner = view.findViewById(R.id.categorySpinner);
        imageButton = view.findViewById(R.id.imageButton);
        saveButton = view.findViewById(R.id.addProduct);
        product = view.findViewById(R.id.productName);
        price = view.findViewById(R.id.price);
        desc = view.findViewById(R.id.description);
        quantity = view.findViewById(R.id.quantity);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProductViewModel viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        productAdapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(productAdapter);

        categorySpinner.setVisibility(View.GONE);
        imageButton.setVisibility(View.GONE);

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
            public void onUpdateClick(View view, final int position) {
                if (viewSwitcher.getCurrentView() == listView) {
                    viewSwitcher.showNext();

                    setData(position);

                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Product theProduct = new Product();
                            theProduct.setName(product.getEditText().getText().toString());
                            theProduct.setPrice(price.getEditText().getText().toString());
                            theProduct.setQuantity(Integer.parseInt(quantity.getEditText().getText().toString()));
                            theProduct.setDetails(desc.getEditText().getText().toString());
                            theProduct.setId(productList.get(position).getId());

                            viewModel.update(theProduct).observe(ShopProductFragment.this, new Observer<Product>() {
                                @Override
                                public void onChanged(Product product) {
                                    if (product != null){
                                        theProduct.setImageUrl1(product.getImageUrl1());
                                        productList.remove(position);
                                        productList.add(theProduct);
                                        viewSwitcher.showPrevious();
                                        productAdapter.notifyDataSetChanged();
                                    }
                                    else
                                        CommonTask.showToast(getContext(), "Something Wrong");
                                }
                            });
                        }
                    });
                }
                else
                    viewSwitcher.showPrevious();

            }
        });
        
        productAdapter.setOnDelteClickListener(new ProductAdapter.OnDeleteClick() {
            @Override
            public void onDeleteClick(View view, final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Warning");
                builder.setCancelable(false);
                builder.setMessage("Are you sure to delete this item?");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delete(productList.get(position).getId()).observe(ShopProductFragment.this, new Observer<Status>() {
                            @Override
                            public void onChanged(Status s) {
                                if (s.isStatus()) {
                                    productList.remove(position);
                                    productAdapter.notifyDataSetChanged();
                                }
                                else
                                    CommonTask.showToast(getContext(), "Something Wrong");
                            }
                        });
                    }
                });
                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

    }

    private void setData(int position) {
        product.getEditText().setText(productList.get(position).getName());
        price.getEditText().setText(productList.get(position).getPrice());
        desc.getEditText().setText(productList.get(position).getDetails());
        quantity.getEditText().setText(String.valueOf(productList.get(position).getQuantity()));

        saveButton.setText("Update Product");
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
