package com.danielakinola.loljournal.champpool;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.res.TypedArray;

import com.danielakinola.loljournal.R;
import com.danielakinola.loljournal.data.MatchupRepository;
import com.danielakinola.loljournal.data.models.Champion;
import com.danielakinola.loljournal.utils.SingleLiveEvent;
import com.danielakinola.loljournal.utils.SnackbarMessage;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dakin on 13/01/2018.
 */

@SuppressWarnings("ALL")
public class ChampPoolViewModel extends ViewModel {
    private final MatchupRepository matchupRepository;
    private final String[] laneTitles;
    private final TypedArray laneIcons;

    private final MutableLiveData<Integer> currentLane = new MutableLiveData<>();
    private LiveData<String> laneTitle;
    private LiveData<Integer> laneIcon;

    private LiveData<List<Champion>>[] champions = new LiveData[5];

    private SnackbarMessage snackbarMessage = new SnackbarMessage();
    private SingleLiveEvent<Void> editChampPoolEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<String> championDetailEvent = new SingleLiveEvent<>();


    @Inject
    public ChampPoolViewModel(MatchupRepository matchupRepository,
                              @Named("laneTitles") String[] laneTitles,
                              @Named("laneIcons") TypedArray laneIcons) {
        this.matchupRepository = matchupRepository;
        this.laneTitles = laneTitles;
        this.laneIcons = laneIcons;
        getChampPoolData();
        subscribeToLaneChanges();
    }

    private void getChampPoolData() {
        for (int i = 0; i < champions.length; i++) {
            this.champions[i] = this.matchupRepository.getChampPool(i);
        }
    }

    private void subscribeToLaneChanges() {

        this.laneTitle = Transformations.map(currentLane, newLane -> {
            return laneTitles[newLane];
        });

        this.laneIcon = Transformations.map(currentLane, newLane -> {
            return laneIcons.getResourceId(newLane, 0);
        });

    }

    public LiveData<List<Champion>> getChampions(int lane) {
        return champions[lane];
    }

    public LiveData<Integer> getCurrentLane() {
        return currentLane;
    }

    public void setCurrentLane(int currentLane) {
        this.currentLane.setValue(currentLane);
    }

    public LiveData<String> getLaneTitle() {
        return laneTitle;
    }

    public LiveData<Integer> getLaneIcon() {
        return laneIcon;
    }

    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public SingleLiveEvent<Void> getEditChampPoolEvent() {
        return editChampPoolEvent;
    }

    public SingleLiveEvent<String> getChampionDetailEvent() {
        return championDetailEvent;
    }

    public void editChampPool() {
        editChampPoolEvent.call();
    }

    public void navigateToChampDetail() {
        championDetailEvent.call();
    }

    public void showSnackbarMessage() {
        snackbarMessage.setValue(R.string.champ_pool_edited);
    }

    public void updateFavourited(Champion champion) {
        Completable.fromAction(() -> matchupRepository.setChampionStarred(champion.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        if (!champion.isStarred()) showSnackbarMessage();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }
}
