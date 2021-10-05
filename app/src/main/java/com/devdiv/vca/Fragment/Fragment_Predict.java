package com.devdiv.vca.Fragment;

import android.app.DatePickerDialog;
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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devdiv.vca.GetWeather.WeatherCondition;
import com.devdiv.vca.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_Predict extends Fragment {

    EditText txt_city;
    Button btn_get;
    TextInputLayout txtLay_country, txtLay_stadium, txtLay_pitch;
    AutoCompleteTextView autoCompletetxtcountry, autoCompletetxtStadium,
            autoCompletetxtpitch, autoCompletetxtteam;
    ArrayList<String> stadium;
    TextView tvSelectedDate, datee, txt_weatherResult;
    ImageView imgCalender;
    Calendar myCalendar;
    CardView card;
    private String fromatedate;
    private String checkstadium;
    WeatherCondition weatherCondition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__predict, container, false);

        iniviews(view);

        getweatherbycity();

        getplayer();

        myCalendar = Calendar.getInstance();
        weatherCondition = new WeatherCondition(getContext());

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

//                for (Iterator<String> it = response.keys(); it.hasNext(); ) {
//                    String key = it.next();
//                    JSONObject entry = null;
//                    try {
//
//                        entry = response.getJSONObject(key);
//                        Log.d("TAG", entry.toString());
//
//                        JSONObject phone = entry.getJSONObject("Inzamam-ul-Haq");
//                        Toast.makeText(getContext(), "" + phone.get("HS"), Toast.LENGTH_SHORT).show();
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }

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

                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);
//        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

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


    }

    private void updateLabel(String stadiumName) {

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
            }

            @Override
            public void onResponse(String weatherCondition) {
                // Toast.makeText(getContext(), "" + weatherCondition, Toast.LENGTH_SHORT).show();
                txt_weatherResult.setText("Weather on " + fromatedate + "  " + weatherCondition);

                txtLay_pitch.setVisibility(View.VISIBLE);
                selectpitch(weatherCondition);
            }
        });

    }

    private void selectpitch(String condition) {

        String[] pitches = {"Green Pitch", "Dusty Pitch", "Dead Pitch"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_list, pitches);
        autoCompletetxtpitch.setAdapter(arrayAdapter);

        autoCompletetxtpitch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                getBestPlayer(autoCompletetxtpitch.getText().toString(), condition);
            }
        });

    }

    private void getBestPlayer(String pitchCondition, String weatherCondition) {

        Toast.makeText(getContext(), pitchCondition + "\n" + weatherCondition, Toast.LENGTH_SHORT).show();
    }
}