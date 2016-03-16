package com.example.furwin.volley_prueba.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.furwin.volley_prueba.R;
import com.example.furwin.volley_prueba.Keys.JsonKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FurWin on 15/03/2016.
 */
public class NewPost extends AppCompatActivity {
    private String urlpost = "La API del servidor que quieras";
    private Button btnSubmit;
    private EditText edtuserid, edttitle, edtbody;
    private JsonKeys keys = new JsonKeys();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_post);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        edtuserid = (EditText) findViewById(R.id.edtuserid);
        edttitle = (EditText) findViewById(R.id.edttitle);
        edtbody = (EditText) findViewById(R.id.edtbody);
        RequestQueue queue = Volley.newRequestQueue(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putRequest();
            }
        });
    }

    private void putRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject jobjectput = new JSONObject();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlpost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(NewPost.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                //Esta parte funciona por que con el JSON que he trabajado esta formada con esas claves
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String jsonerr;
                        NetworkResponse response = error.networkResponse;
                        if (response.statusCode!=200) {
                                    jsonerr = new String(response.data);
                                    jsonerr ="Error: "+trimMessage(jsonerr,keys.getStcode())+" "+trimMessage(jsonerr, "message");
                                 if (jsonerr != null) displayMessage(jsonerr);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", edttitle.getText().toString());
                params.put("password", edtbody.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    /*
        this method take the JSONObject response and get the key you specify
        returns the text of the specified key
    */
    public String trimMessage(String json, String key) {
        String trimmedString = null;
        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
    //This method displays a message in the main screen
    public void displayMessage(String toastString) {
        Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
    }
}


