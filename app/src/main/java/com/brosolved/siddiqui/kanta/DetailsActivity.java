package com.brosolved.siddiqui.kanta;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.models.CartProduct;
import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.brosolved.siddiqui.kanta.viewModel.DetailsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private Toolbar toolbar;
    private List<Product> products = new ArrayList<>();

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        int position = getIntent().getIntExtra(_Constant.PRODUCT_POSITION, 0);
        products = getIntent().getParcelableArrayListExtra(_Constant.PRODUCT_DATA);



        setTitle(products.get(position).getName());
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), products);

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(position);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(products.get(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
       private String imageUrl, name, price, details;
        private int pos, product_id, quantity;

        public PlaceholderFragment() {
        }

        static PlaceholderFragment newInstance(int sectionNumber, String imageUrl1, String name, String price, String details, int product_id) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(_Constant.PRODUCT_IMAGE, imageUrl1);
            args.putString(_Constant.PRODUCT_NAME, name);
            args.putString(_Constant.PRODUCT_PRICE, price);
            args.putString(_Constant.PRODUCT_DETAILS, details);
            args.putInt("Product_id", product_id);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void setArguments(@Nullable Bundle args) {
            super.setArguments(args);
            this.imageUrl = args.getString(_Constant.PRODUCT_IMAGE);
            this.name = args.getString(_Constant.PRODUCT_NAME);
            this.price = args.getString(_Constant.PRODUCT_PRICE);
            this.details = args.getString(_Constant.PRODUCT_DETAILS);
            this.pos = args.getInt(ARG_SECTION_NUMBER);
            this.product_id = args.getInt("Product_id");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_details, container, false);

             ImageView image =rootView.findViewById(R.id.mainImageView);
             final TextView name = rootView.findViewById(R.id.productNameTextView);
             TextView price = rootView.findViewById(R.id.priceTextView);
             ImageButton number = rootView.findViewById(R.id.mobileTextView);
             TextView details = rootView.findViewById(R.id.detailsTextView);

            if (Integer.parseInt(MainActivity.userInfo.getRememberToken())==0){
                number.setVisibility(View.GONE);
            }

            final DetailsViewModel   viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

            Glide.with(getActivity())
                    .asBitmap()
                    .load(this.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);
            name.setText(this.name);
            price.setText("Price: "+this.price+" BDT");
            details.setText(this.details);

            number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(getContext());
                    View promptsView = li.inflate(R.layout.prompts, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog, int id) {
                                            // get user input and set it to result
                                            // edit text
                                            //result.setText(userInput.getText());
                                            CommonTask.dialogShow(getContext(),"Please wait........");
                                            dialog.dismiss();
                                            dialog.cancel();
                                            viewModel.addToCart(MainActivity.userInfo.getId(), product_id, Integer.parseInt(userInput.getText().toString())).observe(getViewLifecycleOwner(), new Observer<CartProduct>() {
                                                @Override
                                                public void onChanged(CartProduct cartProduct) {

                                                    CommonTask.dialogDistroy();
                                                    if (cartProduct.getError() == null)
                                                        CommonTask.dialogShow(getContext(), "Thanks for shop with us");
                                                    else
                                                        CommonTask.dialogShow(getContext(),cartProduct.getError());

                                                }
                                            });
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                   //
                }
            });

            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<Product> products;
        SectionsPagerAdapter(FragmentManager fm, List<Product> products) {
            super(fm);
            this.products = products;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position, products.get(position).getImageUrl1(), products.get(position).getName(), products.get(position).getPrice(), products.get(position).getDetails(), products.get(position).getId());
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return products.get(position).getName();
        }
    }
}
