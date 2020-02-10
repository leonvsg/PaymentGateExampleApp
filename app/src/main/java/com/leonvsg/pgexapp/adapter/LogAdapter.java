package com.leonvsg.pgexapp.adapter;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leonvsg.pgexapp.R;
import com.leonvsg.pgexapp.model.LogEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private List<LogEntry> logList = new ArrayList<>();

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_view, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        holder.bind(logList.get(position));
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public void setItem(LogEntry logEntry) {
        logList.add(logEntry);
        notifyDataSetChanged();
    }

    public List<LogEntry> getAll() {
        return logList;
    }

    class LogViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.log_date) TextView mLogDate;
        @BindView(R.id.log_header) TextView mLogHeader;
        @BindView(R.id.log_text) TextView mLogText;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(LogEntry logEntry) {
            mLogDate.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S Z").format(logEntry.getDate()));
            mLogHeader.setText(logEntry.getHeader());
            mLogText.setText(logEntry.getText());
        }
    }
}
