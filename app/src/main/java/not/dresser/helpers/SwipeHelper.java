package not.dresser.helpers;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import not.dresser.R;
import not.dresser.adapters.ShelfListRecyclerAdapter;
import not.dresser.entity.ClothingItem;
import not.dresser.supporting.SwipeUtil;

public class SwipeHelper {

    private List<ClothingItem> mClothingItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context mContext;

    public SwipeHelper(RecyclerView recyclerView, Context context) {
        mRecyclerView = recyclerView;
        mContext = context;
    }

    private SwipeUtil setSwipeForCategory() {
        return new SwipeUtil(0, ItemTouchHelper.LEFT, mContext) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                ShelfListRecyclerAdapter adapter = (ShelfListRecyclerAdapter) mRecyclerView.getAdapter();
                adapter.pendingRemoval(swipedPosition);
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                ShelfListRecyclerAdapter adapter = (ShelfListRecyclerAdapter) mRecyclerView.getAdapter();
                if (adapter.isPendingRemoval(position, mClothingItems)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };
    }

    public void attachSwipeCategory() {
        SwipeUtil swipeHelper = new SwipeHelper(mRecyclerView, mContext).setSwipeForCategory();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        swipeHelper.setmLeftSwipeLable(mContext.getResources().getString(R.string.extraction));
        swipeHelper.setmLeftcolorCode(ContextCompat.getColor(mContext, R.color.starFullySelected));
    }
}
