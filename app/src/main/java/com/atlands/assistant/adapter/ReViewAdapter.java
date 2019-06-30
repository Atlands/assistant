package com.atlands.assistant.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atlands.assistant.ContentPaper;
import com.atlands.assistant.R;
import com.atlands.assistant.db.Contentlist;

import java.util.List;

public class ReViewAdapter extends RecyclerView.Adapter<ReViewAdapter.ViewHolder> {
    private List<Contentlist> mList;

    public ReViewAdapter(List<Contentlist> contentlists) {
        mList = contentlists;
    }

    @NonNull
    @Override
    public ReViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_file, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReViewAdapter.ViewHolder viewHolder, int i) {
        final Contentlist contentlist=mList.get(i);
        viewHolder.textView.setText(contentlist.getName());
        viewHolder.iView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ContentPaper.class);
                intent.putExtra("name",contentlist.getName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View iView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iView=itemView;
            textView = itemView.findViewById(R.id.tv_name);

        }
    }
}
