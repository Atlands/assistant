package com.atlands.assistant.treeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.atlands.assistant.R;
import com.atlands.assistant.treeview.Node;
import com.atlands.assistant.treeview.Tree;
import com.atlands.assistant.treeview.TreeListViewAdapter;

/**
 * Created by deqiang.wang on 2018/4/6.
 */

public class MyAdapter extends TreeListViewAdapter<String> {
    public MyAdapter(ListView mTree, Context context, Tree treeNode)
    {
        super(mTree, context, treeNode);
    }
    @Override
    public View getConvertView(Node<String> node , int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_for_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageViewIcon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            viewHolder.mTextViewId = convertView.findViewById(R.id.textViewID);
            viewHolder.mTextViewObj = convertView.findViewById(R.id.textViewObj);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (node.isLeaf())
        {
            viewHolder.imageViewIcon.setVisibility(View.INVISIBLE);
        } else
        {
            viewHolder.imageViewIcon.setVisibility(View.VISIBLE);
            viewHolder.imageViewIcon.setImageResource(node.isExpand()?R.drawable.tree_ex:R.drawable.tree_ec);
        }

        viewHolder.mTextViewId.setText(new Integer(node.get_id()).toString());
        String str = node.getObject();
        if(str!=null) {
            viewHolder.mTextViewObj.setText(str);
        }else{
            viewHolder.mTextViewObj.setText("null");
        }
        return convertView;
    }

    private final class ViewHolder
    {
        ImageView imageViewIcon;
        public TextView mTextViewId;
        public TextView mTextViewObj;
    }
}
