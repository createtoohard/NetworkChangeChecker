package demo.example.zj.networkchangechecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by jiezhao on 16/12/4.
 */
public class NetworkReceiver extends BroadcastReceiver {
    private NetworkInfo mNetworkInfo;
    private final String TAG = "NetworkDemo";
    private SharedPreferences mSharedPreferences;
    private final String SPNAME = "network";
    private SharedPreferences.Editor mEditor;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            mNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                Log.e(TAG, "receiver network action");
                Log.e(TAG, "receiver network name = " + mNetworkInfo.getTypeName());
                mSharedPreferences = context.getSharedPreferences(SPNAME, Context.MODE_PRIVATE);
                if (mSharedPreferences != null) {
                    mEditor = mSharedPreferences.edit();
                    int i = mSharedPreferences.getInt(mNetworkInfo.getType() + "", 0);
                    mEditor.putInt(mNetworkInfo.getType() + "", i+1);
                    int total = mSharedPreferences.getInt("total", 0);
                    mEditor.putInt("total", total+1);
                    mEditor.commit();
                }
            }
        }
    }
}
