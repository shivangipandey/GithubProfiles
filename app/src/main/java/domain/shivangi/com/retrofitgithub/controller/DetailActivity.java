package domain.shivangi.com.retrofitgithub.controller;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import domain.shivangi.com.retrofitgithub.R;

public class DetailActivity extends AppCompatActivity{

    TextView link, username;
    Toolbar mActionBarToolBar;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.user_image_header);
        username = (TextView) findViewById(R.id.username);
        link = (TextView) findViewById(R.id.githublink);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("login");
        String avatar_url = bundle.getString("avatarUrl");
        String Link = bundle.getString("htmlUrl");

        username.setText(name);
        link.setText(Link);
        Linkify.addLinks(link,Linkify.WEB_URLS);

        Glide.with(this)
                .load(avatar_url)
                .placeholder(R.drawable.load)
                .into(imageView);

        getSupportActionBar().setTitle("Details");

    }
    private Intent createSharedForcaseIntent(){
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("login");
        String avatar_url = bundle.getString("avatarUrl");
        String Link = bundle.getString("htmlUrl");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer "+ username +", "+link)
                .getIntent();
        return shareIntent;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createSharedForcaseIntent());
        return true;
    }
}
