package not.dresser.helpers;


import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class RemoveClothingItemAsyncTask extends AsyncTask<Integer, Void, Void> {

    private WeakReference<Context> mWeakContext;

    public RemoveClothingItemAsyncTask(Context context){
        mWeakContext = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        int id = integers[0];
        Context context = mWeakContext.get();
        if (context != null) {
            ContentResolver resolver = context.getContentResolver();
            new CRUDRealm().removeClothingItem(id, resolver);
        }
        return null;
    }
}
