package com.rahul.musicapp.adapters;

import static com.rahul.musicapp.fragments.SongPlayingFragment.mediaPlayer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.stevenmwesigwa.musicapp.R;
import com.rahul.musicapp.Songs;
import com.rahul.musicapp.fragments.SongPlayingFragment;

import java.util.ArrayList;

public class FavoriteScreenAdapter extends RecyclerView.Adapter<FavoriteScreenAdapter.FavoriteScreenViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Songs> mSongsList;
    private Context mContext;

    // data is passed into the constructor
    public FavoriteScreenAdapter(ArrayList<Songs> songsList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mSongsList = songsList;
        this.mContext = context;
    }

    /*
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    @NonNull
    @Override
    public FavoriteScreenAdapter.FavoriteScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // We inflate the xml which gives us a View object
        View view = mInflater.inflate(R.layout.row_custom_mainscreen_adapter, parent, false);
        return new FavoriteScreenAdapter.FavoriteScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteScreenAdapter.FavoriteScreenViewHolder holder, int position) {
        Songs song = mSongsList.get(position);

        holder.trackTitle.setText(song.getSongTitle());
        holder.trackArtist.setText(song.getSongArtist());
        /*
         * Implement the onClickListener on the 'navdrawerItemContentHolder' to open the respective
         * Fragment on click.
         */
        holder.contentRowSongList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SongPlayingFragment songPlayingFragment = new SongPlayingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("songArtist", song.getSongArtist());
                bundle.putString("songTitle", song.getSongTitle());
                bundle.putLong("songId", song.getSongId());
                bundle.putString("songData", song.getSongData());
                bundle.putLong("songDateAdded", song.getSongDateAdded());
                bundle.putInt("songPosition", position);
                bundle.putParcelableArrayList("songsList", mSongsList);
                mediaPlayer.reset();
                //Link values with the songPlayingFragment
                songPlayingFragment.setArguments(bundle);
                /*
                 * Let's begin the transaction
                 * We will invoke this Adapter through our MainActivity.java file.
                 */
                FragmentActivity fragmentActivity = (FragmentActivity) mContext;
                fragmentActivity.getSupportFragmentManager()
                        .beginTransaction()
                        // Replace the the already added fragment from MainActivity.java
                        .replace(R.id.detailsFragment, songPlayingFragment)
                        .addToBackStack("FavoriteFragment")
                        .commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mSongsList == null) {
            return 0;
        } else {
            return mSongsList.size();
        }
    }
    // This is where we will initialize our views
    class FavoriteScreenViewHolder extends RecyclerView.ViewHolder {
        TextView trackTitle;
        TextView trackArtist;
        RelativeLayout contentRowSongList;

        FavoriteScreenViewHolder(View view) {
            super(view);
            trackTitle = view.findViewById(R.id.trackTitle);
            trackArtist = view.findViewById(R.id.trackArtist);
            contentRowSongList = view.findViewById(R.id.contentRowSongList);

        }
    }


}

