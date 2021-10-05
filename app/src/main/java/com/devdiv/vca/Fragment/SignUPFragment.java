package com.devdiv.vca.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.devdiv.vca.Activities.ActivityOtp;
import com.devdiv.vca.Activities.MainActivity;
import com.devdiv.vca.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class SignUPFragment extends Fragment implements View.OnClickListener {

    TextInputLayout edt_name_lay, edt_re_pass_lay, edt_password_lay, edt_phone_lay, edt_email_lay;
    AppCompatButton btn_signup;
    TextView text_signlater;
    private String U_p, U_rP;
    CountryCodePicker mCountryCodePicker;
    TextInputEditText edt_phone;
    private String completenumber;
    private AutoCompleteTextView autoCompletetxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_u_p, container, false);
        iniViews(view);

        String[] countryarray = {"Pakistan", "India", "England", "Australia", "New Zealand", "South Africa"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_session, countryarray);
//        autoCompletetxt.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompletetxt.setAdapter(arrayAdapter);

        return view;
    }

    private void iniViews(View view) {
        edt_name_lay = view.findViewById(R.id.edt_name_lay);
        edt_re_pass_lay = view.findViewById(R.id.edt_re_pass_lay);
        edt_password_lay = view.findViewById(R.id.edt_password_lay);
        edt_phone_lay = view.findViewById(R.id.edt_phone_lay);
        edt_email_lay = view.findViewById(R.id.edt_email_lay);
        btn_signup = view.findViewById(R.id.btn_signup);
        text_signlater = view.findViewById(R.id.text_signlater);
        mCountryCodePicker = view.findViewById(R.id.ccp);
        edt_phone = view.findViewById(R.id.edt_phone);
        autoCompletetxt = view.findViewById(R.id.autoCompletetxt);

        btn_signup.setOnClickListener(this);
        text_signlater.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:

                if (!validateUserName() || !validateUserEmail() ||
                        !validateUserNumber() || !validateUserPassword() || !validateUserRe_Password()) {
                    return;
                } else if (!U_p.equals(U_rP)) {
                    edt_password_lay.setError("Please Enter Same Password");
                    edt_re_pass_lay.setError("Please Enter Same Password");
                } else {
                    mCountryCodePicker.registerCarrierNumberEditText(edt_phone);
                    completenumber = mCountryCodePicker.getFullNumberWithPlus().replace("", "").trim();

                    movetoOtpScreen();
//                    Toast.makeText(getContext(), "" + completenumber, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.text_signlater:
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                break;
            default:
                break;
        }

    }

    private void movetoOtpScreen() {
        String U_N = edt_name_lay.getEditText().getText().toString().trim();
        String U_EML = edt_email_lay.getEditText().getText().toString().trim();
        U_p = edt_password_lay.getEditText().getText().toString().trim();
        String U_NMBR = edt_phone_lay.getEditText().getText().toString().trim();

        Intent otpscreen = new Intent(getContext(), ActivityOtp.class);
        otpscreen.putExtra("user_name", U_N);
        otpscreen.putExtra("user_email", U_EML);
        otpscreen.putExtra("user_number", completenumber);
        otpscreen.putExtra("user_password", U_p);
        otpscreen.putExtra("country", autoCompletetxt.getText().toString());
        startActivity(otpscreen);
        getActivity().finish();


    }

    public Boolean validateUserName() {
        String U_N = edt_name_lay.getEditText().getText().toString().trim();
        if (U_N.isEmpty()) {
            edt_name_lay.setError("Field cannot be empty");
            return false;
        } else if (U_N.length() > 20) {
            edt_name_lay.setError("User Name Too Long");
            return false;
        } else {
            edt_name_lay.setErrorEnabled(false);
            edt_name_lay.setError(null);
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