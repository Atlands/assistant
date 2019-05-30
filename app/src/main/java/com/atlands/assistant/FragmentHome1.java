package com.atlands.assistant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.atlands.assistant.db.Onelevel;
import com.atlands.assistant.db.Twolevel;
import com.atlands.assistant.treeview.MyAdapter;
import com.atlands.assistant.treeview.Node;
import com.atlands.assistant.treeview.Tree;
import com.atlands.assistant.treeview.TreeListViewAdapter;

import org.litepal.LitePal;

import java.util.List;

public class FragmentHome1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;

    Tree<String> mTree = new Tree();
    TreeListViewAdapter myAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home1, container, false);

        //初始化数据
        initData();
        listView=view.findViewById(R.id.list_content);
        myAdapter = new MyAdapter(listView,getActivity(),mTree);
        listView.setAdapter(myAdapter);
        return view;
    }

    private void initData() {
        mTree.addRoot(0,"");
        mTree.addLeaf(1,0,"a");
        mTree.addLeaf(2,0,"b");
        mTree.addLeaf(3,0,"c");
        mTree.addLeaf(5,0,"d");
        mTree.addLeaf(4,2,"e");
        mTree.addLeaf(7,4,"f");
        mTree.addLeaf(8,7,"g");
        mTree.addLeaf(9,8,"h");
        mTree.addLeaf(6,5,"i");

//        int i=0;
//        mTree.addRoot(i,"");
//        List<Onelevel> onelevelList= LitePal.findAll(Onelevel.class);
//        for (Onelevel onelevel:onelevelList){
//            List<Twolevel> twolevelList=LitePal.where("oid=?",onelevel.getId()+"").find(Twolevel.class);
//            if (twolevelList.size()>0){
//
//            }
//        }
    }
    public void onClickTreeNode(Node node, int position){
        //对节点的处理 可以在这里做，
        //比如，如果要删除节点，直接点用
        //mTree.deleteNode(node.get_id());
        //myAdapter.notifyDataSetChanged();
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
