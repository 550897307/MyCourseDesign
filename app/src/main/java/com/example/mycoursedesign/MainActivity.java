package com.example.mycoursedesign;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.Main_list_displayUtil;
import com.example.utils.PermissionActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static boolean flag = true;
    Animation animation;
    ArrayList<Main_list_displayUtil> arrayList_main_list_display = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        PermissionActivity permissionActivity = new PermissionActivity();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animation = AnimationUtils.loadAnimation(this, R.anim.my_anim);
        animation.setInterpolator(new LinearInterpolator());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag)
                {
                    fab.startAnimation(animation);
                    flag = false;
                }
                else
                {
                    fab.clearAnimation();
                    flag = true;
                }
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button button_myMusic_more = new Button(this);
        button_myMusic_more.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MyMusicActivity.class);
                        intent.putExtra("id_textView_title", "我的音乐");
                        startActivity(intent);
                    }
                }
        );
        arrayList_main_list_display.add(new Main_list_displayUtil("我的音乐", "啦啦啦", "李一楠",
        "月亮之上", "汪疯", button_myMusic_more));
        arrayList_main_list_display.add(new Main_list_displayUtil("ni的音乐", "kekeek", "李一楠",
                "月亮之上", "汪疯", null));
        Main_list_displayAdapter main_list_displayAdapter = new Main_list_displayAdapter();
        ListView listView = findViewById(R.id.id_content_main_list);
        listView.setAdapter(main_list_displayAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    定义主界面显示适配器
    private class Main_list_displayAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return arrayList_main_list_display.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList_main_list_display.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Main_list_displayUtil main_list_displayUtil = (Main_list_displayUtil) getItem(position);
        if (convertView == null)
        {
            convertView = getLayoutInflater().inflate(R.layout.main_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.button_more = convertView.findViewById(R.id.id_button_more);
            viewHolder.textView = convertView.findViewById(R.id.id_textView_main);
            viewHolder.id_textView_left_title = convertView.findViewById(R.id.id_textView_left_title);
            viewHolder.id_textView_left_signer = convertView.findViewById(R.id.id_textView_left_signer);
            viewHolder.id_textView_right_title = convertView.findViewById(R.id.id_textView_right_title);
            viewHolder.id_textView_right_signer = convertView.findViewById(R.id.id_textView_right_signer);
            viewHolder.imageView_left = convertView.findViewById(R.id.id_imageView_left);
            viewHolder.imageView_right = convertView.findViewById(R.id.id_imageView_right);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(main_list_displayUtil.getName());
        viewHolder.id_textView_left_signer.setText(main_list_displayUtil.getSong1_singer());
        viewHolder.id_textView_left_title.setText(main_list_displayUtil.getSong1_title());
        viewHolder.id_textView_right_signer.setText(main_list_displayUtil.getSong2_singer());
        viewHolder.id_textView_right_title.setText(main_list_displayUtil.getSong2_title());
        viewHolder.button_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main_list_displayUtil.getButton() != null)
                {
                    main_list_displayUtil.getButton().callOnClick();
                }
            }
        });

        return convertView;
    }
    private class ViewHolder {
        TextView textView;
        TextView id_textView_left_title, id_textView_left_signer;
        TextView id_textView_right_title, id_textView_right_signer;
        ImageView imageView_left, imageView_right;
        Button button_more;
    }
}
}
