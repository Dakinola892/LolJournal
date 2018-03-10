package com.danielakinola.loljournal.matchupdetail;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielakinola.loljournal.R;
import com.danielakinola.loljournal.data.models.Comment;
import com.danielakinola.loljournal.databinding.FragmentMatchupDetailBinding;
import com.danielakinola.loljournal.databinding.ItemCommentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchupDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchupDetailFragment extends android.support.v4.app.Fragment {
    private static final String TITLE = "TITLE";
    private static final String CATEGORY = "CATERGORY";

    // TODO: Rename and change types of parameters
    private String title;
    private int category;
    private MatchupDetailViewModel matchupDetailViewModel;
    private CommentAdapter commentAdapter = new CommentAdapter();


    public MatchupDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Title of Fragment.
     * @return A new instance of fragment MatchupDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchupDetailFragment newInstance(int category, String title) {
        MatchupDetailFragment fragment = new MatchupDetailFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY, category);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            category = getArguments().getInt(CATEGORY);
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.comment_recyler_view);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_matchup_detail, container, false);
        FragmentMatchupDetailBinding fragmentMatchupDetailBinding = FragmentMatchupDetailBinding.bind(rootView);
        fragmentMatchupDetailBinding.setViewmodel(matchupDetailViewModel);
        fragmentMatchupDetailBinding.setCategory(category);

        setupRecyclerView();
        return rootView;
    }


    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

        ArrayList<Comment> comments = new ArrayList<>();

        CommentAdapter(List<Comment> comments) {
            this.comments = (ArrayList<Comment>) comments;
        }

        CommentAdapter() {

        }


        public void setComments(List<Comment> comments) {
            this.comments = (ArrayList<Comment>) comments;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            ItemCommentBinding itemCommentBinding = ItemCommentBinding.inflate(inflater, parent, false);
            itemCommentBinding.setListener(new CommentActionListener() {
                @Override
                public void onCommentFavourited(Comment comment) {
                    matchupDetailViewModel.changeCommentFavourited(comment);
                }

                @Override
                public void onCommentPlusOned() {
                    matchupDetailViewModel.onCommentPlusOned();
                }

                @Override
                public void onCommentSelected(Comment comment) {
                    matchupDetailViewModel.onCommentSelected(comment);
                }
            });

            return new CommentViewHolder(itemCommentBinding);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            holder.bind(comments.get(position));
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        class CommentViewHolder extends RecyclerView.ViewHolder {

            private final ItemCommentBinding itemCommentBinding;

            public CommentViewHolder(ItemCommentBinding itemCommentBinding) {
                super(itemCommentBinding.getRoot());
                this.itemCommentBinding = null;
            }

            void bind(Comment comment) {
                itemCommentBinding.setComment(comment);
                itemCommentBinding.executePendingBindings();
            }
        }

    }
}