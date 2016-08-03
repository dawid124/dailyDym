package pl.webd.dawid124.dailygym.layout.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import pl.webd.dawid124.dailygym.R;
import pl.webd.dawid124.dailygym.database.converter.MyDate;
import pl.webd.dawid124.dailygym.database.engine.THistoryEngine;
import pl.webd.dawid124.dailygym.layout.fragment.HomeDayFragment;
import pl.webd.dawid124.dailygym.static_value.AppValue;

public class HomeActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private final Context ctx = this;
    private boolean toRefresh = false;
    private int position;
    private int mPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyDate.setToday();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(AppValue.NUM_PAGES);
        mPager.setCurrentItem(AppValue.NUM_PAGES / 2);
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
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.nav_my_training) {
            startActivity(new Intent(this, TrainingActivity.class));
        } else if (id == R.id.nav_exercise) {
            startActivity(new Intent(this, ExerciseActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            HomeDayFragment homeDayFragment = new HomeDayFragment().create(position, ctx);
            return homeDayFragment;
        }

        @Override
        public int getCount() {
            return AppValue.NUM_PAGES;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle = getIntent().getExtras();
                long planId = data.getLongExtra(AppValue.PLAN_ID,-1);
                String date = data.getStringExtra(AppValue.DATE);
                mPageNumber = data.getIntExtra(AppValue.PAGE_NUMBER, AppValue.NUM_PAGES / 2);
                THistoryEngine historyEngine = new THistoryEngine(ctx);

                if (historyEngine.addNewTrainingToHistory(planId, date)) {
                    this.toRefresh = true;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (toRefresh) {
            /*
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag(name);
            ft.detach(((ScreenSlidePagerAdapter) mPagerAdapter).getItem(mPageNumber));
            ft.attach(((ScreenSlidePagerAdapter) mPagerAdapter).getItem(mPageNumber));
            ft.commit();

            mPager.getAdapter();
            MyAdapter adapter = ((MyAdapter)mViewPager.getAdapter());
            MyFragment fragment = adapter.getFragment(index);
;

            FragmentPagerAdapter adapter = (ScreenSlidePagerAdapter)mPager.getAdapter();

                 ((HomeDayFragment) ((ScreenSlimPdePagerAdapter) mPagerAdapter).getItem(mPageNumber)).reload();
            */
        }
    }

}
