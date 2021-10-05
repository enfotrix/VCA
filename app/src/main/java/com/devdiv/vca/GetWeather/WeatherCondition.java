package com.devdiv.vca.GetWeather;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherCondition {

    public static final String API_CITY_NAME = "https://www.metaweather.com/api/location/search/?query=";
    public static final String API_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";


    Context context;
    private String id;

    public WeatherCondition(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityIdByName(String cityname, VolleyResponseListener volleyResponseListener) {

        String url = API_CITY_NAME + cityname;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                id = "";
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    id = jsonObject.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(context, "" + id, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(id);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "someThing Wrong", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("someThing Wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

    }

    public interface GetCityWeatherByIdResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityWeatherById(String cityID, String date, GetCityWeatherByIdResponseListener getCityWeatherByIdResponseListener) {

        String url = API_CITY_WEATHER_BY_ID + cityID;

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray consolidatedWeatherArray = response.getJSONArray("consolidated_weather");

                    for (int i = 0; i < consolidatedWeatherArray.length(); i++) {

                        JSONObject dateWiseWeather = (JSONObject) consolidatedWeatherArray.get(i);
                        //Toast.makeText(context, "" + dateWiseWeather.get("weather_state_name"), Toast.LENGTH_SHORT).show();

                        if (dateWiseWeather.get("applicable_date").equals(date)) {

                            getCityWeatherByIdResponseListener.onResponse((String) dateWiseWeather.get("weather_state_name"));
                            //Toast.makeText(context, "" + dateWiseWeather.get("weather_state_name"), Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCityWeatherByIdResponseListener.onError("SomeThing Wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(objectRequest);

    }

    public interface GetCityWeatherByNameResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityWeatherByName(String cityName, String date, GetCityWeatherByNameResponseListener getCityWeatherByNameResponseListener) {

        // first need to get cityID
        getCityIdByName(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityID) {

                // on cityID response , we will get cityweatherbyID
                getCityWeatherById(cityID, date, new GetCityWeatherByIdResponseListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(String cityID) {

                        //on cityweatheryid response, will get weather by name

                        getCityWeatherByNameResponseListener.onResponse(cityID);

                    }
                });
            }
        });
    }

}
