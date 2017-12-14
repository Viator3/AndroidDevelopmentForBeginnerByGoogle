package com.example.valerii.weatherreport;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valerii on 13.12.2017.
 */

public final class QueryUtils {


    private static final String SAMPLE_JSON_RESPONSE = "{\"cnt\":4,\"list\":[{\"coord\":{\"lon\":36.25,\"lat\":50}," +
            "\"sys\":{\"type\":1,\"id\":7355,\"message\":0.0021,\"country\":\"UA\",\"sunrise\":1513056299,\"sunset\"" +
            ":1513085585},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":" +
            "\"04n\"}],\"main\":{\"temp\":7,\"pressure\":1014,\"humidity\":93,\"temp_min\":7,\"temp_max\":7}," +
            "\"visibility\":10000,\"wind\":{\"speed\":5,\"deg\":220},\"clouds\":{\"all\":90},\"dt\":1513100672,\"id\"" +
            ":706483,\"name\":\"Kharkiv\"},{\"coord\":{\"lon\":36.16,\"lat\":50.22},\"sys\":{\"type\":1,\"id\":7355," +
            "\"message\":0.0023,\"country\":\"UA\",\"sunrise\":1513056382,\"sunset\":1513085546},\"weather\":[{\"id\":" +
            "804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"main\":{\"temp\":" +
            "6.47,\"pressure\":1014,\"humidity\":93,\"temp_min\":6,\"temp_max\":7},\"visibility\":10000,\"wind\"" +
            ":{\"speed\":5,\"deg\":220},\"clouds\":{\"all\":90},\"dt\":1513100672,\"id\":693598,\"name\":\"Slatino\"}" +
            ",{\"coord\":{\"lon\":-74.01,\"lat\":40.71},\"sys\":{\"type\":1,\"id\":1969,\"message\":0.0047,\"country\"" +
            ":\"US\",\"sunrise\":1513080687,\"sunset\":1513114141},\"weather\":[{\"id\":500,\"main\":\"Rain\"," +
            "\"description\":\"light rain\",\"icon\":\"10d\"},{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\"" +
            ",\"icon\":\"50d\"}],\"main\":{\"temp\":8.38,\"pressure\":999,\"humidity\":61,\"temp_min\":5,\"temp_max\"" +
            ":11},\"visibility\":16093,\"wind\":{\"speed\":4.1,\"deg\":280,\"gust\":8.2},\"clouds\":{\"all\":90},\"dt\"" +
            ":1513100672,\"id\":5128581,\"name\":\"New York\"},{\"coord\":{\"lon\":2.35,\"lat\":48.85},\"sys\":" +
            "{\"type\":1,\"id\":5617,\"message\":0.0212,\"country\":\"FR\",\"sunrise\":1513064132,\"sunset\"" +
            ":1513094030},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\"" +
            ":\"01n\"}],\"main\":{\"temp\":2.57,\"pressure\":1014,\"humidity\":86,\"temp_min\":1,\"temp_max\":4}," +
            "\"visibility\":10000,\"wind\":{\"speed\":2.1,\"deg\":220},\"clouds\":{\"all\":0},\"dt\":1513100672,\"" +
            "id\":2988507,\"name\":\"Paris\"}]}";

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }


    public static ArrayList<Weather> extractWeathersList() {

        ArrayList<Weather> weatherArrayList = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray jsonArray = jsonRootObject.optJSONArray("list");
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = jsonArray.optJSONObject(index);
                JSONObject jsonObjectMain = jsonObject.optJSONObject("main");
                JSONObject jsonObjectSys = jsonObject.optJSONObject("sys");
                JSONArray jsonArrayWeather = jsonObject.optJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                String country = jsonObjectSys.optString("country");
                String icon = jsonObjectWeather.optString("icon");
                int temperature = jsonObjectMain.optInt("temp");
                long data = jsonObject.optLong("dt");
                String name = jsonObject.optString("name");

                Weather weather = new Weather(String.valueOf(temperature), selectionIcon(icon),
                        name + ", " + country, String.valueOf(data));
                weatherArrayList.add(weather);
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return weatherArrayList;
    }

    public static int selectionIcon(String icon) {
        int iconId = 0;
        if (icon.equals("01d")) iconId = R.drawable.clear_sky_01d;
        if (icon.equals("01n")) iconId = R.drawable.clear_sky_01n;
        if (icon.equals("02d")) iconId = R.drawable.few_clouds_02d;
        if (icon.equals("02n")) iconId = R.drawable.few_clouds_02n;
        if (icon.equals("03d")) iconId = R.drawable.scattered_clouds_03d;
        if (icon.equals("03n")) iconId = R.drawable.scattered_clouds_03n;
        if (icon.equals("04d")) iconId = R.drawable.broken_clouds_04d;
        if (icon.equals("04n")) iconId = R.drawable.broken_clouds_04n;
        if (icon.equals("09d")) iconId = R.drawable.shower_rain_09d;
        if (icon.equals("09n")) iconId = R.drawable.shower_rain_09n;
        if (icon.equals("10d")) iconId = R.drawable.rain_10d;
        if (icon.equals("10n")) iconId = R.drawable.rain_10n;
        if (icon.equals("11d")) iconId = R.drawable.thunderstorm_11d;
        if (icon.equals("11n")) iconId = R.drawable.thunderstorm_11n;
        if (icon.equals("13d")) iconId = R.drawable.snow_13d;
        if (icon.equals("13n")) iconId = R.drawable.snow_13n;
        return iconId;
    }
}

