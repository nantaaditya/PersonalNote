package com.android.pramuditya.personalnote;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.pramuditya.personalnote.fragment.FragmentCreateNote;
import com.android.pramuditya.personalnote.fragment.FragmentCreateTag;
import com.android.pramuditya.personalnote.fragment.FragmentListNote;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

public class MainActivity extends AppCompatActivity {
    //Bottom Navigation
    private AHBottomNavigation bottomNavigation;
    private AHBottomNavigationItem create, list, tag;
    private String tabMenu[] = {"Create", "List", "Tags"};
    //Fragment
    private FragmentManager fragmentManager;
    private FragmentCreateNote fragmentCreateNote;
    private FragmentCreateTag fragmentCreateTag;
    private FragmentListNote fragmentListNote;
    //Drawable (ICON)
    private Drawable pencilIcon, listIcon, tagIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeElement();
        this.createBottomNavigation();
        this.initializeFragment();
    }

    //INISIALISASI ELEMEN
    private void initializeElement() {
        this.pencilIcon = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_pencil_square_o)
                .color(Color.WHITE);
        this.listIcon = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_list)
                .color(Color.WHITE);
        this.tagIcon = new IconicsDrawable(this)
                .icon(FontAwesome.Icon.faw_tags)
                .color(Color.WHITE);

        this.fragmentCreateNote = new FragmentCreateNote();
        this.fragmentCreateTag = new FragmentCreateTag();
        this.fragmentListNote = new FragmentListNote();
    }

    // BOTTOM NAVIGATION
    private void createBottomNavigation() {
        this.bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        this.create = new AHBottomNavigationItem(tabMenu[0], this.pencilIcon);
        this.list = new AHBottomNavigationItem(tabMenu[1], this.listIcon);
        this.tag = new AHBottomNavigationItem(tabMenu[2], this.tagIcon);

        this.bottomNavigation.addItem(this.create);
        this.bottomNavigation.addItem(this.list);
        this.bottomNavigation.addItem(this.tag);

        this.bottomNavigation.setCurrentItem(0);

        this.bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#2196F3"));
        this.bottomNavigation.setAccentColor(Color.parseColor("#FFFFFF"));
        this.bottomNavigation.setInactiveColor(Color.parseColor("#BDBDBD"));
        this.bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        this.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                showFragment(position);
                return true;
            }
        });
        this.bottomNavigation.setColoredModeColors(Color.WHITE, Color.parseColor("#BDBDBD"));
        this.bottomNavigation.setBehaviorTranslationEnabled(true);
    }

    //INISIALISASI FRAGMENT AWAL
    private void initializeFragment() {
        this.fragmentManager = this.getSupportFragmentManager();
        this.fragmentManager.beginTransaction()
                .add(R.id.fragment_wrapper,
                        this.fragmentCreateNote,
                        "fCreateNote")
                .commit();
    }

    //TAMPILKAN FRAGMENT YANG AKTIF
    private void showFragment(int position) {
        if (position == 0)
            this.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_wrapper,
                            this.fragmentCreateNote,
                            "fCreateNote")
                    .commit();
        else if (position == 1) {
            this.fragmentManager.beginTransaction()
                    .replace(R.id.fragment_wrapper,
                            this.fragmentListNote,
                            "fListNote")
                    .commit();
        } else {
            if (position == 2) {
                this.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_wrapper,
                                this.fragmentCreateTag,
                                "fCreateTag")
                        .commit();
            }
        }
    }

}
