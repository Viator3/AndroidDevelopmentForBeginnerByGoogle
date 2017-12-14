package com.example.valerii.weatherreport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Valerii on 12.12.2017.
 */

class WeatherAdapter extends ArrayAdapter<Weather> {

    public WeatherAdapter(@NonNull Context context, ArrayList<Weather> weathersList) {
        super(context, 0, weathersList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.weather_list_item, parent, false);
        }

        Weather currentWeather = getItem(position);

        TextView temperatureTextView = (TextView) listItemView.findViewById(R.id.text_temperature);
        temperatureTextView.setText(currentWeather.getTemperature());

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.text_location);
        locationTextView.setText(currentWeather.getLocation());

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.text_time);
        timeTextView.setText(currentWeather.getTime());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_weather);
        imageView.setImageResource(currentWeather.getImageResourceId());

        return listItemView;
    }
}


