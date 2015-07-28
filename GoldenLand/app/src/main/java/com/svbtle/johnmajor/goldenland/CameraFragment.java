package com.svbtle.johnmajor.goldenland;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {

    final static int CAPTURE_IMAGE_REQUEST_CODE = 101;
    String path = "";


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_camera, container, false);
        capturePhoto();
        return (v);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returned) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (returned != null)
                    path = getRealPathFromURI(returned.getData());
                new AsyncTask<String, Void, Void>(){
                    @Override
                    protected Void doInBackground(String... arg) {
                        try {
                            Transfer.uploadFile(arg[0]);
                        } catch (Exception e) {
                        }
                        return null;
                    }
                    protected void onPostExecute(Void b) {
                        Toast.makeText(getActivity(), "The path is" + path, Toast.LENGTH_LONG).show();
                        Fragment fragment = new PhotoGalleryFragment();
                        getFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
                    }
                }.execute(path);
            }
        }
    }

    private void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        path = String.format("%s/PHOTO_%s.jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                String.valueOf(System.currentTimeMillis()));
        Log.i("PATH", path);
        intent.putExtra("return-data", true);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.parse("file://" + path));
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath;
        if (uri != null && "content".equals(uri.getScheme())) {

            Cursor cursor = getActivity().getContentResolver().
                    query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();
            filePath = cursor.getString(0);
            cursor.close();
        } else {
            filePath = uri.getPath();
        }
        return (filePath);
    }

}
