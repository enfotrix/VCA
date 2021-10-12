package com.devdiv.vca.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.devdiv.vca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ActivityOtp extends AppCompatActivity implements View.OnClickListener {

    TextView text_otpNumber;
    AppCompatButton btn_otp;
    EditText opt1, opt2, opt3, opt4, opt5, opt6;
    String uName, uEmail, uNMBR, country, uPassword, verficationCode, whatTodo;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    TextView shownumber, resendotp, countTimer;
    int time = 60;
    PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // hide toolbar & status bar
        getSupportActionBar().hide();

        iniviews();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        uName = getIntent().getStringExtra("user_name");
        uEmail = getIntent().getStringExtra("user_email");
        uNMBR = getIntent().getStringExtra("user_number");
        uPassword = getIntent().getStringExtra("user_password");
        country = getIntent().getStringExtra("country");

        text_otpNumber.setText(uNMBR);
        sendcodetouser(uNMBR);

        numbertopmove();

    }

    private void sendcodetouser(String uNMBR) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(uNMBR)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            resendotp.setEnabled(true);
            Toast.makeText(ActivityOtp.this, "Failed to send OTP, Please Try Again!", Toast.LENGTH_LONG).show();


            Log.d("TAGCALLED", "onVerificationFailed: FAILED");

            // Show a message and update the UI
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAGCALLED", "onVerificationFailed: SUCCESS");
            Toast.makeText(ActivityOtp.this, "OTP Code sent", Toast.LENGTH_SHORT).show();
            verficationCode = verificationId;
            forceResendingToken = token;


            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    countTimer.setVisibility(View.VISIBLE);
                    resendotp.setVisibility(View.GONE);
                    countTimer.setText("0:" + checkDigit(time));
                    time--;
                }

                public void onFinish() {
                    countTimer.setVisibility(View.GONE);
                    resendotp.setVisibility(View.VISIBLE);
                    resendotp.setEnabled(true);
                }

            }.start();

        }
    };

    private void iniviews() {
        text_otpNumber = findViewById(R.id.text_otpNumber);

        btn_otp = findViewById(R.id.btn_otp);

        opt1 = findViewById(R.id.opt1);
        opt2 = findViewById(R.id.opt2);
        opt3 = findViewById(R.id.opt3);
        opt4 = findViewById(R.id.opt4);
        opt5 = findViewById(R.id.opt5);
        opt6 = findViewById(R.id.opt6);

        resendotp = findViewById(R.id.resendotp);
        countTimer = findViewById(R.id.countTimer);

        progressBar = findViewById(R.id.progressBa_splash);
        progressBar.setVisibility(View.GONE);

        resendotp.setEnabled(false);

        btn_otp.setOnClickListener(this);
        resendotp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_otp:

                String entercodeotp = opt1.getText().toString() +
                        opt2.getText().toString() +
                        opt3.getText().toString() +
                        opt4.getText().toString() +
                        opt5.getText().toString() +
                        opt6.getText().toString();

                if (opt1.getText().toString().trim().isEmpty() && opt2.getText().toString().trim().isEmpty()
                        && opt3.getText().toString().trim().isEmpty() && opt4.getText().toString().trim().isEmpty()
                        && opt5.getText().toString().trim().isEmpty() && opt6.getText().toString().trim().isEmpty()
                        || entercodeotp.length() < 6) {

                    Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                } else {

                    verifycode(entercodeotp);

                }

                break;
            case R.id.resendotp:
                time = 60;
                resendotp.setEnabled(false);

                resendVerificationCode(uNMBR, forceResendingToken);
                break;
            default:
                break;
        }
    }

    private void resendVerificationCode(String uNMBR, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+92" + uNMBR,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                forceResendingToken);
    }

    private void verifycode(String codeEnteredByUser) {

        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationCode, codeEnteredByUser);
        // now last function that will aloww to sign
        signIn(credential);

    }

    private void signIn(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            storedataonFirbase();

                        } else {
                            Toast.makeText(ActivityOtp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void updateOlduserData() {

        Intent intent = new Intent(getApplicationContext(), ActivityUpdatePassword.class);
        intent.putExtra("PhoneNumber", uNMBR);
        startActivity(intent);
        finish();

    }

    private void storedataonFirbase() {

        DocumentReference documentReference = firestore.collection("Users").document();
        Map<String, Object> map = new HashMap<>();
        map.put("name", uName);
        map.put("email", uEmail);
        map.put("country", country);
        map.put("number", uNMBR);
        map.put("password", uPassword);
        map.put("user_picture", "");

        documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                    intent.putExtra("openF2", true);
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
//                    startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
//                    finish();
//                    Toast.makeText(ActivitySignup.this, "Account Created SuccessFully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error!Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void numbertopmove() {

        opt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    opt2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        opt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    opt3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        opt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    opt4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        opt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    opt5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        opt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    opt6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}