package not.dresser;


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

public class ShelfListRecyclerAdapter extends RecyclerView.Adapter<ShelfListRecyclerAdapter.CustomViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ClothingItem> mItems = new ArrayList<>();
    private ShelfListRecyclerAdapter.ItemClickListener mClickListener;

    public ShelfListRecyclerAdapter(Context context, List<ClothingItem> items,
                                   ShelfListRecyclerAdapter.ItemClickListener clickListener) {
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
    public ShelfListRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
        }
        return ShelfListRecyclerAdapter.CustomViewHolder.create(mInflater, parent);
    }

    @Override
    public void onBindViewHolder(ShelfListRecyclerAdapter.CustomViewHolder holder, int position) {
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

        static ShelfListRecyclerAdapter.CustomViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new ShelfListRecyclerAdapter.CustomViewHolder(inflater.inflate(R.layout.shelf_item, parent, false));
        }

        CustomViewHolder(View v) {
            super(v);
            this.textView = (TextView) v.findViewById(R.id.infoText);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    public interface ItemClickListener {
        void onItemClick(ClothingItem item);
    }
}
