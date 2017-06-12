package not.dresser.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import not.dresser.R;
import not.dresser.helpers.CRUDRealm;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.SimpleViewHolder> {

    private final Context mContext;
    private static String[] mData;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private HorizontalRVAdapter horizontalAdapter;

        public SimpleViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.course_item_name_tv);
            RecyclerView horizontalList = (RecyclerView) itemView.findViewById(R.id.horizontal_list);
            horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new HorizontalRVAdapter();
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public CourseRVAdapter(Context context, String[] data) {
        mContext = context;
        mData = data;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData[position]);
        holder.horizontalAdapter.setData(new CRUDRealm().getClothingList(mData[position])); // List of Strings
        holder.horizontalAdapter.setRowIndex(position);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

}
