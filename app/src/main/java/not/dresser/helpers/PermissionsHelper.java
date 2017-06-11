package not.dresser.helpers;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import not.dresser.R;

public class PermissionsHelper {

    public static final int WRITE_EXTERNAL_STORAGE_REQUEST = 15;
    public static final int CAMERA_PERMISSION_REQUEST = 14;

    private Context mContext;
    private int mPermissionRequest;
    private String mPermission;

    public PermissionsHelper() {
    }

    public PermissionsHelper(Context context) {
        mContext = context;
    }

    public void showPermissionDialog(int request) {
        mPermissionRequest = request == WRITE_EXTERNAL_STORAGE_REQUEST ? WRITE_EXTERNAL_STORAGE_REQUEST
                : CAMERA_PERMISSION_REQUEST;
        mPermission = mPermissionRequest == WRITE_EXTERNAL_STORAGE_REQUEST ? Manifest.permission.WRITE_EXTERNAL_STORAGE
                : Manifest.permission.CAMERA;
        new AlertDialog.Builder(mContext)
                .setMessage(mContext.getResources().getString(R.string.give_permission))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((Activity) mContext,
                                new String[]{mPermission}, mPermissionRequest);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openPermissionSettings();
                    }
                })
                .create()
                .show();
    }

    private void openPermissionSettings() {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + mContext.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        ActivityCompat.startActivity(mContext, intent, null);
    }
}
