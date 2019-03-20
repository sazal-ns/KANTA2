package com.brosolved.siddiqui.kanta.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.brosolved.siddiqui.kanta.MainActivity;
import com.brosolved.siddiqui.kanta.R;
import com.brosolved.siddiqui.kanta.models.UserInfo;
import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.viewModel.ProfileViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    private TextView name, number, shopName, shopAddress;
    private EditText nameEdit, numberEdit, shopNameEdit, shopAddressEdit;
    private Button button, button2;
    private View viewProfile, editProfile;

    private UserInfo userInfo;
    private ViewSwitcher profileViewSwitcher;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.profile_fragment, container, false);

        profileViewSwitcher = view.findViewById(R.id.profileViewSwitcher);

        name = view.findViewById(R.id.nameTextView);
        nameEdit = view.findViewById(R.id.nameTextView2);
        number = view.findViewById(R.id.contactTextView);
        numberEdit = view.findViewById(R.id.contactTextView2);
        shopName = view.findViewById(R.id.shopTextView);
        shopNameEdit = view.findViewById(R.id.shopTextView2);
        shopAddress = view.findViewById(R.id.addressTextView);
        shopAddressEdit = view.findViewById(R.id.addressTextView2);

        button = view.findViewById(R.id.editButton);
        button2 = view.findViewById(R.id.editButton2);

        viewProfile = view.findViewById(R.id.viewProfileLayout);
        editProfile = view.findViewById(R.id.editProfileLayout);

        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        userInfo = MainActivity.userInfo;

        name.setText(userInfo.getName());
        number.setText(userInfo.getMobile());
        shopName.setText(userInfo.getShopName());
        shopAddress.setText(userInfo.getAddress());

        nameEdit.setText(userInfo.getName());
        numberEdit.setText(userInfo.getMobile());
        shopNameEdit.setText(userInfo.getShopName());
        shopAddressEdit.setText(userInfo.getAddress());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileViewSwitcher.getCurrentView() == viewProfile)
                    profileViewSwitcher.showNext();
                else profileViewSwitcher.showPrevious();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (profileViewSwitcher.getCurrentView() == editProfile){
                   if (CommonTask.checkInput(nameEdit.getText().toString(), nameEdit) && CommonTask.checkInput(numberEdit.getText().toString(), numberEdit) && CommonTask.checkInput(shopNameEdit.getText().toString(), shopNameEdit) && CommonTask.checkInput(shopAddressEdit.getText().toString(), shopAddressEdit)){

                       UserInfo updateInfo = new UserInfo();
                       updateInfo.setId(userInfo.getId());
                       updateInfo.setName(nameEdit.getText().toString().trim());
                       updateInfo.setMobile(numberEdit.getText().toString().trim());
                       updateInfo.setShopName(shopNameEdit.getText().toString().trim());
                       updateInfo.setAddress(shopAddressEdit.getText().toString().trim());

                       mViewModel.updateUserInfo(updateInfo).observe(ProfileFragment.this, new Observer<UserInfo>() {
                           @Override
                           public void onChanged(UserInfo userInfo) {
                               if (userInfo != null){
                                   name.setText(userInfo.getName());
                                   number.setText(userInfo.getMobile());
                                   shopName.setText(userInfo.getShopName());
                                   shopAddress.setText(userInfo.getAddress());
                                   profileViewSwitcher.showPrevious();
                               }else CommonTask.showToast(getContext(), "Something Wrong");
                           }
                       });
                   }

               }else profileViewSwitcher.showNext();
            }
        });
    }

}
