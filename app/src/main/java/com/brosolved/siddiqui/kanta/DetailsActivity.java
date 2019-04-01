package com.brosolved.siddiqui.kanta;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brosolved.siddiqui.kanta.models.Product;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private Toolbar toolbar;
    private List<Product> products = new ArrayList<>();

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
        private int pos;

        public PlaceholderFragment() {
        }

        static PlaceholderFragment newInstance(int sectionNumber, String imageUrl1, String name, String price, String details) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(_Constant.PRODUCT_IMAGE, imageUrl1);
            args.putString(_Constant.PRODUCT_NAME, name);
            args.putString(_Constant.PRODUCT_PRICE, price);
            args.putString(_Constant.PRODUCT_DETAILS, details);
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
                    CommonTask.showToast(getContext(), name.getText().toString());
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
            return PlaceholderFragment.newInstance(position, products.get(position).getImageUrl1(), products.get(position).getName(), products.get(position).getPrice(), products.get(position).getDetails());
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
