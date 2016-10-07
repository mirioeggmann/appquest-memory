package ch.jmelab.appquest_memory;

import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MemoryAdapter extends RecyclerView.Adapter<MemoryAdapter.MemoryViewHolder> {
    private List<MemoryCard> mMemoryDataset;

    public class MemoryViewHolder extends RecyclerView.ViewHolder {
        public CardView memoryCardView;
        public ImageView memoryPhoto;
        public TextView memoryCode;

        public MemoryViewHolder(View itemView) {
            super(itemView);
            this.memoryCardView = (CardView)itemView.findViewById(R.id.memory_card_view);
            this.memoryPhoto = (ImageView) itemView.findViewById(R.id.memory_photo);
            this.memoryCode = (TextView) itemView.findViewById(R.id.memory_code);
        }
    }

    public MemoryAdapter(List<MemoryCard> memoryDataset) {
        this.mMemoryDataset = memoryDataset;
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
        memoryViewHolder.memoryPhoto.setImageBitmap(BitmapFactory.decodeFile(mMemoryDataset.get(position).getPath()));
        memoryViewHolder.memoryCode.setText(mMemoryDataset.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return mMemoryDataset.size();
    }

}
