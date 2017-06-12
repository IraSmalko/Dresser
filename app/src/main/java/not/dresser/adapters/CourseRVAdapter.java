package not.dresser.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.concurrent.ExecutionException;

import not.dresser.R;
import not.dresser.entity.ClothingItem;
import not.dresser.helpers.CRUDRealm;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.SimpleViewHolder> {

    private final Context mContext;
    private  String[] mData;
    private static int position;

    public String[] getmData() {
        return mData;
    }
    private RecyclerViewPager horizontalList;
    public  int getPosition() {
        return horizontalList.getCurrentPosition();
    }


    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private HorizontalRVAdapter horizontalAdapter;


        public SimpleViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.course_item_name_tv);
            horizontalList = (RecyclerViewPager) itemView.findViewById(R.id.horizontal_list);
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
