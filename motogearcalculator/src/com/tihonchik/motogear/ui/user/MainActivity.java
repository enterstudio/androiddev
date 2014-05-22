package com.tihonchik.motogear.ui.user;

import com.tihonchik.motogear.R;
import com.tihonchik.motogear.ui.BaseActivity;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends BaseActivity {

	private String[] mMenuItems = new String[2];
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	@Override
	protected Context getContextforBase() {
		return MainActivity.this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			Fragment f = new CustomSetupFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.body, f, "BlogDisplayFragment").commit();
		}

		mMenuItems[0] = "Pick Motorcycle";
		mMenuItems[1] = "Custom Parameters";

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mTitle = mDrawerTitle = getTitle();

	}

	@Override
	public void onNewIntent(Intent intent) {
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		// Create a new fragment and specify the planet to show based on
		// position
		/*
		 * Fragment fragment = new PlanetFragment(); Bundle args = new Bundle();
		 * args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		 * fragment.setArguments(args);
		 * 
		 * // Insert the fragment by replacing any existing fragment
		 * FragmentManager fragmentManager = getFragmentManager();
		 * fragmentManager.beginTransaction() .replace(R.id.content_frame,
		 * fragment) .commit();
		 */
		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mMenuItems[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
