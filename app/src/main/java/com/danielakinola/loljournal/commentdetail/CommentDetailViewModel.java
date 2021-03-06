package com.danielakinola.loljournal.commentdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.res.TypedArray;

import com.danielakinola.loljournal.R;
import com.danielakinola.loljournal.data.MatchupRepository;
import com.danielakinola.loljournal.data.models.Comment;
import com.danielakinola.loljournal.data.models.Matchup;
import com.danielakinola.loljournal.utils.SingleLiveEvent;
import com.danielakinola.loljournal.utils.SnackbarMessage;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentDetailViewModel extends ViewModel {
    private final MatchupRepository matchupRepository;
    private final SingleLiveEvent<Comment> editCommentEvent;
    private final SingleLiveEvent<Comment> deleteCommentEvent;
    private final SingleLiveEvent<Void> finishEvent;
    private final SnackbarMessage snackbarMessage;
    private LiveData<String> title;
    private LiveData<String> subtitle;
    private LiveData<Integer> logo;
    private LiveData<Matchup> matchup;
    private LiveData<Comment> comment;
    private int commentId;
    private final String[] commentCategories;
    private final TypedArray laneIcons;
    private final String versusString;



    @Inject
    public CommentDetailViewModel(MatchupRepository matchupRepository,
                                  SingleLiveEvent<Comment> editCommentEvent,
                                  SingleLiveEvent<Comment> deleteCommentEvent,
                                  SingleLiveEvent<Void> finishEvent, SnackbarMessage snackbarMessage,
                                  @Named("commentCategories") String[] commentCategories,
                                  @Named("actionBarIcons") TypedArray laneIcons,
                                  @Named("versus") String versusString) {
        this.matchupRepository = matchupRepository;
        this.editCommentEvent = editCommentEvent;
        this.deleteCommentEvent = deleteCommentEvent;
        this.finishEvent = finishEvent;
        this.snackbarMessage = snackbarMessage;
        this.commentCategories = commentCategories;
        this.laneIcons = laneIcons;
        this.versusString = versusString;
    }

    public void initialize(int commentId) {
        this.commentId = commentId;
        this.comment = matchupRepository.getComment(commentId);
        this.subtitle = Transformations.map(comment, comment -> {
            if (comment != null) {
                return commentCategories[comment.getCategory()];
            } else {
                return " ";
            }
        });
        this.matchup = Transformations.switchMap(this.comment, comment -> {
            if (comment != null) {
                return matchupRepository.getMatchup(comment.getMatchupId());
            } else {
                return new MutableLiveData<>();
            }
        });
        this.title = Transformations.map(matchup, matchup -> {
            if (matchup != null) {
                return String.format(versusString, matchup.getPlayerChampion(), matchup.getEnemyChampion());
            } else {
                return " ";
            }
        });
        this.logo = Transformations.map(matchup, matchup -> {
            if (matchup != null) {
                return laneIcons.getResourceId(matchup.getLane(), 0);
            } else {
                return R.drawable.empty;
            }

        });
    }

    public void editComment() {
        editCommentEvent.setValue(comment.getValue());
    }

    public void favouriteComment() {
        Completable.fromAction(() -> matchupRepository.setCommentStarred(commentId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        if (!comment.getValue().isStarred())
                            snackbarMessage.setValue(R.string.comment_favourited);
                    }

                    @Override
                    public void onError(Throwable e) {
                        snackbarMessage.setValue(R.string.error);
                    }
                });
    }

    public void deleteComment() {
        Completable.fromAction(() -> matchupRepository.deleteComment(comment.getValue()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        finishEvent.call();
                    }

                    @Override
                    public void onError(Throwable e) {
                        snackbarMessage.setValue(R.string.error);
                    }
                });
    }

    public SingleLiveEvent<Comment> getEditCommentEvent() {
        return editCommentEvent;
    }

    public SingleLiveEvent<Comment> getDeleteCommentEvent() {
        return deleteCommentEvent;
    }

    public SingleLiveEvent<Void> getFinishEvent() {
        return finishEvent;
    }

    public SnackbarMessage getSnackbarMessage() {
        return snackbarMessage;
    }

    public LiveData<Comment> getComment() {
        return comment;
    }

    public LiveData<Matchup> getMatchup() {
        return matchup;
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getSubtitle() {
        return subtitle;
    }

    public LiveData<Integer> getLogo() {
        return logo;
    }

    public void showDeleteDialog() {
        deleteCommentEvent.setValue(comment.getValue());
    }

    public void onEdit(int resultCode) {
        if (resultCode == 1) {
            snackbarMessage.setValue(R.string.comment_edited);
        } else {
            snackbarMessage.setValue(R.string.error);
        }
    }
}
