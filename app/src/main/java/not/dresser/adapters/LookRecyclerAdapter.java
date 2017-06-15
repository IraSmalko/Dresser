package not.dresser.adapters;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import not.dresser.R;
import not.dresser.entity.ClothingLook;
import not.dresser.helpers.RemoveClothingLookAsyncTask;

public class LookRecyclerAdapter extends RecyclerView.Adapter<LookRecyclerAdapter.CustomViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ClothingLook mItem;
    private List<ClothingLook> mItems = new ArrayList<>();
    private ItemClickListener mClickListener;

    private static final int PENDING_REMOVAL_TIMEOUT = 3000;
    private List<ClothingLook> mItemsPendingRemoval;
    private Handler mHandler = new Handler();
    private HashMap<ClothingLook, Runnable> mPendingRunnables = new HashMap<>();

    public LookRecyclerAdapter(Context context, List<ClothingLook> items, ItemClickListener clickListener) {
        updateAdapter(items);
        mContext = context;
        mItemsPendingRemoval = new ArrayList<>();
        mClickListener = clickListener;
    }

    public void updateAdapter(List<ClothingLook> clothingItems) {
        mItems.clear();
        if (clothingItems != null) {
            mItems.addAll(clothingItems);
        }
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
        }
        return CustomViewHolder.create(mInflater, parent);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final ClothingLook item = mItems.get(position);
        mItem = item;
        if (mItemsPendingRemoval.contains(item)) {
            holder.regularLayout.setVisibility(View.GONE);
            holder.swipeLayout.setVisibility(View.VISIBLE);
        } else {
            /** {show regular layout} and {hide swipe layout} */
            holder.regularLayout.setVisibility(View.VISIBLE);
            holder.swipeLayout.setVisibility(View.GONE);
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
        holder.regularLayout.setOnClickListener(listener);
    }

    private void undoOpt(ClothingLook item) {
        Runnable pendingRemovalRunnable = mPendingRunnables.get(item);
        mPendingRunnables.remove(item);
        if (pendingRemovalRunnable != null)
            mHandler.removeCallbacks(pendingRemovalRunnable);
        mItemsPendingRemoval.remove(item);
        // this will rebind the row in "normal" state
        notifyItemChanged(mItems.indexOf(mItem));
    }

    public void pendingRemoval(int position) {
        final ClothingLook data = mItems.get(position);
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
        ClothingLook data = mItems.get(position);
        if (mItemsPendingRemoval.contains(data)) {
            mItemsPendingRemoval.remove(data);
        }
        if (mItems.contains(data)) {
            mItems.remove(position);
            notifyItemRemoved(position);
            new RemoveClothingLookAsyncTask( mContext).execute(data.getId());
        }
    }

    public boolean isPendingRemoval(int position) {
        ClothingLook data = mItems.get(position);
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
        private ImageView imageView;

        static CustomViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new CustomViewHolder(inflater.inflate(R.layout.row_look_item, parent, false));
        }

        CustomViewHolder(View v) {
            super(v);
            this.regularLayout = (CardView) v.findViewById(R.id.card);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
            this.swipeLayout = (LinearLayout) v.findViewById(R.id.swipeLayout);
            this.undo = (TextView) v.findViewById(R.id.undo);
        }
    }
    public interface ItemClickListener {
        void onItemClick(ClothingLook item);
    }
}
