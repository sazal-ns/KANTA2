package com.brosolved.siddiqui.kanta.viewModel;

import com.brosolved.siddiqui.kanta.models.User;
import com.brosolved.siddiqui.kanta.remote.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/*
 * com.brosolved.siddiqui.kanta.viewModel is created by Noor Nabiul Alam Siddiqui on 2/23/2019
 *
 * BroSolved (c) 2019.
 */
public class MainViewModel extends ViewModel {



    public LiveData<User> getUserInfo(String number){

        return Repository.getInstance().loadUser(number);

    }

}
