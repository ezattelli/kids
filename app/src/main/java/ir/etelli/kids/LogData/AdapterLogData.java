package ir.etelli.kids.LogData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.etelli.kids.R;

class AdapterLogData extends RecyclerView.Adapter<AdapterLogData.viewHolder> {

    Context context;
    ArrayList<String> date;
    OnClickListener onClickListener;

    public AdapterLogData(Context context, ArrayList<String> date) {
        this.context = context;
        this.date = date;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.row_log_data, parent, false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tvLog.setText(date.get(position));
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    interface OnClickListener {
        void clickListener(View view, int position);
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvLog;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvLog = itemView.findViewById(R.id.tvLogDataDate);
            tvLog.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.clickListener(view, getAdapterPosition());
        }
    }

}
