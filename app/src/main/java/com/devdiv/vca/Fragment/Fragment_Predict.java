package com.devdiv.vca.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devdiv.vca.Activities.ActivityMySquad;
import com.devdiv.vca.Activities.ActivityPredictedPlayer;
import com.devdiv.vca.GetWeather.WeatherCondition;
import com.devdiv.vca.LottieDialog;
import com.devdiv.vca.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Predict extends Fragment {

    EditText txt_city;
    Button btn_get;
    TextInputLayout txtLay_country, txtLay_stadium, txtLay_pitch;
    AutoCompleteTextView autoCompletetxtcountry, autoCompletetxtStadium,
            autoCompletetxtpitch, autoCompletetxtteam,autoCompletetxtweather;
    ArrayList<String> stadium;
    TextView tvSelectedDate, datee, txt_weatherResult, txt_mysquad;
    ImageView imgCalender;
    Calendar myCalendar;
    CardView card;
    private String fromatedate;
    private String checkstadium;
    WeatherCondition weatherCondition;
    AppCompatButton btn_predict;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__predict, container, false);

        iniviews(view);

        getweatherbycity();

        getplayer();
        postDataUsingVolley();
//        getsquad();

        btn_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getContext(), ActivityPredictedPlayer.class);
                intent.putExtra("weather", autoCompletetxtweather.getText().toString());
                intent.putExtra("pitch", autoCompletetxtpitch.getText().toString());
                startActivity(intent);
            }
        });

        myCalendar = Calendar.getInstance();
        weatherCondition = new WeatherCondition(getContext());

        ////////////////// my squad////////////////////
        txt_mysquad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityMySquad.class));
            }
        });


        String[] weatherarray = {"warm", "cold", "cloud", "normal"};
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(), R.layout.dropdown_list, weatherarray);
        autoCompletetxtweather.setAdapter(arrayAdapter1);

        autoCompletetxtweather.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(), "Weather " + autoCompletetxtweather.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        String[] countryarray = {"Pakistan", "India", "England", "Australia", "New Zealand", "South Africa"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_list, countryarray);
        autoCompletetxtteam.setAdapter(arrayAdapter);

        autoCompletetxtteam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(), "Opponent Team " + autoCompletetxtteam.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


      /*  // get weather by id
        weatherCondition.getCityWeatherByName(txt_city.getText().toString().trim(), new WeatherCondition.GetCityWeatherByNameResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String cityID) {
                Toast.makeText(getContext(), "" + cityID, Toast.LENGTH_SHORT).show();
            }
        });
        // get city id only
                weatherCondition.getCityIdByName(txt_city.getText().toString().trim(), new WeatherCondition.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(getContext(), "" + cityID, Toast.LENGTH_SHORT).show();

                    }
                });*/


        return view;
    }

    private void getplayer() {

        String URL = "https://misri.pythonanywhere.com/player";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject postData = new JSONObject();

        try {
            postData.put("uname", "hassan");
            postData.put("pass", "data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "" + response.toString(), Toast.LENGTH_SHORT).show();

                JSONObject playerName = null;
                try {

                    playerName = response.getJSONObject("Inzamam-ul-Haq");
                    playerName.getString("Inns");

                    Toast.makeText(getContext(), "" + playerName.getString("HS"), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);
//        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void getsquad() {

        String URL = "https://misri.pythonanywhere.com/player";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

//                        Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < response.length(); i++) {

                            try {

                                Toast.makeText(getContext(), "" + response.getString(i), Toast.LENGTH_SHORT).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);

    }

    private void postDataUsingVolley() {
        // url to post our data
        String URL = "https://misri.pythonanywhere.com/player";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty

                // on below line we are displaying a success toast message.
//                Toast.makeText(getContext(), "Data added to API" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject playername = new JSONObject(response);

                    JSONObject player = playername.getJSONObject("KC Sangakkara");
                    // below are the strings which we
                    // extract from our json object.

                    String name = player.getString("Player");
                    String job = player.getString("Runs");
                    String century = player.getString("100");
                    String fifty = player.getString("50");
                    Toast.makeText(getContext(), name + "\n" +
                            job + "\n" +
                            century + "\n" +
                            fifty, Toast.LENGTH_SHORT).show();

                    // on below line we are setting this string s to our text view.
//                    responseTV.setText("Name : " + name + "\n" + "Job : " + job);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("team", "paksitan");
                params.put("pitch", "dusty");
                params.put("weather", "clear");
                params.put("ground", "lahore");
                params.put("oteam", "India");

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private void getweatherbycity() {

        String[] countryarray = {"Pakistan", "India", "England", "Australia", "New Zealand", "South Africa"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_list, countryarray);
        autoCompletetxtcountry.setAdapter(arrayAdapter);

        autoCompletetxtcountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedcountry = autoCompletetxtcountry.getText().toString();
                // Toast.makeText(getContext(), "" + selectedcountry, Toast.LENGTH_SHORT).show();

                txtLay_stadium.setVisibility(View.VISIBLE);

                selectstadium(autoCompletetxtcountry.getText().toString());

            }
        });

    }

    private void selectstadium(String countryName) {

        stadium = new ArrayList<>();
        if (countryName.equals("Pakistan")) {

            stadium.add("Gaddafi Stadium(Lahore)");
            stadium.add("National Stadium(Karachi)");

        } else if (countryName.equals("India")) {

            stadium.add("Eden Gardens(Kolkata)");
            stadium.add("Arun Jaitley Stadium(New Delhi)");
            stadium.add("Brabourne Stadium(Mumbai)");
            stadium.add("Wankhede Stadium(Mumbai)");

        } else if (countryName.equals("England")) {

            stadium.add("Lords(London,UK)");
            stadium.add("Sophia Gardens(Carddiff , Wales)");
            stadium.add("Trent Bridge(West Bridgford, Nottingham)");

        } else if (countryName.equals("Australia")) {

            stadium.add("Stadium Australia(Sydney)");
            stadium.add("Melbourne Cricket Ground(Melbourne)");
            stadium.add("Perth Stadium(Perth)");

        } else if (countryName.equals("New Zealand")) {

            stadium.add("Auckland Domain(Auckland)");
            stadium.add("Victoria Park(Auckland)");
            stadium.add("Eden Park(Auckland)");
            stadium.add("CornWall Park(Auckland)");
            stadium.add("Burnside Park(Christchurch)");

        } else if (countryName.equals("South Africa")) {

            stadium.add("Ellis Park(Johannesburg)");
            stadium.add("Kingsmead(Durban)");
            stadium.add("Lords(Durban)");
            stadium.add("lords No.3 Ground(Durban)");
            stadium.add("lords No.4 Ground(Durban)");

        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_list, stadium);
        autoCompletetxtStadium.setAdapter(arrayAdapter);

        autoCompletetxtStadium.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                datee.setVisibility(View.VISIBLE);
                card.setVisibility(View.VISIBLE);


                imgCalender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                // TODO Auto-generated method stub
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel(autoCompletetxtStadium.getText().toString());
                            }

                        };

                        new DatePickerDialog(getContext(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                    }
                });

            }
        });

    }

    private void iniviews(View view) {

        txtLay_country = view.findViewById(R.id.txtLay_country);
        txtLay_stadium = view.findViewById(R.id.txtLay_stadium);
        autoCompletetxtStadium = view.findViewById(R.id.autoCompletetxtStadium);
        autoCompletetxtcountry = view.findViewById(R.id.autoCompletetxtcountry);
        imgCalender = view.findViewById(R.id.imgCalender);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        datee = view.findViewById(R.id.datee);
        card = view.findViewById(R.id.card);
        txt_weatherResult = view.findViewById(R.id.txt_weatherResult);
        autoCompletetxtpitch = view.findViewById(R.id.autoCompletetxtpitch);
        txtLay_pitch = view.findViewById(R.id.txtLay_pitch);
        autoCompletetxtteam = view.findViewById(R.id.autoCompletetxtteam);
        autoCompletetxtweather = view.findViewById(R.id.autoCompletetxttweather);
        txt_mysquad = view.findViewById(R.id.txt_mysquad);
        btn_predict = view.findViewById(R.id.btn_predict);


    }

    private void updateLabel(String stadiumName) {

        final LottieDialog lottieDialog = new LottieDialog(getContext());
        lottieDialog.show();

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        fromatedate = sdf.format(myCalendar.getTime());

        tvSelectedDate.setText(sdf.format(myCalendar.getTime()));

        //Toast.makeText(getContext(), "" + stadiumName + "\n" + fromatedate, Toast.LENGTH_SHORT).show();

        if (stadiumName.equals("Gaddafi Stadium(Lahore)")) {
            checkstadium = "Lahore";
        } else if (stadiumName.equals("National Stadium(Karachi)")) {
            checkstadium = "Karachi";
        } else if (stadiumName.equals("Eden Gardens(Kolkata)")) {
            checkstadium = "Kolkata";
        } else if (stadiumName.equals("Arun Jaitley Stadium(New Delhi)")) {
            checkstadium = "New Delhi";
        } else if (stadiumName.equals("Brabourne Stadium(Mumbai)")) {
            checkstadium = "Mumbai";
        } else if (stadiumName.equals("Wankhede Stadium(Mumbai)")) {
            checkstadium = "Mumbai";
        } else if (stadiumName.equals("Lords(London,UK)")) {
            checkstadium = "London";
        } else if (stadiumName.equals("Sophia Gardens(Carddiff , Wales)")) {
            checkstadium = "Cardiff";
        } else if (stadiumName.equals("Trent Bridge(West Bridgford, Nottingham)")) {
            checkstadium = "Nottingham";
        } else if (stadiumName.equals("Stadium Australia(Sydney)")) {
            checkstadium = "Sydney";
        } else if (stadiumName.equals("Melbourne Cricket Ground(Melbourne)")) {
            checkstadium = "Melbourne";
        } else if (stadiumName.equals("Perth Stadium(Perth)")) {
            checkstadium = "Perth";
        } else if (stadiumName.equals("Auckland Domain(Auckland)")) {
            checkstadium = "Auckland";
        } else if (stadiumName.equals("Victoria Park(Auckland)")) {
            checkstadium = "Auckland";
        } else if (stadiumName.equals("Eden Park(Auckland)")) {
            checkstadium = "Auckland";
        } else if (stadiumName.equals("CornWall Park(Auckland)")) {
            checkstadium = "Auckland";
        } else if (stadiumName.equals("Burnside Park(Christchurch)")) {
            checkstadium = "Christchurch";
        } else if (stadiumName.equals("Ellis Park(Johannesburg)")) {
            checkstadium = "Johannesburg";
        } else if (stadiumName.equals("Kingsmead(Durban)")) {
            checkstadium = "Durban";
        } else if (stadiumName.equals("Lords(Durban)")) {
            checkstadium = "Durban";
        } else if (stadiumName.equals("lords No.3 Ground(Durban)")) {
            checkstadium = "Durban";
        } else if (stadiumName.equals("lords No.4 Ground(Durban)")) {
            checkstadium = "Durban";
        }


        weatherCondition.getCityWeatherByName(checkstadium, fromatedate, new WeatherCondition.GetCityWeatherByNameResponseListener() {
            @Override
            public void onError(String message) {

                Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();

                lottieDialog.dismiss();
            }

            @Override
            public void onResponse(String weatherCondition) {
                // Toast.makeText(getContext(), "" + weatherCondition, Toast.LENGTH_SHORT).show();
                txt_weatherResult.setText("Weather on " + fromatedate + "  " + weatherCondition);

                txtLay_pitch.setVisibility(View.VISIBLE);
                selectpitch(weatherCondition);

                lottieDialog.dismiss();
            }
        });

    }

    private void selectpitch(String condition) {

        String[] pitches = {"dusty", "green", "seam" , "normal"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_list, pitches);
        autoCompletetxtpitch.setAdapter(arrayAdapter);

        autoCompletetxtpitch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                getBestPlayer(autoCompletetxtpitch.getText().toString(), condition);

                btn_predict.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getBestPlayer(String pitchCondition, String weatherCondition) {

        Toast.makeText(getContext(), pitchCondition + "\n" + weatherCondition, Toast.LENGTH_SHORT).show();
    }
}