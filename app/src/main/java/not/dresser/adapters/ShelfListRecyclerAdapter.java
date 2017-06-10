package not.dresser.adapters;


import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import not.dresser.R;
import not.dresser.entity.ClothingItem;
import not.dresser.helpers.CRUDRealm;

public class ShelfListRecyclerAdapter extends RecyclerView.Adapter<ShelfListRecyclerAdapter.CustomViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ClothingItem mItem;
    private List<ClothingItem> mItems = new ArrayList<>();
    private ShelfListRecyclerAdapter.ItemClickListener mClickListener;

    private static final int PENDING_REMOVAL_TIMEOUT = 3000;
    private List<ClothingItem> mItemsPendingRemoval;
    private Handler mHandler = new Handler();
    private HashMap<ClothingItem, Runnable> mPendingRunnables = new HashMap<>();

    public ShelfListRecyclerAdapter(Context context, List<ClothingItem> items,
                                    ShelfListRecyclerAdapter.ItemClickListener clickListener) {
        updateAdapter(items);
        mContext = context;
        mClickListener = clickListener;
        mItemsPendingRemoval = new ArrayList<>();
    }

    public void updateAdapter(List<ClothingItem> clothingItems) {
        mItems.clear();
        if (clothingItems != null) {
            mItems.addAll(clothingItems);
        }
        notifyDataSetChanged();
    }

    @Override
    public ShelfListRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
        }
        return ShelfListRecyclerAdapter.CustomViewHolder.create(mInflater, parent);
    }

    @Override
    public void onBindViewHolder(ShelfListRecyclerAdapter.CustomViewHolder holder, int position) {
        final ClothingItem item = mItems.get(position);
        mItem = item;
        if (mItemsPendingRemoval.contains(item)) {
            holder.regularLayout.setVisibility(View.GONE);
            holder.swipeLayout.setVisibility(View.VISIBLE);
        } else {
            /** {show regular layout} and {hide swipe layout} */
            holder.regularLayout.setVisibility(View.VISIBLE);
            holder.swipeLayout.setVisibility(View.GONE);
            holder.textView.setText(item.getName());
            Glide.with(mContext).load(item.getPhotoUrl()).into(holder.imageView);
        }
        holder.undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoOpt(item);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(item);
            }
        };

        holder.imageView.setOnClickListener(listener);
        holder.textView.setOnClickListener(listener);
    }

    private void undoOpt(ClothingItem item) {
        Runnable pendingRemovalRunnable = mPendingRunnables.get(item);
        mPendingRunnables.remove(item);
        if (pendingRemovalRunnable != null)
            mHandler.removeCallbacks(pendingRemovalRunnable);
        mItemsPendingRemoval.remove(item);
        // this will rebind the row in "normal" state
        notifyItemChanged(mItems.indexOf(mItem));
    }

    public void pendingRemoval(int position) {
        final ClothingItem data = mItems.get(position);
        if (!mItemsPendingRemoval.contains(data)) {
            mItemsPendingRemoval.add(data);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the data
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(mItems.indexOf(data));
                }
            };
            mHandler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            mPendingRunnables.put(data, pendingRemovalRunnable);
        }

    }

    private void remove(int position) {
        ClothingItem data = mItems.get(position);
        if (mItemsPendingRemoval.contains(data)) {
            mItemsPendingRemoval.remove(data);
        }
        if (mItems.contains(data)) {
            mItems.remove(position);
            notifyItemRemoved(position);
            new CRUDRealm().removeClothingItem(data.getId(), mContext.getContentResolver());
        }
    }

    public boolean isPendingRemoval(int position, List<ClothingItem> clothingItems) {
        ClothingItem data = mItems.get(position);
        return mItemsPendingRemoval.contains(data);
    }

    @Override
    public int getItemCount() {
        return (null != mItems ? mItems.size() : 0);
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        private CardView regularLayout;
        private LinearLayout swipeLayout;
        private TextView undo;
        private TextView textView;
        private ImageView imageView;

        static ShelfListRecyclerAdapter.CustomViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new ShelfListRecyclerAdapter.CustomViewHolder(inflater.inflate(R.layout.row_item, parent, false));
        }

        CustomViewHolder(View v) {
            super(v);
            this.regularLayout = (CardView) v.findViewById(R.id.card);
            this.textView = (TextView) v.findViewById(R.id.infoText);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
            this.swipeLayout = (LinearLayout) v.findViewById(R.id.swipeLayout);
            this.undo = (TextView) v.findViewById(R.id.undo);
        }
    }

    public interface ItemClickListener {
        void onItemClick(ClothingItem item);
    }
}
