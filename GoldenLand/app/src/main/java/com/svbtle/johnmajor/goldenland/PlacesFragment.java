package com.svbtle.johnmajor.goldenland;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {


    public PlacesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_places, container, false);
        CardView cardView1 = (CardView)view.findViewById(R.id.card1);
        CardView cardView2 = (CardView)view.findViewById(R.id.card2);
        CardView cardView3 = (CardView)view.findViewById(R.id.card3);
        CardView cardView4 = (CardView)view.findViewById(R.id.card4);
        final TextView textView1 = (TextView)view.findViewById(R.id.title_text1);
        final TextView textView2 = (TextView)view.findViewById(R.id.title_text2);
        final TextView textView3 = (TextView)view.findViewById(R.id.title_text3);
        final TextView textView4 = (TextView)view.findViewById(R.id.title_text4);
        textView1.setText("Shwedagon");
        textView2.setText("Mandalay Palace");
        textView3.setText("Ananda Temple");
        textView4.setText("Inle Lake");

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putString("query", textView1.getText().toString());
                fragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putString("query",textView2.getText().toString());
                fragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment).commit();
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putString("query",textView3.getText().toString());
                fragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment).commit();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putString("query",textView4.getText().toString());
                fragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment).commit();
            }
        });

        return view;
    }
}
