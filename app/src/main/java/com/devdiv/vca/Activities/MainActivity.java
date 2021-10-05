package com.devdiv.vca.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.devdiv.vca.Fragment.FragmentProfile;
import com.devdiv.vca.Fragment.Fragment_Home;
import com.devdiv.vca.Fragment.Fragment_Predict;
import com.devdiv.vca.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //    WebView web_View;
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.Frame_layout, new Fragment_Home());
        tx.commit();
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

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