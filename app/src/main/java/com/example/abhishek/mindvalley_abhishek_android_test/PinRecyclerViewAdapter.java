package com.example.abhishek.mindvalley_abhishek_android_test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishek.remoteresourceloader.OnDownloadCompletionListener;
import com.example.abhishek.remoteresourceloader.ResourceManager;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PinRecyclerViewAdapter extends RecyclerView.Adapter<PinRecyclerViewAdapter.PinViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Board> boards;
    private Context context;
    private ResourceManager resourceManager;
    byte[] bytes;

    public PinRecyclerViewAdapter(Context _context, List<Board> boards) {
        this.context = _context;
        this.boards = boards;

    }

    @Override
    public PinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _Item = LayoutInflater.from(parent.getContext()).inflate(R.layout.pin_item, parent, false);
        return new PinViewHolder(_Item);
    }

    private void updatePinImage(PinViewHolder holder, byte[] bytes) {
        if (bytes != null) {
            holder.buttonCancelLoad.setVisibility(View.GONE);
            Bitmap bitmap = ResourceManager.getInstance(context).getConvertersUtils().byteArrayToBitmap(bytes);
            holder.pinnedImage.setImageBitmap(bitmap);
        } else
            Toast.makeText(context, "Connection not established", Toast.LENGTH_SHORT).show();
    }

    private void updateProfilePic(PinViewHolder holder, byte[] bytes) {
        if (bytes != null) {
            Bitmap bitmap = ResourceManager.getInstance(context).getConvertersUtils().byteArrayToBitmap(bytes);
            holder.profileImage.setImageBitmap(bitmap);
        } else
            Toast.makeText(context, "Connection not established", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBindViewHolder(final PinViewHolder holder, int position) {
        Board board = this.boards.get(position);
        resourceManager = ResourceManager.getInstance(context);
        int totalPics = 0;
        if (board.getUser() != null) {
            holder.username.setText("@" + board.getUser().getUsername());
            holder.user_fullName.setText(board.getUser().getName());
        }
        holder.pinnedImage.setBackgroundColor(Color.parseColor(board.getColor()));

        ResourceManager.getInstance(context).getResourceLoader().fetchResource(board.getUrls().getSmall(), new OnDownloadCompletionListener() {
            @Override
            public void resourceFetchedFromRemote(String url, byte[] bytes) {
                updatePinImage(holder, bytes);
            }

            @Override
            public void resourceFetchedFromMemory(String url, byte[] bytes) {
                updatePinImage(holder, bytes);
            }

            @Override
            public void resourceDownloadCancelled(String url) {
                Toast.makeText(context, "Resource loading cancelled", Toast.LENGTH_LONG).show();
            }
        }, holder.buttonCancelLoad);

        resourceManager.getResourceLoader().fetchResource(board.getUser().getProfileImage().getSmall(), new OnDownloadCompletionListener() {
            @Override
            public void resourceFetchedFromRemote(String url, byte[] bytes) {
                updateProfilePic(holder, bytes);
            }

            @Override
            public void resourceFetchedFromMemory(String url, byte[] bytes) {
                updateProfilePic(holder, bytes);
            }

            @Override
            public void resourceDownloadCancelled(String url) {

            }
        }, null);

        holder.totalLikes.setText(String.valueOf(board.getLikes()));

        if (board.getCategories() != null) {
            for (Categories category : board.getCategories()) {
                totalPics += category.getPhotoCount();
            }
        }
        if (totalPics < 1000)
            holder.totalPins.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.ENGLISH).format(totalPics)));
        else {
            float newTotalPics = totalPics / 1000;
            holder.totalPins.setText(String.format(Locale.ENGLISH, "%.1f K", newTotalPics));
        }
        holder.itemView.setTag(board);
    }

    @Override
    public int getItemCount() {
        return this.boards.size();
    }

    @Override
    public void onViewRecycled(PinViewHolder holder) {
        super.onViewRecycled(holder);
        holder.pinnedImage.setImageBitmap(null);
        holder.buttonCancelLoad.setVisibility(View.VISIBLE);
        holder.profileImage.setImageBitmap(null);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(context, "Long press triggered", Toast.LENGTH_LONG).show();
        return true;
    }


    public class PinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView username;
        public TextView user_fullName;
        public ImageView pinnedImage;
        public TextView totalPins;
        public TextView totalLikes;
        public CircleImageView profileImage;
        public ImageButton buttonCancelLoad;

        public PinViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            username = (TextView) itemView.findViewById(R.id.textView_user_username);
            user_fullName = (TextView) itemView.findViewById(R.id.textView_user_name);
            pinnedImage = (ImageView) itemView.findViewById(R.id.imageView_pin_image);
            totalPins = (TextView) itemView.findViewById(R.id.pin_item_totalPics);
            totalLikes = (TextView) itemView.findViewById(R.id.textView_total_likes);
            profileImage = (CircleImageView) itemView.findViewById(R.id.circleImageView_user_profile_pic);
            buttonCancelLoad = (ImageButton) itemView.findViewById(R.id.imageButton_cancel);
        }

        @Override
        public void onClick(View view) {
            // Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            //   Intent intent = new Intent(context, DetialsActivity.class);
            // intent.putExtra("AlbumId", ((TextView)view.findViewById(R.id.lblAlbumId)).getText().toString());
            // view.getContext().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(v.getContext(), "Long clicked Position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

