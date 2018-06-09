package domain.shivangi.com.retrofitgithub.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import domain.shivangi.com.retrofitgithub.ItemAdapter;
import domain.shivangi.com.retrofitgithub.R;
import domain.shivangi.com.retrofitgithub.api.Client;
import domain.shivangi.com.retrofitgithub.api.Service;
import domain.shivangi.com.retrofitgithub.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    TextView disconnected;
    private User item;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeRefreshLayout;

    List<User> items;
    ItemAdapter itemAdapter;
    ImageButton search_btn;
    EditText name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews("Fetching Github Users...","");
        name = (EditText)findViewById(R.id.name);
        search_btn = (ImageButton)findViewById(R.id.search_btn);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                name.setText("");
                Toast.makeText(MainActivity.this, "Github Users Refreshed", Toast.LENGTH_SHORT).show();

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                if(n.isEmpty())
                    Toast.makeText(MainActivity.this, "Enter username to search", Toast.LENGTH_SHORT).show();
                else {
                    initViews("Getting "+n,n);
                }
            }
        });

    }

    private void initViews(String text, String n){
        pd = new ProgressDialog(this);
        pd.setMessage(text);
        pd.setCancelable(false);
        pd.show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        if(n.isEmpty())
            loadJSON();
        else
            loadJSON(n);

    }

    private void loadJSON(){
        disconnected = (TextView)findViewById(R.id.diconnected);
        try{
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);

            //Call<ItemResponse> call = apiService.getUsers();
            Call<List<User>> call = apiService.getUsers();

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    items = response.body();
                    int size = items.size();
                    itemAdapter = new ItemAdapter(getApplicationContext(), items);
                    recyclerView.setAdapter(itemAdapter);
                    recyclerView.smoothScrollToPosition(0);
                    swipeRefreshLayout.setRefreshing(false);
                    pd.hide();
                    Toast.makeText(MainActivity.this, "Showing top 100 results", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                    String p = t.getMessage();
                    Toast.makeText(MainActivity.this, "Error Fetching Data! ", Toast.LENGTH_SHORT).show();
                    disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }
            });
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSON(String username){
        disconnected = (TextView)findViewById(R.id.diconnected);
        try{
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<User> callSingle = apiService.getSingleUser(username);

            callSingle.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(items != null && itemAdapter != null) {
                        item = response.body();
                        if(items.contains(item))
                            items.remove(item);
                        items.add(0,item);
                        recyclerView.setAdapter(new ItemAdapter(getApplicationContext(),items));
                        recyclerView.invalidate();
                        pd.hide();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                    String p = t.getMessage();
                    Toast.makeText(MainActivity.this, "Error Fetching Data! ", Toast.LENGTH_SHORT).show();
                    disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }
            });
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
