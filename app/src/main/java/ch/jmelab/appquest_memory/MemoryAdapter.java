package ch.jmelab.appquest_memory;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder> {
    private List<MemoryCard> mMemoryDataset;
    private Context mContext;
    private MemoryActivity mMemoryActivity;

    public class MemoryViewHolder extends RecyclerView.ViewHolder {
        public CardView memoryCardView;
        public ImageView memoryPhoto;
        public TextView memoryCode;

        public MemoryViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMemoryActivity.takeQrCodePicture();
                    mMemoryActivity.clickedField = getAdapterPosition();
                }
            });
            this.memoryCardView = (CardView)itemView.findViewById(R.id.memory_card_view);
            this.memoryPhoto = (ImageView) itemView.findViewById(R.id.memory_photo);
            this.memoryCode = (TextView) itemView.findViewById(R.id.memory_code);
        }
    }

    public MemoryAdapter(List<MemoryCard> memoryDataset, Context context, MemoryActivity memoryActivity) {
        this.mMemoryDataset = memoryDataset;
        this.mContext = context;
        this.mMemoryActivity = memoryActivity;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MemoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memory_card, viewGroup, false);
        MemoryViewHolder memoryViewHolder = new MemoryViewHolder(view);
        return memoryViewHolder;
    }

    @Override
    public void onBindViewHolder(MemoryViewHolder memoryViewHolder, int position) {
        if (!mMemoryDataset.get(position).getCode().equals("") && !mMemoryDataset.get(position).getPath().equals("")) {
            memoryViewHolder.memoryPhoto.setImageBitmap(BitmapFactory.decodeFile(mMemoryDataset.get(position).getPath()));
            memoryViewHolder.memoryCode.setText(mMemoryDataset.get(position).getCode());
        } else {
            memoryViewHolder.memoryPhoto.setImageDrawable(mContext.getResources().getDrawable(R.drawable.photo_icon));
            memoryViewHolder.memoryCode.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mMemoryDataset.size();
    }

}
