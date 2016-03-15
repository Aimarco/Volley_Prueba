package com.example.furwin.volley_prueba.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.furwin.volley_prueba.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by FurWin on 15/03/2016.
 */
public class NewPost extends AppCompatActivity {
    private String urlpost="http://jsonplaceholder.typicode.com/posts/1";
    private Button btnSubmit;
    private EditText edtuserid,edttitle,edtbody;
    @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_post);
        btnSubmit=(Button) findViewById(R.id.btnsubmit);
        edtuserid=(EditText) findViewById(R.id.edtuserid);
        edttitle=(EditText) findViewById(R.id.edttitle);
        edtbody=(EditText) findViewById(R.id.edtbody);
        RequestQueue queue = Volley.newRequestQueue(this);
    btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            putRequest();
        }
    });
    }
private void putRequest(){
    RequestQueue queue = Volley.newRequestQueue(this);
    JSONObject jobjectput=new JSONObject();

    try{
        jobjectput.put("userId",Integer.parseInt(edtuserid.getText().toString()));
        jobjectput.put("title",edttitle.getText().toString());
        jobjectput.put("body",edtbody.getText().toString());
    }catch(Exception e){

    }

    JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.POST,urlpost, jobjectput,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response) {
                    // response
                    Log.d("Response", response.toString());
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("Error.Response", error.toString());
                }
            }
    );

    queue.add(putRequest);
}








}


