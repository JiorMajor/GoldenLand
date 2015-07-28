package com.svbtle.johnmajor.goldenland;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentLocationFragment extends android.app.Fragment {

    int oldrate = 0;


    public CurrentLocationFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_current_location, container, false);
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        final TextView textView = (TextView)view.findViewById(R.id.title_text);
        TextView abouttext = (TextView)view.findViewById(R.id.about_text);
        TextView detailtext = (TextView)view.findViewById(R.id.details_text);
        TextView link = (TextView)view.findViewById(R.id.link);
        final ImageView imageView = (ImageView)view.findViewById(R.id.image);
        RatingBar rb = (RatingBar)view.findViewById(R.id.ratingBar);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                switch (textView.getText().toString()){
                    case "Shwedagon":
                        oldrate = pref.getInt("sdgrating", 1);
                        break;
                    case "Inle Lake":
                        oldrate = pref.getInt("inlerating", 1);
                        break;
                    case "Mandalay Palace":
                        oldrate = pref.getInt("mdyrating", 1);
                        break;
                    case "Ananda Temple":
                        oldrate = pref.getInt("andrating", 1);
                        break;
                    case "Institute of System Science":
                        oldrate = pref.getInt("issrating", 1);
                        break;
                }
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(textView.getText().toString(), oldrate+1);
                editor.commit();
            }
        });
        if(getArguments()!=null){
            getArguments().getString("latitude");
            getArguments().getString("longitude");
            String lat =getArguments().getString("latitude");
            String lgt = getArguments().getString("longitute");
            Places place = getPlacefromLatLgt(lat, lgt);
            if(place!=null){

                textView.setText(place.getName());
                abouttext.setText(place.getAbout());
                detailtext.setText(place.getDetails());
                link.setText(link.getText()+" "+place.getLink());
                ArrayList<String> images = place.getImages();
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(
                        getActivity().getApplicationContext());
                int rate=0;
                switch (place.getName()){
                    case "Shwedagon":
                        rate = pref.getInt("sdgrating", 1);
                        break;
                    case "Inle Lake":
                        rate = pref.getInt("inlerating", 1);
                        break;
                    case "Mandalay Palace":
                        rate = pref.getInt("mdyrating", 1);
                        break;
                    case "Ananda Temple":
                        rate = pref.getInt("andrating", 1);
                        break;
                    case "Institute of System Science":
                        rate = pref.getInt("issrating", 1);
                        break;
                }
                rb.setRating(oldrate+rate);
                String s;
                final ArrayList<String> newimages = new ArrayList<String>();
                for(int i=0;i<images.size();i++){
                    s = images.get(i);
                    if(s.contains("localhost")){
                        s=s.replace("localhost", "http://10.10.2.144");
                    }
                    newimages.add(s);
                }
                ImageRequest request = new ImageRequest(newimages.get(1),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                imageView.setImageResource(R.drawable.sdg1);
                            }
                        });
                // Access the RequestQueue through your singleton class.
                MySingleton.getInstance(getActivity().getBaseContext()).addToRequestQueue(request);


                //OnClick
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment photofragment = new PhotoGalleryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("images", newimages);
                        photofragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.container, photofragment)
                                .commit();
                    }
                });
            }
            else {
                Toast.makeText(getActivity().getBaseContext(), "Cannot find place!", Toast.LENGTH_SHORT).show();
            }
        }

        //Image View
        else {

            String lat = "1.2919015";//String.valueOf(currentLocation().latitude);
            String lgt = "103.7762237";//String.valueOf(currentLocation().longitude);
            Places current = getPlacefromLatLgt(lat, lgt);
            textView.setText(current.getName());
            abouttext.setText(current.getAbout());
            detailtext.setText(current.getDetails());
            link.setText(link.getText()+" "+current.getLink());
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(
                    getActivity().getApplicationContext());
            int rate = pref.getInt("issrating",1);
            rb.setRating(oldrate+rate);
            ArrayList<String> images = current.getImages();
            String s;
            final ArrayList<String> newimages = new ArrayList<String>();
            for(int i=0;i<images.size();i++){
                s = images.get(i);
                if(s.contains("localhost")){
                    s=s.replace("localhost", "http://10.10.2.144");
                }
                newimages.add(s);
            }
            ImageRequest request = new ImageRequest(newimages.get(1),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            imageView.setImageResource(R.drawable.sdg1);
                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getActivity().getBaseContext()).addToRequestQueue(request);


            //OnClick
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment photofragment = new PhotoGalleryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("images", newimages);
                    photofragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container, photofragment)
                            .commit();
                }
            });


        }

        return view;
    }

    private LatLng currentLocation() {
        String curloc="";
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location loc;
        LatLng current;
        List<String> providers = lm.getProviders(true);
        for(int i=0;i<providers.size();i++){
            Location l=lm.getLastKnownLocation(providers.get(0));
            Log.i("[Lat, Long]",l.getLatitude()+", "+l.getLongitude());
        }
        loc = lm.getLastKnownLocation(providers.get(0));
        current = new LatLng(loc.getLatitude(), loc.getLongitude());
        Geocoder gc = new Geocoder(getActivity().getLayoutInflater().getContext(), Locale.getDefault());
        List<Address> addresses= null;
        try
        {
            addresses  = gc.getFromLocation(current.latitude, current.longitude,1);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getLayoutInflater().getContext(), "Location update failed.", Toast.LENGTH_SHORT).show();
        }
        Address cur = addresses.get(0);
        if(cur!=null) {
            if(cur.getAddressLine(1)!=null){
                curloc += cur.getAddressLine(0)+" "+cur.getAddressLine(1);
            }
            curloc+=cur.getAddressLine(0);
        }

        return current;
    }

    private Places getPlacefromLatLgt(String lat, String lgt){
        String url = "http://10.10.2.144:3000/api/v1/places";
        url = url+"/"+lat+"/"+lgt;
        JSONObject jsonObject = JSONParser.getJSONFromUrl(url);
        Log.i("JSON", jsonObject.toString());

        Places p=null;
        try {
            String _id = jsonObject.getString("_id");
            Log.i("ID", _id);
            String name = jsonObject.getString("name");
            Log.i("Name", name);

            JSONObject content = jsonObject.getJSONObject("content");
            String about = content.getString("about");
            String details = content.getString("detail");
            String link = content.getString("link");

            int recommend = Integer.parseInt(jsonObject.getString("recommend"));

            JSONObject coordinates = jsonObject.getJSONObject("coordinates");
            double latitute = Double.parseDouble(coordinates.getString("lat"));
            double longitute = Double.parseDouble(coordinates.getString("lgt"));

            ArrayList<String> images = new ArrayList<String>();
            JSONArray jArray = jsonObject.getJSONArray("images");
            if (jArray != null) {
                for (int i=0;i<jArray.length();i++){
                    images.add(jArray.get(i).toString());
                }
            }
            p = new Places(_id , name, about,  link,images,details, recommend, latitute, longitute);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }

}
