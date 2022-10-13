package com.example.sound_soul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class homepage extends AppCompatActivity {
    ListView lv;
    String[] items;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        lv = (ListView) findViewById(R.id.ListView);
        runPermission();
        Toolbar tool = (Toolbar)findViewById(R.id.ToolBar);
        setSupportActionBar(tool);
        nav = (NavigationView)findViewById(R.id.Nav);
        drawer = (DrawerLayout)findViewById(R.id.Drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawer,tool,R.string.OPEN,R.string.CLOSE);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"Home page is clicked",Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_settings:
                        Toast.makeText(getApplicationContext(),"setting is clicked is clicked",Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(getApplicationContext(),"Logout is clicked",Toast.LENGTH_LONG).show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
    }

    public void runPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse)
            {
                displaySongs();
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    public ArrayList<File> FindSongs(File file) {
        ArrayList<File> list = new ArrayList<>();
        File[] files = file.listFiles();
        for(File ss:files)
        {
            if(ss.isDirectory() && !ss.isHidden())
            {
                list.addAll(FindSongs(ss));
            }
            else if(ss.getName().endsWith(".mp3") || ss.getName().endsWith(".WAV"))
            {
                list.add(ss);
            }
        }
        return list;
    }
    public void displaySongs()
    {
        final ArrayList<File> songs = FindSongs(Environment.getExternalStorageDirectory());
        System.out.println(songs.size());
        items = new String[songs.size()];
        for(int x=0;x<songs.size();x++)
        {
            items[x] = songs.get(x).getName().toString().replace(".mp3","").replace(".wav","");
        }
        customAdapter ca = new customAdapter();
        lv.setAdapter(ca);
    }
    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list_item,null);
            TextView song_name = view.findViewById(R.id.song_name);
            song_name.setSelected(true);
            song_name.setText(items[position]);
            return view;
        }
    }
}

