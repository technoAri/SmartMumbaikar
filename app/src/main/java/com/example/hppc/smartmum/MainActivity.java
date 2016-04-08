package com.example.hppc.smartmum;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends Activity {
    public static final String JSON_URL = "http://smartmumbaikar.com/sharingo/publicgrouplist.php?gid=2";

    private Button refreshButton;

    private ListView listView;

    TextView txtView;

    EditText editTextSearch;
    String formattedDate;

    CustomList cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // buttonGet = (Button) findViewById(R.id.buttonGet);
        refreshButton = (Button) findViewById(R.id.refresh_button);
        //buttonGet.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setTextFilterEnabled(true);
        txtView = (TextView) findViewById(R.id.textView_time);
        editTextSearch = (EditText) findViewById(R.id.editText_search);
        sendRequest();

        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cl.getFilter().filter(s);
                cl.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.INVISIBLE);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cl.notifyDataSetChanged();
                    }
                });
                listView.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"You have refreshed the items",Toast.LENGTH_SHORT).show();
            }
        });
        showTime();
    }

    private void showTime() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("IST"));
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        formattedDate = df.format(c.getTime());
        if(formattedDate == ("12:00:00"))
        {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cl.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),"Listview updated autometically",Toast.LENGTH_SHORT).show();
                }
            });
        }
        txtView.setText(formattedDate);
        txtView.setTextSize(10);
    }

    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String json){
        VollyController pj = new VollyController(json);
        pj.parseJSON();
        cl = new CustomList(this, VollyController.ids,VollyController.names,VollyController.admin_id,VollyController.time);
        listView.setAdapter(cl);
    }
}
