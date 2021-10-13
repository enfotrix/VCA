package com.devdiv.vca.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.devdiv.vca.Fragment.FragmentProfile;
import com.devdiv.vca.Fragment.Fragment_Home;
import com.devdiv.vca.Fragment.Fragment_Predict;
import com.devdiv.vca.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //    WebView web_View;
    BottomNavigationView bottom_navigation;

    InputStream inputStream;

    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.Frame_layout, new Fragment_Home());
        tx.commit();
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        inputStream = getResources().openRawResource(R.raw.t20_data_);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {

            String csvfiel;
            while ((csvfiel = bufferedReader.readLine()) != null) {

                data = csvfiel.split(",");

               // Log.d("TAGgg", "onCreate: " + data[0] + "," + data[1]);
                /*Toast.makeText(getApplicationContext(), "" + data[0] + "," + data[1]
                                + "," + data[3]
                                + "," + data[5]
                                + "," + data[6]
                                + "," + data[9]
                                + "," + data[10]
                                + "," + data[11]
                                

                        , Toast.LENGTH_SHORT).show();*/

            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        bottom_navigation = findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                //itemView.removeView(view);
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_layout, new Fragment_Home()).addToBackStack(null).commit();
                break;
            case R.id.menu_dasboard:
                //itemView.removeView(view);
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_layout, new FragmentProfile()).addToBackStack(null).commit();

                break;
            case R.id.menu_predict:
                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_layout, new Fragment_Predict()).commit();

                break;
            default:
                break;
        }
        return true;
    }
}