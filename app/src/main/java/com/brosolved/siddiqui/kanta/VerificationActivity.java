package com.brosolved.siddiqui.kanta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.brosolved.siddiqui.kanta.utils.CommonTask;
import com.brosolved.siddiqui.kanta.utils._Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);

        sendVerificationCode(getIntent().getStringExtra(_Constant.INTENT_PHONE_NUMBER));

    }

    private void sendVerificationCode(String number) {
        Log.i("BOO", "sendVerificationCode: "+number);
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 30, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                Log.i("Boo", "onCodeSent: "+s);
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                if (phoneAuthCredential.getSmsCode() != null){
                    editText.setText(phoneAuthCredential.getSmsCode());
                    verifyCode(phoneAuthCredential.getSmsCode());
                }
                Log.i("BOO", "onVerificationCompleted: "+phoneAuthCredential.getSignInMethod()+" "+phoneAuthCredential.getProvider()+" "+phoneAuthCredential.getSmsCode());
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                CommonTask.showToast(VerificationActivity.this, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.i("Boo", "onCodeAutoRetrievalTimeOut: "+s);
            }
        });
    }



    private void verifyCode(String smsCode) {
        signInWithCredential(PhoneAuthProvider.getCredential(verificationId, smsCode));
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.i("BOO", "onComplete: "+task);
                if (task.isSuccessful()){
                    Intent intent = new Intent(VerificationActivity.this, MainActivity.class);
                    intent.putExtra(_Constant.INTENT_PHONE_NUMBER, task.getResult().getUser().getPhoneNumber());
                    intent.putExtra(_Constant.IS_BUYER, getIntent().getIntExtra(_Constant.IS_BUYER, 1));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else
                    CommonTask.showToast(VerificationActivity.this, task.getException().getMessage());
            }
        });
    }
}
