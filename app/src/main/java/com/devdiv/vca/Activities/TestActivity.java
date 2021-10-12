package com.devdiv.vca.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devdiv.vca.R;
import com.devdiv.vca.predictor;

public class TestActivity extends AppCompatActivity {

    private com.devdiv.vca.predictor predic;

    EditText a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btn= findViewById(R.id.c);
        a=findViewById(R.id.a);
        b=findViewById(R.id.b);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predic= new predictor(a.getText().toString(),b.getText().toString(),"","");
                boolean check=predic.calculateXI();
                if(check){
                    Toast.makeText(TestActivity.this,
                            predic.getnALL()+" " +
                            predic.getnBAT()+" " +
                            predic.getnSPN()+" " +
                            predic.getnBWL()


                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}