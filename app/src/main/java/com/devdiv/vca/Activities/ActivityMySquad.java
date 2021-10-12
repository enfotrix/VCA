package com.devdiv.vca.Activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.devdiv.vca.Adapters.Adapter_MySquad;
import com.devdiv.vca.LottieDialog;
import com.devdiv.vca.Model.Model_MySquad;
import com.devdiv.vca.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ActivityMySquad extends AppCompatActivity {

    ArrayList<Model_MySquad> mySquadArrayList = new ArrayList<>();
    RecyclerView recyc_mysquad;
    Adapter_MySquad adapterMySquad;
    TextView txt_totalsquad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_squad);

        getSupportActionBar().hide();

        txt_totalsquad = findViewById(R.id.txt_totalsquad);
        txt_totalsquad.setText("15");

        recyc_mysquad = findViewById(R.id.list_mysquad);
        recyc_mysquad.setHasFixedSize(true);
        recyc_mysquad.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getmysquad();

        getsquad();
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getmysquad();
//                getsquad();
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void getsquad() {

        final LottieDialog lottieDialog = new LottieDialog(this);
//        lottieDialog.show();

        mySquadArrayList.clear();

        String URL = "https://misri.pythonanywhere.com/player";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

//                        Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < response.length(); i++) {

                            try {

                                Model_MySquad player1 = new Model_MySquad();
                                player1.setPlayername(response.getString(i));
                                mySquadArrayList.add(player1);

                                Toast.makeText(getApplicationContext(), "" + response.getString(i), Toast.LENGTH_SHORT).show();

//                                lottieDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        adapterMySquad = new Adapter_MySquad(getApplicationContext(), mySquadArrayList);
                        recyc_mysquad.setAdapter(adapterMySquad);
                        adapterMySquad.notifyDataSetChanged();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                lottieDialog.dismiss();
            }
        });

        queue.add(jsonArrayRequest);

    }

    private void getmysquad() {

        final LottieDialog lottieDialog = new LottieDialog(this);
        lottieDialog.show();

        mySquadArrayList.clear();

        Model_MySquad player1 = new Model_MySquad();
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
        mySquadArrayList.add(player1);

        Model_MySquad player2 = new Model_MySquad();
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
        mySquadArrayList.add(player2);

        Model_MySquad player3 = new Model_MySquad();
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
        mySquadArrayList.add(player3);

        Model_MySquad player4 = new Model_MySquad();
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
        mySquadArrayList.add(player4);

        Model_MySquad player5 = new Model_MySquad();
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
        mySquadArrayList.add(player5);

        Model_MySquad player6 = new Model_MySquad();
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
        mySquadArrayList.add(player6);

        Model_MySquad player7 = new Model_MySquad();
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
        mySquadArrayList.add(player7);

        Model_MySquad player8 = new Model_MySquad();
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
        mySquadArrayList.add(player8);

        Model_MySquad player9 = new Model_MySquad();
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
        mySquadArrayList.add(player9);

        Model_MySquad player10 = new Model_MySquad();
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
        mySquadArrayList.add(player10);

        Model_MySquad player11 = new Model_MySquad();
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
        mySquadArrayList.add(player11);

        Model_MySquad player12 = new Model_MySquad();
        player12.setCentury("0");
        player12.setPlayerage("22");
        player12.setFifyt("0");
        player12.setHighscore("40");
        player12.setPlayername("Haris Rauf,");
        player12.setMatchplay("30");
        player12.setStriekrate("120");
        player12.setWickets("40");
        player12.setEconomy("0");
        player12.setTotalruns("1300");
        mySquadArrayList.add(player12);

        Model_MySquad player13 = new Model_MySquad();
        player13.setCentury("0");
        player13.setPlayerage("26");
        player13.setFifyt("0");
        player13.setHighscore("40");
        player13.setPlayername("Shaheen Shah Afridi");
        player13.setMatchplay("80");
        player13.setStriekrate("120");
        player13.setWickets("140");
        player13.setEconomy("3.0");
        player13.setTotalruns("400");
        mySquadArrayList.add(player13);

        Model_MySquad player14 = new Model_MySquad();
        player14.setCentury("0");
        player14.setPlayerage("24");
        player14.setFifyt("0");
        player14.setHighscore("40");
        player14.setPlayername("Sohaib Maqsood");
        player14.setMatchplay("30");
        player14.setStriekrate("120");
        player14.setWickets("1");
        player14.setEconomy("0");
        player14.setTotalruns("800");
        mySquadArrayList.add(player14);

        Model_MySquad player15 = new Model_MySquad();
        player15.setCentury("6");
        player15.setPlayerage("26");
        player15.setFifyt("20");
        player15.setHighscore("40");
        player15.setPlayername("Fakhar Zaman");
        player15.setMatchplay("30");
        player15.setStriekrate("120");
        player15.setWickets("0");
        player15.setEconomy("0");
        player15.setTotalruns("2700");
        mySquadArrayList.add(player15);

        adapterMySquad = new Adapter_MySquad(getApplicationContext(), mySquadArrayList);
        recyc_mysquad.setAdapter(adapterMySquad);
        adapterMySquad.notifyDataSetChanged();

        lottieDialog.dismiss();

    }
}