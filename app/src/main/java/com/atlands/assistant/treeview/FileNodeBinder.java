package com.atlands.assistant.treeview;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.atlands.assistant.ContentPaper;
import com.atlands.assistant.R;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(final ViewHolder holder, final int position, TreeNode node) {
        final File fileNode = (File) node.getContent();
        holder.tvName.setText(fileNode.fileName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), ContentPaper.class);
                intent.putExtra("name",fileNode.fileName);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }
    }
}
