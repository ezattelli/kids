package ir.etelli.kids.Utils;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import ir.etelli.kids.R;

public class DeviceAdminSample extends DeviceAdminReceiver {


    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context, context.getString(R.string.admin_receiver_status_enabled), Toast.LENGTH_LONG).show();
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {

//        Intent i = new Intent(context, MainActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);


//        try{
//            dpm.resetPassword("123abc", DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
//        }catch (Exception e){
//            Toast.makeText(context, "تعویض رمز با خطا مواجه شد", Toast.LENGTH_SHORT).show();
//            Log.i("StartService", "تعویض رمز با خطا مواجه شد");
//        }


//        SharedPreferences sharedPreferences =
//                context.getSharedPreferences("startService", MODE_PRIVATE);
//
//        if (sharedPreferences.getBoolean("isAdmin", true)) {
//            DevicePolicyManager dpm = (DevicePolicyManager)
//                    context.getApplicationContext().
//                            getSystemService(Context.DEVICE_POLICY_SERVICE);
//            dpm.lockNow();
//
//        }


//        @SuppressLint("ResourceType")
//        Dialog dialog = new Dialog(context,R.layout.dialog_wip_data);
//        dialog.show();
//        dpm.wipeData(0);


//        return context.getString(R.string.admin_receiver_status_disable_warning);
        return "Are you sure??"; //This gets displayed in an alert dialog, but will only be visible after the user unlocks the screen
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
//        Log.i("TAGTAG", "onDisabled requested");
        Toast.makeText(context, context.getString(R.string.admin_receiver_status_disabled), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        Toast.makeText(context, context.getString(R.string.admin_receiver_status_pw_changed), Toast.LENGTH_LONG).show();
    }

}
