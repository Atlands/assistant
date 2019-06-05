package com.atlands.assistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atlands.assistant.db.Contentlist;
import com.atlands.assistant.db.Onelevel;
import com.atlands.assistant.db.Twolevel;
import com.atlands.assistant.treeview.Dir;
import com.atlands.assistant.treeview.DirectoryNodeBinder;
import com.atlands.assistant.treeview.File;
import com.atlands.assistant.treeview.FileNodeBinder;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

public class FragmentHome1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private TreeViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home1, container, false);
        recyclerView=view.findViewById(R.id.rv);
        //LitePal.getDatabase();
        //初始化数据
        initData();

        return view;
    }

    private void initData() {
        List<TreeNode> nodes = new ArrayList<>();
        List<Onelevel> onelevels = LitePal.findAll(Onelevel.class);
        Log.d("oneh",onelevels.toString());
        for (Onelevel onelevel : onelevels) {
            TreeNode<Dir> one = new TreeNode<>(new Dir(onelevel.getName()));
            nodes.add(one);
            List<Twolevel> twolevels = LitePal.where("oid = ?", onelevel.getId() + "").find(Twolevel.class);
            Log.d("hhh",twolevels.size()+"");
            if (twolevels.size() > 0) {
                for (Twolevel twolevel : twolevels) {
                    TreeNode<Dir> two = one.addChild(new TreeNode<>(new Dir(twolevel.getName())));
                    List<Contentlist>contentlists=LitePal.where("wid = ?",twolevel.getId()+"").find(Contentlist.class);
                    for (Contentlist contentlist:contentlists){
                        two.addChild(new TreeNode<>(new File(contentlist.getName())));
                    }
                }
            }else {
                List<Contentlist>contentlists=LitePal.where("oid = ?",onelevel.getId()+"").find(Contentlist.class);
                for (Contentlist contentlist:contentlists){
                    one.addChild(new TreeNode<>(new File(contentlist.getName())));
                }
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        adapter=new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(),new DirectoryNodeBinder()));
        //是否折叠树视图
        adapter.ifCollapseChildWhileCollapseParent(true);
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //更新并切换节点
                    onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree).start();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public FragmentHome1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome1.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome1 newInstance(String param1, String param2) {
        FragmentHome1 fragment = new FragmentHome1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
