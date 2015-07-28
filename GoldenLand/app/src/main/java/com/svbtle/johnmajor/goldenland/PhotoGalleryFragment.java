package com.svbtle.johnmajor.goldenland;


import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoGalleryFragment extends Fragment {


    public PhotoGalleryFragment() {
        // Required empty public constructor
    }


    Integer[] imageIDs = {
            R.drawable.inle1,
            R.drawable.inle2,
            R.drawable.inle3,
            R.drawable.inle4,
            R.drawable.iss1,
            R.drawable.iss2,
            R.drawable.iss3,
            R.drawable.iss4,
            R.drawable.mdy1,
            R.drawable.mdy2,
            R.drawable.mdy3,
            R.drawable.mdy4,
            R.drawable.mdy5,
            R.drawable.mdy6,
            R.drawable.sdg1,
            R.drawable.sdg2,
            R.drawable.sdg3,
            R.drawable.sdg4,
            R.drawable.sdg5
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        Gallery gallery = (Gallery)view.findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(getActivity().getBaseContext()));
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // display the images selected
                    ImageView imageView = (ImageView) view.findViewById(R.id.image1);
                    imageView.setImageResource(imageIDs[position]);
                }
            });

        return  view;
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a =getActivity().obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }
        // returns the number of images
        public int getCount() {
            return imageIDs.length;
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageIDs[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }

}
