package com.zhaoyanyang.multiplenewsreader;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhaoyanyang.multiplenewsreader.ContextUtils.hadread;
import com.zhaoyanyang.multiplenewsreader.CustomizeView.DropListView;

import org.litepal.crud.DataSupport;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ImageView tianqiImageView;
    private boolean baitian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NewsFragment()).commit();



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        setupDrawerContent(mNavigationView);
        baitian=true;
//        tianqiImageView=mDrawerLayout.findViewById(R.id.imageView);
//        tianqiImageView.setOnClickListener((V)->{
//            baitian=!baitian;
//            if (baitian){
//                tianqiImageView.setImageResource(R.drawable.ic_wb_sunny_black_24dp);
//            }else {
//                tianqiImageView.setImageResource(R.drawable.ic_brightness_2_black_24dp);
//            }
//        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),
                    item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }
        if (id==R.id.action_anti){

            Intent overnumber=new Intent(getApplicationContext(), AntiindulgenceActivity.class);
            overnumber.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(overnumber);

            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
//                        Toast.makeText(getApplicationContext(),
//                                menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        switch (menuItem.getItemId()){
                            case R.id.navigation_news:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new NewsFragment()).commit();
                                mToolbar.setTitle("新闻");
                                break;
                            case R.id.navigation_trump:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new TrumpFragment()).commit();
                                mToolbar.setTitle("Today Trump");
                                break;

                            case R.id.navigation_read:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new readerlaterFragment()).commit();
                                mToolbar.setTitle("稍后阅读");
                                break;

                            case  R.id.navigation_time:
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new StatisticsFragment()).commit();
                                mToolbar.setTitle("数据统计/用户画像生成");
                                break;

                            case R.id.navigation_login:
                                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                                startActivity(intent);
                                break;

                            default:
                                break;
                        }

                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;

                    }
                });
    }


}
