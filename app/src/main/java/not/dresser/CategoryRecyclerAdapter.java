package not.dresser;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CustomViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Category> mItems = new ArrayList<>();
    private CategoryRecyclerAdapter.ItemClickListener mClickListener;

    public CategoryRecyclerAdapter(Context context, List<Category> items,
                                         CategoryRecyclerAdapter.ItemClickListener clickListener) {
        updateAdapter(items);
        mContext = context;
        mClickListener = clickListener;
    }

    public void updateAdapter(List<Category> categoryRecipes) {
        mItems.clear();
        if (categoryRecipes != null) {
            mItems.addAll(categoryRecipes);
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
        final Category item = mItems.get(position);
            holder.textView.setText(item.getName());
            holder.imageView.setImageDrawable(item.getPhoto());

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
            return new CustomViewHolder(inflater.inflate(R.layout.card_item, parent, false));
        }

        CustomViewHolder(View v) {
            super(v);
            this.textView = (TextView) v.findViewById(R.id.infoText);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Category item);
    }
}
