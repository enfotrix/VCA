package com.devdiv.vca.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.devdiv.vca.Adapters.Adapter_PredictedPlayer;
import com.devdiv.vca.LottieDialog;
import com.devdiv.vca.Model.Model_PredictedPlayer;
import com.devdiv.vca.R;

import java.util.ArrayList;

public class ActivityPredictedPlayer extends AppCompatActivity {

    ArrayList<Model_PredictedPlayer> modelPredictedPlayerArrayList = new ArrayList<>();
    RecyclerView recyc_predictedplayer;
    Adapter_PredictedPlayer adapterPredictedPlayer;
    TextView txt_totalsquad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicted_player);

        getSupportActionBar().hide();

        txt_totalsquad = findViewById(R.id.txt_totalsquad);
        txt_totalsquad.setText("12");

        recyc_predictedplayer = findViewById(R.id.list_predictedplayer);
        recyc_predictedplayer.setHasFixedSize(true);
        recyc_predictedplayer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getmysquad();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getmysquad();
                pullToRefresh.setRefreshing(false);
            }
        });


        String w=getIntent().getStringExtra("weather");
        String p=getIntent().getStringExtra("pitch");





    }



    private void getmysquad() {

        final LottieDialog lottieDialog = new LottieDialog(this);
        lottieDialog.show();

        modelPredictedPlayerArrayList.clear();

        Model_PredictedPlayer player1 = new Model_PredictedPlayer();
        player1.setCentury("20");
        player1.setPlayerage("22");
        player1.setFifyt("10");
        player1.setHighscore("120");
        player1.setPlayername("Babar Azam");
        player1.setMatchplay("197");
        player1.setStriekrate("120");
        player1.setWickets("0");
        player1.setTotalruns("8000");
        player1.setEconomy("0");
        modelPredictedPlayerArrayList.add(player1);

        Model_PredictedPlayer player2 = new Model_PredictedPlayer();
        player2.setCentury("0");
        player2.setPlayerage("25");
        player2.setFifyt("16");
        player2.setHighscore("90");
        player2.setPlayername("Shadab Khan");
        player2.setMatchplay("110");
        player2.setStriekrate("78.96");
        player2.setWickets("134");
        player2.setTotalruns("970");
        player1.setEconomy("2.0");
        modelPredictedPlayerArrayList.add(player2);

        Model_PredictedPlayer player3 = new Model_PredictedPlayer();
        player3.setCentury("10");
        player3.setPlayerage("24");
        player3.setFifyt("10");
        player3.setHighscore("120");
        player3.setPlayername("Asif Ali");
        player3.setMatchplay("50");
        player3.setStriekrate("120");
        player3.setWickets("0");
        player3.setTotalruns("800");
        player1.setEconomy("0");
        modelPredictedPlayerArrayList.add(player3);

        Model_PredictedPlayer player4 = new Model_PredictedPlayer();
        player4.setCentury("0");
        player4.setPlayerage("23");
        player4.setFifyt("2");
        player4.setHighscore("60");
        player4.setPlayername("Azam Khan");
        player4.setMatchplay("5");
        player4.setStriekrate("120");
        player4.setWickets("0");
        player1.setEconomy("0");
        player3.setTotalruns("800");
        modelPredictedPlayerArrayList.add(player4);

        Model_PredictedPlayer player5 = new Model_PredictedPlayer();
        player5.setCentury("0");
        player5.setPlayerage("22");
        player5.setFifyt("0");
        player5.setHighscore("40");
        player5.setPlayername("Haris Rauf");
        player5.setMatchplay("30");
        player5.setStriekrate("120");
        player5.setWickets("40");
        player1.setEconomy("3.0");
        player3.setTotalruns("900");
        modelPredictedPlayerArrayList.add(player5);

        Model_PredictedPlayer player6 = new Model_PredictedPlayer();
        player6.setCentury("0");
        player6.setPlayerage("22");
        player6.setFifyt("0");
        player6.setHighscore("40");
        player6.setPlayername("Hasan Ali");
        player6.setMatchplay("120");
        player6.setStriekrate("120");
        player6.setWickets("40");
        player6.setEconomy("2.4");
        player6.setTotalruns("1900");
        modelPredictedPlayerArrayList.add(player6);

        Model_PredictedPlayer player7 = new Model_PredictedPlayer();
        player7.setCentury("0");
        player7.setPlayerage("26");
        player7.setFifyt("0");
        player7.setHighscore("40");
        player7.setPlayername("Imad Wasim");
        player7.setMatchplay("100");
        player7.setStriekrate("120");
        player7.setWickets("40");
        player7.setEconomy("2.0");
        player7.setTotalruns("1200");
        modelPredictedPlayerArrayList.add(player7);

        Model_PredictedPlayer player8 = new Model_PredictedPlayer();
        player8.setCentury("0");
        player8.setPlayerage("27");
        player8.setFifyt("0");
        player8.setHighscore("40");
        player8.setPlayername("Khushdil Shah");
        player8.setMatchplay("30");
        player8.setStriekrate("120");
        player8.setWickets("0");
        player8.setEconomy("0");
        player8.setTotalruns("756");
        modelPredictedPlayerArrayList.add(player8);

        Model_PredictedPlayer player9 = new Model_PredictedPlayer();
        player9.setCentury("20");
        player9.setPlayerage("22");
        player9.setFifyt("0");
        player9.setHighscore("40");
        player9.setPlayername("Mohammad Hafeez");
        player9.setMatchplay("250");
        player9.setStriekrate("120");
        player9.setWickets("89");
        player9.setEconomy("2.0");
        player9.setTotalruns("11000");
        modelPredictedPlayerArrayList.add(player9);

        Model_PredictedPlayer player10 = new Model_PredictedPlayer();
        player10.setCentury("0");
        player10.setPlayerage("23");
        player10.setFifyt("0");
        player10.setHighscore("40");
        player10.setPlayername("Mohammad Hasnain");
        player10.setMatchplay("30");
        player10.setStriekrate("120");
        player10.setWickets("40");
        player10.setEconomy("2.0");
        player10.setTotalruns("980");
        modelPredictedPlayerArrayList.add(player10);

        Model_PredictedPlayer player11 = new Model_PredictedPlayer();
        player11.setCentury("0");
        player11.setPlayerage("26");
        player11.setFifyt("1");
        player11.setHighscore("40");
        player11.setPlayername("Mohammad Nawaz");
        player11.setMatchplay("30");
        player11.setStriekrate("120");
        player11.setWickets("40");
        player11.setEconomy("2.6");
        player11.setTotalruns("1400");
        modelPredictedPlayerArrayList.add(player11);


        adapterPredictedPlayer = new Adapter_PredictedPlayer(getApplicationContext(), modelPredictedPlayerArrayList);
        recyc_predictedplayer.setAdapter(adapterPredictedPlayer);
        adapterPredictedPlayer.notifyDataSetChanged();

        lottieDialog.dismiss();


    }
}