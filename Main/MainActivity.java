package com.example.furwin.volley_prueba.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.furwin.volley_prueba.R;
import com.example.furwin.volley_prueba.Keys.JsonKeys;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private TextView userid,id,title,body;
    private String urlget = "http://jsonplaceholder.typicode.com/posts?userId=1";
    private String salida;
    private ArrayList<Post> listaposts;
    private ListView lista;
    private Button btnpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonArrayRequest jsonarray = new JsonArrayRequest(urlget, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO Auto-generated method stub
                listaposts=creaArrayPosts(response);
                postView();
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, ""+listaposts.size(), Toast.LENGTH_SHORT).show();




            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error", error.getMessage());
            }
        });
        queue.add(jsonarray);


    }





    public ArrayList<Post> creaArrayPosts(JSONArray datos) {
        ArrayList<Post> listaPosts = new ArrayList<>();
        JsonKeys key = new JsonKeys();
        for (int i = 0; i < datos.length(); i++) {
            Post p = new Post();
            try {
                p.setId(datos.getJSONObject(i).getInt(key.getUserId()));
                p.setUserId(datos.getJSONObject(i).getInt(key.getUserId()));
                p.setTitle(datos.getJSONObject(i).getString(key.getTitle()));
                p.setBody(datos.getJSONObject(i).getString(key.getBody()));

                listaPosts.add(p);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        return listaPosts;
    }


    //crear la vista para el listview



    public void postView() {
        ArrayAdapter<Post> listaposts = new myListAdapter();
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(listaposts);
    }


    private class myListAdapter extends ArrayAdapter<Post>{

        public myListAdapter(){
            super(MainActivity.this,R.layout.layout_item_view,listaposts);
                    }
        public View getView(int position,View convertView,ViewGroup parentView){
            View itemView=convertView;
            if(itemView==null)
                itemView=getLayoutInflater().inflate(R.layout.layout_item_view,parentView,false);
            userid=(TextView) itemView.findViewById(R.id.txtuserid);
            id=(TextView) itemView.findViewById(R.id.txtid);
            title=(TextView) itemView.findViewById(R.id.txttitle);
            body=(TextView) itemView.findViewById(R.id.txtbody);

            //recoger post actual


            Post currentPost=listaposts.get(position);

            //asignar valores

            userid.setText("UserID: "+String.valueOf(currentPost.getUserId()));
            id.setText("ID: "+String.valueOf(currentPost.getId()));
            title.setText(currentPost.getTitle());
            body.setText(currentPost.getBody());
            return itemView;

        }

    }

}
