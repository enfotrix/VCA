package com.devdiv.vca.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.devdiv.vca.Activities.ActivityForgetPassword;
import com.devdiv.vca.Activities.MainActivity;
import com.devdiv.vca.R;
import com.devdiv.vca.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginFragment extends Fragment implements View.OnClickListener {

    TextInputLayout edt_password_lay, edt_email_lay;
    AppCompatButton btn_login;
    TextView text_loginlater, text_forgetpassword;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private Utils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        iniviews(view);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(getContext());
        reference = FirebaseDatabase.getInstance().getReference("Users");

        return view;
    }

    private void iniviews(View view) {

        edt_password_lay = view.findViewById(R.id.edt_password_lay);
        edt_email_lay = view.findViewById(R.id.edt_email_lay);
        btn_login = view.findViewById(R.id.btn_login);
        text_loginlater = view.findViewById(R.id.text_loginlater);
        text_forgetpassword = view.findViewById(R.id.text_forgetpassword);

        btn_login.setOnClickListener(this);
        text_loginlater.setOnClickListener(this);
        text_forgetpassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                if (!validateUserEmail() || !validateUserPassword()) {
                    return;
                } else {
                    String u_p = edt_password_lay.getEditText().getText().toString().trim();
                    String u_EML = edt_email_lay.getEditText().getText().toString().trim();

                    firestore.collection("Users").whereEqualTo("email", u_EML)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (!task.getResult().isEmpty()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (document.getString("password").equals(u_p)) {
                                                    utils.putToken(document.getId());

                                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                                    intent.putExtra("documentID", document.getId());
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                } else
                                                    edt_password_lay.setError("Please Enter Correct Password");

                                            }
                                        } else
                                            edt_email_lay.setError("Please Enter Correct Email");
                                    }
                                }
                            });
                }
                break;
            case R.id.text_loginlater:

                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

                break;
            case R.id.text_forgetpassword:

                startActivity(new Intent(getContext(), ActivityForgetPassword.class));
                getActivity().finish();

                break;
            default:
                break;
        }

    }

    public Boolean validateUserPassword() {
        String U_p = edt_password_lay.getEditText().getText().toString().trim();

        if (U_p.isEmpty()) {
            edt_password_lay.setError("Field cannot be empty");
            return false;
        } else {
            edt_password_lay.setErrorEnabled(false);
            edt_password_lay.setError(null);
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
}