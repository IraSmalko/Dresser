package not.dresser.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import not.dresser.R;
import not.dresser.entity.Category;
import not.dresser.entity.ClothingItem;

public class ShelfRecyclerAdapter extends RecyclerView.Adapter<ShelfRecyclerAdapter.CustomViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ClothingItem> mItems = new ArrayList<>();
    private ItemClickListener mClickListener;

    public ShelfRecyclerAdapter(Context context, List<ClothingItem> items, ItemClickListener clickListener) {
        updateAdapter(items);
        mContext = context;
        mClickListener = clickListener;
    }

    public void updateAdapter(List<ClothingItem> clothingItems) {
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
        final ClothingItem item = mItems.get(position);
        holder.textView.setText(item.getName());
        Glide.with(mContext).load(item.getPhotoUrl()).into(holder.imageView);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(item);
            }
        };

        holder.imageView.setOnClickListener(listener);
        holder.textView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != mItems ? mItems.size() : 0);
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        static CustomViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new CustomViewHolder(inflater.inflate(R.layout.image_item, parent, false));
        }

        CustomViewHolder(View v) {
            super(v);
            this.textView = (TextView) v.findViewById(R.id.text);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    public interface ItemClickListener {
        void onItemClick(ClothingItem item);
    }
}
