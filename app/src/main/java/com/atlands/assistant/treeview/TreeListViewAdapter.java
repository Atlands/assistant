package com.atlands.assistant.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected Tree mTree;
    protected LayoutInflater mInflater;

    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public TreeListViewAdapter(ListView mTreeView, Context context, Tree tree) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTree = tree;

        mTreeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mTree != null) {
                    mTree.expandOrCollapse(position);
                    notifyDataSetChanged();
                    if (onTreeNodeClickListener != null) {
                        onTreeNodeClickListener.onClick(mTree.getInCollapse(position), position);
                    }
                }
            }
        });
    }

    @Override
    public int getCount() {
        return mTree == null ? 0 : mTree.sizeOfCollapse();
    }

    @Override
    public Object getItem(int position) {
        return mTree == null ? null : mTree.getInCollapse(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mTree == null)
            return null;

        Node node = mTree.getInCollapse(position);
        convertView = getConvertView(node, position, convertView, parent);
        convertView.setPadding(node.get_level() * 30, 3, 3, 3);
        return convertView;
    }

    public abstract View getConvertView(Node<T> node, int position, View convertView, ViewGroup parent);
}