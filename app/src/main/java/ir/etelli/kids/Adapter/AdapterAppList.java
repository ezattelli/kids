package ir.etelli.kids.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ir.etelli.kids.R;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;

import static android.content.Context.MODE_PRIVATE;

import com.google.android.material.button.MaterialButton;

public class AdapterAppList extends RecyclerView.Adapter<AdapterAppList.viewHolder> {


    public OnClick onClick;
    Context context;
    ArrayList<String> appName;
    ArrayList<String> packageName;
    ArrayList<Boolean> cbActive;
    ArrayList<String> authTime;
    ArrayList<String> startTime;
    ArrayList<Integer> usedTime;
    ArrayList<Boolean> expand;
    FancyShowCaseView item1, item2, item3, item4;

    public AdapterAppList(Context context,
                          ArrayList<String> appName,
                          ArrayList<String> packageName,
                          ArrayList<Boolean> cbActive,
                          ArrayList<String> authTime,
                          ArrayList<String> startTime,
                          ArrayList<Integer> usedTime,
                          ArrayList<Boolean> expand) {
        this.context = context;
        this.appName = appName;
        this.packageName = packageName;
        this.cbActive = cbActive;
        this.authTime = authTime;
        this.startTime = startTime;
        this.usedTime = usedTime;
        this.expand = expand;

    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_app_list, parent, false);

        return new viewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


//        Log.i("LockingForDelay","LockingForDelay - 1: " + position);

        holder.tvAppName.setText(appName.get(position));
        try {
            holder.appIcon.setImageDrawable(context.
                    getPackageManager().getApplicationIcon(packageName.get(position)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        if (cbActive.get(position)) {
            holder.ivActive.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            holder.ivActive.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


        if (!expand.get(position)) {
            holder.clTimeSetting.setVisibility(View.GONE);
        } else {
            holder.clTimeSetting.setVisibility(View.VISIBLE);
        }


        holder.tvAuthTime.setHint(authTime.get(position));

        holder.tvStartTime.setHint(startTime.get(position));

        int tempMinute = usedTime.get(position);
        int tempHour = 0;
        do {
            if (tempMinute > 59) {
                tempHour++;
                tempMinute -= 60;
            }

        } while (tempMinute > 59);

        holder.UsedTime.setText(tempHour + " : " + tempMinute);
        holder.timeBuget.setText(String.valueOf(usedTime.get(position)));

    }

    @Override
    public int getItemCount() {
        return appName.size();
    }

    public void setOnClickListener(OnClick onClick) {
        this.onClick = onClick;
    }


    public interface OnClick {
        void setOnClickInterface(View view, int position);
    }


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAppName;
        ImageView appIcon;
        ImageView ivActive;
        MaterialButton tvAuthTime;
        MaterialButton tvStartTime;
        ConstraintLayout clTimeSetting;
        ConstraintLayout clMainRow;
        TextView UsedTime;
        TextView timeBuget;
        CardView cardView;
        Button btnReset;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivActive = itemView.findViewById(R.id.ivActivate);
            tvAuthTime = itemView.findViewById(R.id.tvAuthAppTime);
            tvStartTime = itemView.findViewById(R.id.tvStartedAppTime);
            cardView = itemView.findViewById(R.id.cvRowAdapter);
            clMainRow = itemView.findViewById(R.id.clMainRow);
            btnReset = itemView.findViewById(R.id.btnResetAppLockTime);
            tvAppName = itemView.findViewById(R.id.tvAppName);
            appIcon = itemView.findViewById(R.id.imageIconApp);
            clTimeSetting = itemView.findViewById(R.id.clTimeSetting);
            UsedTime = itemView.findViewById(R.id.tvUsedTime);
            timeBuget = itemView.findViewById(R.id.tvTimeBuget);

            ivActive.setOnClickListener(this);
            tvAuthTime.setOnClickListener(this);
            tvStartTime.setOnClickListener(this);
            cardView.setOnClickListener(this);
//            appIcon.setOnClickListener(this);
            clMainRow.setOnClickListener(this);
            btnReset.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            onClick.setOnClickInterface(view, getLayoutPosition());
            cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.animation));

        }//switch end


    }

    private void showHelp(viewHolder holder) {

        SharedPreferences sharedPreferences =
                context.getSharedPreferences("startService", MODE_PRIVATE);

        boolean showHelpSetting = sharedPreferences.getBoolean("showHelpRow", false);

        if (!showHelpSetting) {

            item1 = new FancyShowCaseView.Builder((Activity) context)
                    .focusOn(holder.ivActive)
                    .focusCircleRadiusFactor(0.1)
//                    .focusCircleAtPosition(60,60,100)
                    .backgroundColor(context.getResources().getColor(R.color.help_search_icon))
                    .title("قفل کردن برنامه")
                    .build();
            item2 = new FancyShowCaseView.Builder((Activity) context)
                    .focusOn(holder.UsedTime)
                    .focusCircleRadiusFactor(0.1)
                    .title("مدت زمان استفاده شده")
                    .backgroundColor(context.getResources().getColor(R.color.help_sort_icon))
                    .build();
            item3 = new FancyShowCaseView.Builder((Activity) context)
                    .focusOn(holder.tvAuthTime)
                    .focusCircleRadiusFactor(0.1)
                    .title("مدت زمان مجاز")
                    .build();
            item4 = new FancyShowCaseView.Builder((Activity) context)
                    .focusOn(holder.tvStartTime)
                    .focusCircleRadiusFactor(0.1)
                    .title("زمان شروع مجاز شدن برنامه")
                    .build();

            new FancyShowCaseQueue()
                    .add(item1)
                    .add(item2)
                    .add(item3)
                    .add(item4)
                    .show();

            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("showHelpRow", true);
            editor.commit();
        }

    }

}
