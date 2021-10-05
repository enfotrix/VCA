package com.devdiv.vca.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.devdiv.vca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

public class ActivityForgetPassword extends AppCompatActivity implements View.OnClickListener {

    CountryCodePicker mCountryCodePicker;
    AppCompatButton btn_verifycode;
    TextInputLayout edt_phone_lay, edt_email_lay;
    TextInputEditText edt_phone, edt_emal;
    private String completenumber;
    ProgressBar progressBar;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        // hide toolbar & status bar
        getSupportActionBar().hide();
        firestore = FirebaseFirestore.getInstance();
        iniviews();
    }

    private void iniviews() {

        mCountryCodePicker = findViewById(R.id.ccp);

        btn_verifycode = findViewById(R.id.btn_verifycode);

        edt_phone_lay = findViewById(R.id.edt_phone_lay);
        edt_phone = findViewById(R.id.edt_phone);

        edt_email_lay = findViewById(R.id.edt_email_lay);
        edt_emal = findViewById(R.id.edt_emal);

        progressBar = findViewById(R.id.progressBa_splash);
        progressBar.setVisibility(View.GONE);

        btn_verifycode.setOnClickListener(this);
    }

    public Boolean validateUserNumber() {
        String U_NMBR = edt_phone_lay.getEditText().getText().toString().trim();
        if (U_NMBR.isEmpty()) {
            edt_phone_lay.setError("Field cannot be empty");
            return false;
        } else {
            edt_phone_lay.setErrorEnabled(false);
            edt_phone_lay.setError(null);
            return true;
        }
    }

    public Boolean validateUserEmail() {
        String U_EML = edt_email_lay.getEditText().getText().toString().trim();
        if (U_EML.isEmpty()) {
            edt_email_lay.setError("Field cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(U_EML).matches()) {
            edt_email_lay.setError("info@gmail.com");
            return false;
        } else {
            edt_email_lay.setErrorEnabled(false);
            edt_email_lay.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verifycode:

                progressBar.setVisibility(View.VISIBLE);
                String u_EML = edt_email_lay.getEditText().getText().toString().trim();

                if (!validateUserNumber() || !validateUserEmail()) {
                    return;
                } else {

                    mCountryCodePicker.registerCarrierNumberEditText(edt_phone);
                    completenumber = mCountryCodePicker.getFullNumberWithPlus().replace("", "").trim();

                    firestore.collection("Users").whereEqualTo("number", completenumber).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getString("email").equals(u_EML)) {

                                            Intent intent = new Intent(getApplicationContext(), ActivityUpdatePassword.class);
                                            intent.putExtra("documentID", document.getId());
                                            startActivity(intent);
                                            finish();

                                        } else
                                            Toast.makeText(getApplicationContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                } else
                                    Toast.makeText(getApplicationContext(), "Mobile Number Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                break;
            default:
                break;
        }
    }
}