package de.artmedia.artyom.trendevent_screentest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    ImageView banner_v;
    TextView title_v;
    TextView teaser_v;
    TextView name_v;
    TextView firm_title_v;
    TextView firm_name_v;
    ImageView ref_image_v;
    TextView stext_v;
    TextView mtext_v;
    private String stringID;
    private int id;
    private int useId;
    private int jsonId;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.top_main_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject json;
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

            //download Banner
            URL bannerUrl = new URL(json.getString("banner"));
            Bitmap bmp_banner = BitmapFactory.decodeStream(bannerUrl.openConnection().getInputStream());
            banner_v.setImageBitmap(bmp_banner);

            //download Referentenbild
            URL refUrl = new URL(json.getString("pic"));
            Bitmap bmp_ref = BitmapFactory.decodeStream(refUrl.openConnection().getInputStream());
            ref_image_v.setImageBitmap(bmp_ref);


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop()
    {
        super.onStop();
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
    }

}
