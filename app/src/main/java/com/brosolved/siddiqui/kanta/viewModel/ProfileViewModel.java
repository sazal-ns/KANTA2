package com.brosolved.siddiqui.kanta.viewModel;

import com.brosolved.siddiqui.kanta.models.UserInfo;
import com.brosolved.siddiqui.kanta.remote.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    public LiveData<UserInfo> updateUserInfo(UserInfo userInfo){
        return Repository.getInstance().updateUserInfo(userInfo);
    }
}
