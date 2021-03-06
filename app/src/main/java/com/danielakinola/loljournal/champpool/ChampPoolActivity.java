package com.danielakinola.loljournal.champpool;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielakinola.loljournal.R;
import com.danielakinola.loljournal.ViewModelFactory;
import com.danielakinola.loljournal.championselect.ChampionSelectActivity;
import com.danielakinola.loljournal.data.models.Champion;
import com.danielakinola.loljournal.databinding.ActivityChampPoolBinding;
import com.danielakinola.loljournal.matchups.MatchupsActivity;
import com.danielakinola.loljournal.utils.SnackbarMessage;
import com.danielakinola.loljournal.utils.SnackbarUtils;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public class ChampPoolActivity extends DaggerAppCompatActivity {

    public static final int REQUEST_EDIT_CHAMP_POOL = RESULT_FIRST_USER + 1;
    public static final String PLAYER_CHAMPION_ID = "PLAYER_CHAMPION_ID";
    private ChampPoolViewModel champPoolViewModel;
    @Inject
    LanePagerAdapter lanePagerAdapter;
    @Inject
    ViewModelFactory viewModelFactory;
    private int lane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        if (savedInstanceState != null) {
            lane = savedInstanceState.getInt(ChampionSelectActivity.LANE, 0);
        } else {
            lane = getIntent().getIntExtra(ChampionSelectActivity.LANE, 0);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champ_pool);
        setupViewModel();
        setupDataBinding();
        setupViewPager();
        setupFAB();
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.fab_change_champ_pool);
        fab.setOnClickListener(v -> champPoolViewModel.editChampPool());
    }


    private void setupDataBinding() {
        ActivityChampPoolBinding activityChampPoolBinding = ActivityChampPoolBinding.inflate(getLayoutInflater());
        activityChampPoolBinding.setViewmodel(champPoolViewModel);
    }

    private void setupViewModel() {
        ImageView icon = findViewById(R.id.img_lane_icon);
        TextView title = findViewById(R.id.text_lane_title);
        View root = findViewById(R.id.coordinator_layout);

        champPoolViewModel = ViewModelProviders.of(this, viewModelFactory).get(ChampPoolViewModel.class);
        champPoolViewModel.setCurrentLane(lane);
        champPoolViewModel.getCurrentLane().observe(this, newLane -> lane = newLane);
        champPoolViewModel.getLaneTitle().observe(this, title::setText);
        champPoolViewModel.getLaneIcon().observe(this, icon::setImageResource);
        champPoolViewModel.getEditChampPoolEvent().observe(this, aVoid -> openChampionSelect());
        champPoolViewModel.getChampionDetailEvent().observe(this, this::openChosenChampion);
        champPoolViewModel.getDeleteChampionEvent().observe(this, this::showDeleteDialog);
        champPoolViewModel.getSnackbarMessage().observe(this,
                (SnackbarMessage.SnackbarObserver) message ->
                        SnackbarUtils.showSnackbar(
                                root, getString(message, champPoolViewModel.getMessageArgument())));
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(lanePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                champPoolViewModel.setCurrentLane(position);
            }
        });
        viewPager.setCurrentItem(lane, true);
    }

    private void showDeleteDialog(Champion champion) {
        String championTitle = champPoolViewModel.getLaneTitle().getValue() + " " + champion.getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppDialogTheme);
        AlertDialog dialog = builder.setTitle(getString(R.string.delete_dialog_title, championTitle))
                .setMessage(getString(R.string.delete_dialog_champool_message, championTitle))
                .setPositiveButton(R.string.delete, (dialog1, which) -> champPoolViewModel.deleteChampion(champion))
                .setNegativeButton(R.string.cancel, (dialog12, which) -> {
                })
                .create();

        dialog.setOnShowListener(dialog13 -> dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(getResources().getColor(R.color.colorAccent)));

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_champ_pool, menu);
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

    public void openChosenChampion(String championId) {
        Intent intent = new Intent(this, MatchupsActivity.class);
        intent.putExtra(PLAYER_CHAMPION_ID, championId);
        startActivity(intent);
    }

    public void openChampionSelect() {
        Intent intent = new Intent(this, ChampionSelectActivity.class);
        intent.putExtra(ChampionSelectActivity.LANE, lane);
        startActivityForResult(intent, REQUEST_EDIT_CHAMP_POOL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        champPoolViewModel.onEdit(resultCode);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ChampionSelectActivity.LANE, lane);
    }

    public static class LanePagerAdapter extends FragmentPagerAdapter {

        private String[] names;

        @Inject
        public LanePagerAdapter(FragmentManager fm, @Named("laneTitles") String[] names) {
            super(fm);
            this.names = names;
        }

        @Override
        public Fragment getItem(int position) {
            return ChampPoolFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return names[position];
        }
    }
}
