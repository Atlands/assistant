package com.atlands.assistant.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atlands.assistant.ContentPaper;
import com.atlands.assistant.DevelopWebContent;
import com.atlands.assistant.R;

import java.util.List;

public class DevelopReViewAdapter extends RecyclerView.Adapter<DevelopReViewAdapter.ViewHolder> {
    private List<Develop> mList;

    @NonNull
    @Override
    public DevelopReViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_develop, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DevelopReViewAdapter.ViewHolder viewHolder, int i) {
        final Develop develop = mList.get(i);
        viewHolder.icon.setImageResource(develop.getIcon());
        viewHolder.title.setText(develop.getTitle());
        viewHolder.recommend.setText(develop.getRecommend());
        viewHolder.itmeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DevelopWebContent.class);
                intent.putExtra("url", develop.getUrl());
                v.getContext().startActivity(intent);
            }
        });
    }

    public DevelopReViewAdapter(List<Develop> develops) {
        mList = develops;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, recommend;
        View itmeView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itmeView = itemView;
            icon = itemView.findViewById(R.id.develop_icon);
            title = itemView.findViewById(R.id.develop_title);
            recommend = itemView.findViewById(R.id.develop_recommend);
        }
    }
}
