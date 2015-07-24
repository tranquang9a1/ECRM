package com.fu.group10.capstone.apps.staffmobileapp.Utils;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by QuangTV on 5/30/2015.
 * @author TrungDQ
 */

public class NetworkUtils {

    /** Get Data Fom URL Using GET Method */
    public static String getResponseFromGetRequest(String url) {
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = 60000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 60000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpGet httpGet = new HttpGet(url);

        /** after prepare for data. prepare for sending */
        try {
            /**
             * HttpResponse is an interface just like HttpGet
             * therefore we can't initialize them
             */
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return parseHttpResponse(httpResponse);
        } catch (IllegalStateException ex) {
            Log.d("", "Request IllegalStateException");
            ex.printStackTrace();
        } catch (ClientProtocolException ex) {
            Log.d("", "Request ClientProtocolException");
            ex.printStackTrace();
        } catch (IOException ex) {
            Log.d("", "Request IOException");
            ex.printStackTrace();
            return "IOException";
        }
        return null;
    }

    /** Get Data Fom URL Using POST Method */
    public static String getResponseFromPOSTRequest(String url, Map<String, String> params, int timeout) {

        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        /**
         * In a POST request, we don't pass the values in the URL.
         * Therefore we use only the web page URL as the parameter of the HtpPost arguments
         */
        HttpPost httpPost = new HttpPost(url);

        /**
         * Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
         * uniquely separate by the other end.
         * To achieve that we use BasicNameValuePair
         */
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            BasicNameValuePair basicNameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());

            /**
             * We add the content that we want to pass with the POST request to as name-value pairs
             * Now we put those sending details to an ArrayList with type safe of NameValuePair
             */
            nameValuePairList.add(basicNameValuePair);
        }

        /** after prepare for data. prepare for sending */
        try {

            /**
             *  UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
             *  This is typically useful while sending an HTTP POST request.
             */
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

            /** setEntity() hands the entity (here it is urlEncodedFormEntity) to the request. */
            httpPost.setEntity(urlEncodedFormEntity);

            try {
                /**
                 * HttpResponse is an interface just like HttpPost
                 * therefore we can't initialize them
                 */
                HttpResponse httpResponse = httpClient.execute(httpPost);
                return parseHttpResponse(httpResponse);


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parseHttpResponse(HttpResponse httpResponse) {
        /**
         * according to the JAVA API, InputStream constructor do nothing.
         * So we can't initialize InputStream although it is not an interface
         */
        InputStream inputStream;
        try {
            inputStream = httpResponse.getEntity().getContent();
            /** buffer for performance */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            /** StringBuilder for performance */
            StringBuilder stringBuilder = new StringBuilder();

            String bufferedStrChunk;

            while((bufferedStrChunk = bufferedReader.readLine()) != null){
                stringBuilder.append(bufferedStrChunk);
            }

            // Log.i(APP_TAG, stringBuilder.toString());
            String result = stringBuilder.toString();
            // Remove illegal char 65279
            if((int)result.charAt(0)==65279)
                result=result.substring(1);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
