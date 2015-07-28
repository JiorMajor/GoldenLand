package com.svbtle.johnmajor.goldenland;


import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        final EditText search = (EditText)view.findViewById(R.id.searchquery);
        Button btnSearch = (Button) view.findViewById(R.id.btnSearch);
        if(getArguments()!=null)
        {
            search.setText(getArguments().getString("query"));
            Log.e("QUERY NOW", getArguments().getString("query"));
            searchlocation(search.getText().toString());
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LatLng latlng = null;
                // Perform action on click
                //if(getArguments()==null){
                latlng = searchlocation(search.getText().toString());
                //}else {
                //  latlng=searchlocation(getArguments().getString("query"));
                //Log.i("AA", getArguments().getString("query"));
                //}

                String lat = String.valueOf(latlng.latitude);
                String lng = String.valueOf(latlng.longitude);
                //Launch Current Location Fragment
                Fragment place = new CurrentLocationFragment();
                Bundle args = new Bundle();
                args.putString("latitude", lat);
                args.putString("longitute", lng);
                place.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, place)
                        .commit();
            }
        });
        //searchlocation(search.getText().toString());


        return view;
    }

    private LatLng searchlocation(String query) {
        Geocoder gc = new Geocoder(getActivity().getLayoutInflater().getContext(), Locale.getDefault());
        List<Address> addresses = null;
        double lat = 0; double lng =0;
        try{
            addresses = gc.getFromLocationName(query, 1);
            if(addresses.equals(null)){
                Toast.makeText(getActivity().getLayoutInflater().getContext(), "Please input place name to search.", Toast.LENGTH_SHORT).show();
            }
            //Location name to Latitude & Longitude
            if (addresses != null && addresses.size() > 0) {
                lat = addresses.get(0).getLatitude();
                lng = addresses.get(0).getLongitude();
            }
        }catch (IOException e){

            Toast.makeText(getActivity().getLayoutInflater().getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        LatLng place = new LatLng(lat, lng);
        return place;
    }


}
