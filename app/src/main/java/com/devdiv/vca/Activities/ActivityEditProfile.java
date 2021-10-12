package com.devdiv.vca.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.devdiv.vca.R;
import com.devdiv.vca.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityEditProfile extends AppCompatActivity {

    TextView oldname, oldpassword;
    TextInputLayout edit_LayName, editnewpassword;
    AppCompatButton btn_edit;
    private FirebaseFirestore db;
    private Utils utils;
    TextInputEditText edtt_reg, oldpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_LayName = findViewById(R.id.edit_LayName);
        editnewpassword = findViewById(R.id.editnewpassword);
        edtt_reg = findViewById(R.id.edtt_reg);
        oldpass = findViewById(R.id.oldpass);
        btn_edit = findViewById(R.id.btn_edit);

        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty()) {
                    editprofile(edit_LayName.getEditText().getText().toString().trim(), editnewpassword.getEditText().getText().toString().trim());
                }
            }
        });

        getData();

    }

    private void editprofile(String name, String password) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", edit_LayName.getEditText().getText().toString().trim());
        map.put("password", editnewpassword.getEditText().getText().toString().trim());
        db.collection("Users").document(utils.getToken()).update(map).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(ActivityResetPassword.this, ""+txt_newPassword, Toast.LENGTH_SHORT).show();

                        Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(ActivityResetPassword.this, ActivityLogin.class);
//                        startActivity(intent);
//                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Something Wrong!Try Again", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private boolean checkEmpty() {
        Boolean isEmpty = false;
        if (edit_LayName.getEditText().getText().toString().trim().isEmpty())
            edit_LayName.setError("Please Enter Name");
        else if (editnewpassword.getEditText().getText().toString().trim().isEmpty())
            editnewpassword.setError("Please Enter Password");
        else isEmpty = true;
        return isEmpty;
    }

    public void getData() {

        db.collection("Users").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        String name = document.getString("name");

                        edtt_reg.setText(name);
//                        oldpass.setText(password);
//                        usernumber.setText(phone);

//                        Glide.with(image)
//                                .load(photo)
//                                .fitCenter().into(image);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}