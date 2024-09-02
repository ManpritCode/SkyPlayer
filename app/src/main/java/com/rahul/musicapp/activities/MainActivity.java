package com.rahul.musicapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stevenmwesigwa.musicapp.R;
import com.rahul.musicapp.adapters.NavigationDrawerAdapter;
import com.rahul.musicapp.fragments.MainScreenFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     * Initialize our DrawerLayout object
     * The object is made static so that in our NavigationDrawerAdapter.java
     * so that when we click an item, we can refer to this 'drawerLayout' and close all
     * drawers associated with this
     * "drawerLayout" (To automatically close the 'nav drawer' when an item is clicked).
     */
    public static DrawerLayout drawerLayout = null;


    private final int[] imagesForNavigationDrawer = {R.drawable.navigation_allsongs, R.drawable.navigation_favorites, R.drawable.navigation_settings, R.drawable.navigation_aboutus};

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        List<String> navigationDrawerIconsList;
        super.onCreate(savedInstanceState);
        navigationDrawerIconsList = Arrays.asList("All Songs", "Favorites", "Settings", "About Us");
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // Initialize our DrawerLayout object with a value
        MainActivity.drawerLayout = findViewById(R.id.drawerLayout);
        // Make an object of ActionBarDrawerToggle class
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, MainActivity.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        MainActivity.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        // Animate the 'hamburger icon' whenever it's clicked on. i.e from '3 lines' to an 'arrow'
        actionBarDrawerToggle.syncState();


        final MainScreenFragment mainScreenFragment = new MainScreenFragment();
        /**
         * A manager to manage different fragments, their inception, their layouts, animations if any
         *  and all the other attributes associated with the fragment.
         *  We access the FragmentManager by mentionng the context. (this)
         *  To start a series of operations on the Fragments
         *  associated with this FragmentManager - beginTransaction().
         *  To commit all / apply the changes made - commit()
         */
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.detailsFragment, mainScreenFragment, "MainScreenFragment")
                .commit();

        NavigationDrawerAdapter navigationDrawerAdapter = new NavigationDrawerAdapter(navigationDrawerIconsList, imagesForNavigationDrawer, this);
        /**
         * Set the adapter to be adaptable to change.
         * The adapter would get updated whenever there is a change in the list. i.e If an item is
         * added or deleted, the adapter will get notified automatically. Hence will
         * make the necessary changes.
         */
        navigationDrawerAdapter.notifyDataSetChanged();

        // Define Recycler View
        RecyclerView navigationRecyclerView = findViewById(R.id.navigationRecyclerView);
        // Setup LayoutManager - Is responsible for measuring and positioning 'item views' with in a recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        navigationRecyclerView.setLayoutManager(layoutManager);
        navigationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        navigationRecyclerView.setAdapter(navigationDrawerAdapter);
        navigationRecyclerView.setHasFixedSize(true);
    }
}
