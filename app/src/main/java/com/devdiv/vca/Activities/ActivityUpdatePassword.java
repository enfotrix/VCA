package com.devdiv.vca.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.devdiv.vca.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityUpdatePassword extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout edt_re_pass_lay, edt_password_lay;
    AppCompatButton btn_update;
    ProgressBar progressBar;
    private String U_p, U_rP;
    private String documentID;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        // hide toolbar & status bar
        getSupportActionBar().hide();
        firestore = FirebaseFirestore.getInstance();

        iniviews();


        //-------- get global variables
        Bundle bundle = getIntent().getExtras();
        documentID = bundle.getString("documentID");

    }

    private void iniviews() {

        edt_re_pass_lay = findViewById(R.id.edt_re_pass_lay);
        edt_password_lay = findViewById(R.id.edt_password_lay);

        btn_update = findViewById(R.id.btn_update);

        progressBar = findViewById(R.id.progressBa_splash);
        progressBar.setVisibility(View.GONE);

        btn_update.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:

                progressBar.setVisibility(View.VISIBLE);

                if (!validateUserPassword() || !validateUserRe_Password()) {
                    return;
                } else if (!U_p.equals(U_rP)) {
                    edt_password_lay.setError("Please Enter Same Password");
                    edt_re_pass_lay.setError("Please Enter Same Password");
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    Map<String, Object> map = new HashMap<>();
                    map.put("password", edt_password_lay.getEditText().getText().toString().trim());

                    firestore.collection("Users").document(documentID).update(map).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Toast.makeText(ActivityResetPassword.this, ""+txt_newPassword, Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(), "Something Wrong!Try Again", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                break;
            default:
                break;
        }
    }

    public Boolean validateUserPassword() {
        U_p = edt_password_lay.getEditText().getText().toString().trim();

        if (U_p.isEmpty()) {
            edt_password_lay.setError("Field cannot be empty");
            return false;
        } else {
            edt_password_lay.setErrorEnabled(false);
            edt_password_lay.setError(null);
            return true;
        }
    }

    public Boolean validateUserRe_Password() {
        U_rP = edt_re_pass_lay.getEditText().getText().toString().trim();

        if (U_rP.isEmpty()) {
            edt_re_pass_lay.setError("Field cannot be empty");
            return false;
        } else {
            edt_re_pass_lay.setErrorEnabled(false);
            edt_re_pass_lay.setError(null);
            return true;
        }
    }
}