package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ivara.aravi.echo.R;
import com.ivara.aravi.echo.ShoutOut.Shouter;

public class ShoutRepo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shout_repo);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_shout_repo);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager_shout);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), ShoutRepo.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.shout_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, " Shout Out To The World And Lets Unite !", Snackbar.LENGTH_LONG)
                        .setAction("Action", SnackIntent()).show();
            }
        });

    }

    private View.OnClickListener SnackIntent() {
        startActivity(new Intent(getApplication(),Shouter.class));
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shoutinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.shoutmenuinfo)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        String[] tabTitles = new String[]{"Let's Unite","Happenings","Humanity"};
        Context context;

        public PagerAdapter(FragmentManager fm,  Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0 : return new UniteFragment(getApplicationContext());

                case 1 : return new HappeningsFragment(getApplicationContext());//HappeningsFragment();

                case 2 : return new HumanityFragment(getApplicationContext());//HumanityFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        public View getTabView(int position){

            View tab = LayoutInflater.from(ShoutRepo.this).inflate(R.layout.tab_for_shout_custom, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text_shout);
            tv.setText(tabTitles[position]);
            return tab;

        }

    }

}
