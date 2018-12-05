package com.example.raphael.pettransport.activities;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.raphael.pettransport.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        this.mWindow = LayoutInflater.from(context).inflate(R.layout.custom_windom_field, null);

    }

    private void renderWindowText(Marker marker, View view) {
        TextView textView = (TextView) view.findViewById(R.id.fieldChamado);
        textView.setText(marker.getTitle());
        TextView snippet = (TextView) view.findViewById(R.id.snippet);
        snippet.setText(marker.getSnippet());
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker, this.mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker, this.mWindow);
        return mWindow;
    }

}
