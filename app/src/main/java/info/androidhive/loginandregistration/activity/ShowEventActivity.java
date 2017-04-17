package info.androidhive.loginandregistration.activity;


import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowEventActivity extends AppCompatActivity {

    private MaterialBetterSpinner search_category;
    private EditText search_query;
    private Button search_action_button;
    private String chosen_category;
    private ListView list_of_tours;
    private ArrayList<TourItems> upcomingTours;

    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        search_category = (MaterialBetterSpinner) findViewById(R.id.search_category);
        String categories[]= {"name","place","start_date","num_of_days","budget","moderator","contact","details"};
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this,
                                                 android.R.layout.simple_dropdown_item_1line, categories);
        search_category.setAdapter(categoriesAdapter);
        search_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosen_category = parent.getItemAtPosition(position+1).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                chosen_category=search_category.getText().toString();


            }
        });

        search_query = (EditText)findViewById(R.id.search_query);

        upcomingTours = new ArrayList<>();
        upcomingTours.add(new TourItems("Winter tour","Rangamati","28-12-17",10,8000,"Rashik","01673859609","f"));

        list_of_tours = (ListView)findViewById(R.id.listViewTours);
        final customListViewAdapter adaptor = new customListViewAdapter();
        list_of_tours.setAdapter(adaptor);

        search_action_button = (Button)findViewById(R.id.search_confirm);
        search_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), chosen_category+"\n"+search_query.getText().toString(),Toast.LENGTH_LONG).show();
                Refresh(adaptor);
            }
        });



        requestQueue = Volley.newRequestQueue(this);

        Refresh(adaptor);


        list_of_tours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TourItems clicked_tour = upcomingTours.get(position);
                String tour_info = "";
                tour_info = tour_info + "Name : "+clicked_tour.get_name()+"\n";
                tour_info = tour_info + "Location : "+clicked_tour.get_place()+"\n";
                tour_info = tour_info + "Start Date : "+clicked_tour.get_start_date()+"\n";
                tour_info = tour_info + "Duration : "+Integer.toString(clicked_tour.get_num_days())+"\n";
                tour_info = tour_info + "Budget : "+Integer.toString(clicked_tour.get_budget())+"\n";
                tour_info = tour_info + "Moderator : "+clicked_tour.get_moderator()+"\n";
                tour_info = tour_info + "Contact : "+clicked_tour.get_contact()+"\n";
                tour_info = tour_info + "Details : "+clicked_tour.get_details()+"\n";

                for(int i=0;i<100;i++){
                    tour_info = tour_info + "Details : "+clicked_tour.get_details()+"\n";
                }


                new AlertDialog.Builder(ShowEventActivity.this)
                        .setTitle("Tour Information")
                        .setMessage(tour_info)
                        .setPositiveButton("Interested", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton("No,Thanks", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        }).show();


            }
        });



    }

    public class customListViewAdapter extends ArrayAdapter<TourItems>
    {
        public customListViewAdapter()
        {
            super(getApplicationContext(), R.layout.tour_row, upcomingTours);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null)
            {
                v = getLayoutInflater().inflate(R.layout.tour_row, parent, false);
            }
            TextView t1, t2,t3,t4,t5,t6;


            t1 = (TextView) v.findViewById(R.id.tour_name);
            t2 = (TextView) v.findViewById(R.id.location_name);
            t3 = (TextView) v.findViewById(R.id.start_date);
            t4 = (TextView) v.findViewById(R.id.cost);
            t5 = (TextView) v.findViewById(R.id.number_of_days);
            t6 = (TextView) v.findViewById(R.id.contact);


            t1.setText(upcomingTours.get(position).get_name());
            t2.setText(upcomingTours.get(position).get_place());
            t3.setText(upcomingTours.get(position).get_start_date());
            t4.setText(Integer.toString(upcomingTours.get(position).get_budget()));
            t5.setText(Integer.toString(upcomingTours.get(position).get_num_days()));
            t6.setText(upcomingTours.get(position).get_contact());




            return v;
        }
    }

    void Refresh(final customListViewAdapter adaptor)
    {

        StringRequest jor = new StringRequest(Request.Method.GET, AppConfig.URL_SHOW_EVENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "SUCCESS!!! " + response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "FUCK", Toast.LENGTH_LONG).show();
            }
        }) ;

//        JsonArrayRequest jor = new JsonArrayRequest(AppConfig.URL_SHOW_EVENT,
//                new Response.Listener<JSONArray>() {
//
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        int size= response.length();
//                        Toast.makeText(getApplicationContext(),Integer.toString(size),Toast.LENGTH_LONG).show();
//                        upcomingTours.clear();
//                        int len = response.length();
//                        for (int i = 0; i<len; i++)
//                        {
//                            try {
//                                JSONObject j = response.getJSONObject(i);
//                                int id = j.getInt("unique_id");
//                                String name = j.getString("name");
//                                String place = j.getString("place");
//                                String start_date = j.getString("start_date");
//                                int number_of_days = j.getInt("num_of_days");
//                                int budget = j.getInt("budget");
//                                String moderator = j.getString("moderator");
//                                String contact = j.getString("contact");
//                                String details = j.getString("details");
//                                upcomingTours.add(new TourItems(name,place,start_date,number_of_days,
//                                        budget,moderator,contact,details));//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        adaptor.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
        requestQueue.add(jor);
    }


}
