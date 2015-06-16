package de.artmedia.artyom.trendevent_screentest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import java.net.MalformedURLException;
import java.net.URL;

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
    ProgressBar banner_bar;
    private String stringID;
    private int id;
    private int useId;
    private int jsonId;
    JSONObject json;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String str = "";
        HttpResponse response;
        HttpClient myClient = new DefaultHttpClient();
        HttpPost myConnection = new HttpPost("http://art-tokarev.de/json/top1.php");

        id = this.getIntent().getExtras().getInt("item");

        try {
            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
            setContentView(R.layout.top_main_layout);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            setContentView(R.layout.no_top);
        } catch (IOException e) {
            e.printStackTrace();
            setContentView(R.layout.no_top);
        }


        banner_v = (ImageView) findViewById(R.id.banner);
        title_v = (TextView) findViewById(R.id.title);
        teaser_v = (TextView) findViewById(R.id.teaser);
        name_v = (TextView) findViewById(R.id.name);
        firm_title_v = (TextView) findViewById(R.id.firm_title);
        firm_name_v = (TextView) findViewById(R.id.firmname);
        ref_image_v = (ImageView) findViewById(R.id.ref_pic);
        stext_v = (TextView) findViewById(R.id.stext);
        mtext_v = (TextView) findViewById(R.id.mtext);
        banner_bar = (ProgressBar) findViewById(R.id.top_banner_progress);

        banner_bar.setVisibility(View.GONE);

        try {
            JSONArray jArray = new JSONArray(str);

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
            name_v.setText(json.getString("fname")+" "+json.getString("sname"));
            firm_title_v.setText(json.getString("firmtitle"));
            firm_name_v.setText(json.getString("firm"));
            stext_v.setText(json.getString("stext"));
            mtext_v.setText(json.getString("mtext"));


            //new downloadImagesBanner().execute(json.getString("banner"));
            //new downloadImagesPic().execute(json.getString("pic"));
            Picasso.with(context).load(json.getString("banner")).into(banner_v);
            Picasso.with(context).load(json.getString("pic")).into(ref_image_v);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /*@Override
    protected void onPause()
    {
        super.onPause();

    }*/

    @Override
    protected void onPause()
    {
        super.onPause();
        banner_v = null;
        title_v = null;
        teaser_v = null;
        name_v = null;
        firm_title_v = null;
        firm_name_v = null;
        ref_image_v = null;
        stext_v = null;
        mtext_v = null;
        System.gc();
        finish();
    }

    @Override
    protected void onDestroy()
    {
        Log.d("ON_DESTROY", "destroyed");
        super.onDestroy();
    }

    /*//Die Bilder werden im hintergrund geladen. Wenn die Internetverbindung langsam ist, koennen die Textelemente vorher schon geladen werden.
    private class downloadImagesBanner extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            banner_bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String banner = urls[0];
            Bitmap bmp_banner = null;

            URL bannerUrl = null;
            try {
                bannerUrl = new URL(banner);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                bmp_banner = BitmapFactory.decodeStream(bannerUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp_banner;
        }

        @Override
        protected void onPostExecute(Bitmap banner)
        {
            if(banner != null)
            {
                banner_v.setImageBitmap(banner);
                banner_bar.setVisibility(View.GONE);
            }
        }
    }

    private class downloadImagesPic extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            String pic = urls[0];
            Bitmap bmp_pic = null;

            URL picUrl = null;
            try {
                picUrl = new URL(pic);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                bmp_pic = BitmapFactory.decodeStream(picUrl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp_pic;
        }

        @Override
        protected void onPostExecute(Bitmap pic)
        {
            if(pic != null)
            {
                ref_image_v.setImageBitmap(pic);
            }
        }
    }*/
}
