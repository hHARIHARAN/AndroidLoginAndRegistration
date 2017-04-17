package info.androidhive.loginandregistration.activity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;


import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;
import info.androidhive.loginandregistration.app.AppConfig;

public class AddEventActivity extends AppCompatActivity {

    public static String EXTRA_MESSAGE="MMMMMM";
    private RequestQueue requestQueue;
    //private Calendar myCalender;

    private EditText name ;
    private EditText location;
    public static EditText start_date ;
    private EditText duration ;
    private EditText budget ;
    private EditText moderator ;
    private EditText contact ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        name = (EditText) findViewById(R.id.editTextName);
        location = (EditText) findViewById(R.id.editTextPlace);
        start_date = (EditText) findViewById(R.id.editTextStartDate);
        duration = (EditText) findViewById(R.id.editTextDuration);
        budget = (EditText) findViewById(R.id.editTextBudget);
        moderator = (EditText) findViewById(R.id.editTextModerator);
        contact = (EditText) findViewById(R.id.editTextContact);

        requestQueue = Volley.newRequestQueue(this);


        final EditText input_details = (EditText) findViewById(R.id.editTextDetails);
        input_details.setMovementMethod(new ScrollingMovementMethod());


        Button submit_button = (Button) findViewById(R.id.submit_button);


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest jor = new StringRequest(Request.Method.POST, AppConfig.URL_ADD_EVENT,
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
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> p = new HashMap<>();
                        p.put("name", name.getText().toString());
                        p.put("place", location.getText().toString());
                        p.put("start_date", start_date.getText().toString());
                        p.put("num_of_days", duration.getText().toString());
                        p.put("budget", budget.getText().toString());
                        p.put("moderator", moderator.getText().toString());
                        p.put("contact", contact.getText().toString());
                        p.put("details", input_details.getText().toString());
                        return p;
                    }
                };
                requestQueue.add(jor);

            }


        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            String date = day+"-"+month+"-"+year;
            start_date.setText(date);
        }
    }



    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}

