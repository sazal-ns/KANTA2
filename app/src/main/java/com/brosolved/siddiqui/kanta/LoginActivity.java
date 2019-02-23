package com.brosolved.siddiqui.kanta;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.rilixtech.CountryCodePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private CountryCodePicker countryCodePicker;
    private AppCompatEditText appCompatEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        countryCodePicker = findViewById(R.id.ccp);
        appCompatEditText = findViewById(R.id.phone_number_edt);

        countryCodePicker.registerPhoneNumberTextView(appCompatEditText);



        Button button = findViewById(R.id.buttonContinue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d = getResources().getDrawable(R.drawable.ic_error_black_24dp);
                d.setBounds(0,0, d.getIntrinsicWidth(), d.getIntrinsicHeight());

                if (!CommonTask.checkInput(countryCodePicker.getFullNumberWithPlus(), 14)){
                    appCompatEditText.setError("Invalid Number", d);
                    appCompatEditText.requestFocus();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
                intent.putExtra(_Constant.INTENT_PHONE_NUMBER, countryCodePicker.getFullNumberWithPlus());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.i(TAG, "onStart: "+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(_Constant.INTENT_PHONE_NUMBER, FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
