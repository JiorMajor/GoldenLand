package com.svbtle.johnmajor.goldenland;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Transfer {

    static public String base = "http://10.10.2.144/api/v1/image";
    static public String target = "/upload";
    static public  String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiI1NWI0ZjY1OGRhNWYyODgyNWFmMzM3ZDMiLCJ1c2VybmFtZSI6ImpvaG5tYWpvciIsImlhdCI6MTQzNzkyMzY2NiwiZXhwIjoxNDM4MDEwMDY2fQ.aqqvd-kNFLIXK9My_aNNHmxRKp60Ej3eGHR0KFh8WGY";
    static public String placeid = "55b4f6f4960fcef2285090d0";

//    public static int uploadFile(String path){
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(base+target);
//
//        try{
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//            nameValuePairs.add(new BasicNameValuePair("image",""));
//            nameValuePairs.add(new BasicNameValuePair("placeId", placeid));
//            nameValuePairs.add(new BasicNameValuePair("token", token));
//
//        }
//    }


    public static int uploadFile(String path) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize, serverResponseCode=0;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(path);
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(base+target);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("file", sourceFile.getName());

            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"file1\";filename=\""  + sourceFile.getName() + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverResponseCode;
    }

}