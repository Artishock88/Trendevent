package de.artmedia.artyom.trendevent_screentest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Artyom on 16.05.2015.
 */
public class Pending extends Fragment implements View.OnClickListener{
    private TextView lade_text;
    private View dimmer;

    //Arrays fuer den Adapter
    String TITLE[];
    String CONTENT[];
    String NAME[];
    String UPCOUNT[];
    String BANNER[];
    String PIC[];
    int ID[];

    int position;

    Context context;

    RecyclerView mBlockView;
    RecyclerView.Adapter mBlockAdapter;
    RecyclerView.LayoutManager mBlockLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.pending_layout, container, false);

        lade_text = (TextView) getActivity().findViewById(R.id.lade_text);
        dimmer = (View) getActivity().findViewById(R.id.dimmer);
        lade_text.setVisibility(View.GONE);
        dimmer.setVisibility(View.GONE);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        JSONObject json;
        String str = "";
        String urlStr = "";
        HttpResponse response;

        urlStr = "http://art-tokarev.de/json/" + getArguments().getString("blockUrl");
        Log.d("STRING_IN_ADAPTER",urlStr);
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost(urlStr);


        try {
            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            JSONArray jArray = new JSONArray(str);
            position = jArray.length();

            TITLE = new String[position];
            CONTENT = new String[position];
            NAME = new String[position];
            UPCOUNT = new String[position];
            BANNER = new String[position];
            PIC = new String[position];
            ID = new int[position];


            for (int i=0; i<position; i++)
            {
                json = jArray.getJSONObject(i);

                TITLE[i] = json.getString("title");
                CONTENT[i] = json.getString("content");
                NAME[i] = json.getString("referent");
                BANNER[i] = json.getString("banner");
                PIC[i] = json.getString("pic");
                ID[i] = json.getInt("id");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        context = getActivity().getApplicationContext();


        //Setup der TOP-Liste
        mBlockView = null;
        mBlockAdapter = null;

        mBlockView = (RecyclerView) view.findViewById(R.id.block_live);
        mBlockView.setHasFixedSize(true);
        mBlockAdapter = new MyPendingAdapter(TITLE,CONTENT,NAME,BANNER,PIC,ID,context);
        mBlockView.setAdapter(mBlockAdapter);
        mBlockLayoutManager = new LinearLayoutManager(context);
        mBlockView.setLayoutManager(mBlockLayoutManager);

        return view;
    }



    @Override
    public void onStop()
    {
        super.onStop();
        lade_text.setVisibility(View.GONE);
        dimmer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {

    }
}
