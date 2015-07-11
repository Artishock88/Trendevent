package de.artmedia.artyom.trendevent_screentest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Artyom on 16.05.2015.
 */
public class Top_Activity extends Activity {

    private Context context;


    ImageView banner_v;
    TextView title_v;
    TextView teaser_v;
    TextView name_v;
    TextView firm_title_v;
    TextView firm_name_v;
    ImageView ref_image_v;
    TextView stext_v;
    TextView mtext_v;
    TextView ref;
    ProgressBar top_lade;
    FloatingActionButton fab;
    private String stringID;
    private String str;
    private int id;
    private int useId;
    private int jsonId;
    JSONObject json;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        id = this.getIntent().getExtras().getInt("item");
        new AsyncHttp().execute();


    }

    public void onSend()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, title_v.getText());
        String shareMessage = null;
        String shareBody = getResources().getString(R.string.shareBody);
        shareMessage = name_v.getText() + " " + shareBody + " " + title_v.getText() + "." + "\nDas solltest du dir ansehen! http://bit.ly/1CumAYa";

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent,"Teilen"));
    }


    private class AsyncHttp extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            setContentView(R.layout.top_main_layout);
            top_lade = (ProgressBar) findViewById(R.id.top_lade);
            top_lade.setVisibility(View.VISIBLE);

            ref = (TextView) findViewById(R.id.ref);
            ref.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {

            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost("http://art-tokarev.de/json/top1.php");

            try {
                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return str;
        }

        @Override
        protected void onPostExecute(String result)
        {
            if(result != null){
                banner_v = (ImageView) findViewById(R.id.banner);
                title_v = (TextView) findViewById(R.id.title);
                teaser_v = (TextView) findViewById(R.id.teaser);
                name_v = (TextView) findViewById(R.id.name);
                firm_title_v = (TextView) findViewById(R.id.firm_title);
                firm_name_v = (TextView) findViewById(R.id.firmname);
                ref_image_v = (ImageView) findViewById(R.id.ref_pic);
                stext_v = (TextView) findViewById(R.id.stext);
                mtext_v = (TextView) findViewById(R.id.mtext);
                fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSend();
                    }
                });


            try {
                JSONArray jArray = new JSONArray(result);

                for (int i=0; i<jArray.length(); i++)
                {
                    json = jArray.getJSONObject(i);
                    jsonId = json.getInt("id");
                    if(jsonId==id){
                        useId = i;}
                }

                json = jArray.getJSONObject(useId);

                title_v.setText(json.getString("title"));
                teaser_v.setText(json.getString("teaser"));
                ref.setVisibility(View.VISIBLE);
                name_v.setText(json.getString("fname")+" "+json.getString("sname"));
                firm_title_v.setText(json.getString("firmtitle"));
                firm_name_v.setText(json.getString("firm"));
                stext_v.setText(json.getString("stext"));
                mtext_v.setText(json.getString("mtext"));

                Picasso.with(context).load(json.getString("banner")).into(banner_v);
                Picasso.with(context).load(json.getString("pic")).into(ref_image_v);


            } catch (JSONException e) {
                e.printStackTrace();
            }
                top_lade.setVisibility(View.GONE);}
            else {setContentView(R.layout.no_top);}
        }
    }

    /*@Override
    protected void onPause()
    {
        super.onPause();
        banner_v = null;
        //title_v = null;
        teaser_v = null;
        //name_v = null;
        firm_title_v = null;
        firm_name_v = null;
        ref_image_v = null;
        stext_v = null;
        mtext_v = null;
        System.gc();
    }*/
}
