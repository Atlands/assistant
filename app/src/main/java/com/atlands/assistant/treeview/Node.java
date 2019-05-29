package com.atlands.assistant.treeview;

import java.util.ArrayList;
import java.util.List;

public class Node <T>{
    private int _id;  //节点id
    private Node _parent;  //父节点
    private List<Node> _children=new ArrayList<>(); //所有的儿子节点
    private T obj; //依附这个节点的用户对象
    private int _size_all; //本节点对应的树的大小（计算其下所有的节点，无论展开状态是啥）
    private int _size_expand; //本节点对应的树展开状态大小
    private boolean isExpand=true; //本节点的展开状态

    public Node(int id, T obj){
        this._id = id;
        this.obj = obj;
        _size_expand=1;
        _size_all=1;
    }
    Node get_parent(){return _parent;}
    void set_parent(Node node){
        this._parent = node;
    }
    List<Node> get_children(){return _children;}
    int get_size(boolean isExpand){return isExpand?_size_expand:_size_all;}
    void set_size(int size,boolean isExpand){
        if(isExpand)
            _size_expand = size;
        else
            _size_all=size;
    }
    //在当前这个node为根的树上寻找等于id的node，如果找不到，返回null
    //isExpand:
    //true：表示在expand树上查找
    // false: 表示不考虑expand属性，在整颗树上找
    Node find_Node_By_Id(int id,boolean isExpand){
        if(this.get_id()==id)
            return this;

        List<Node> list=this.get_children();
        if(list.size()==0){
            return null;
        }else{
            if((isExpand && this.getExpand()) || !isExpand){
                for(Node node: this.get_children()){
                    Node result=node.find_Node_By_Id(id,isExpand);
                    if(result!=null)
                        return result;
                }
            }
        }
        return null;
    }
    //按照深度优先，遍历以本节点为根的整个树，返回第position个元素的node
    //position从0开始
    //isExpand:
    //true：表示在expand树上查找
    // false: 表示不考虑expand属性，在整颗树上找
    Node get(int position,boolean isExpand){
        if(position==0)
            return this;
        position--;
        List<Node> list=this.get_children();
        if(list.size()==0){
            return null;
        }else{
            if(!isExpand || (isExpand && this.getExpand())){
                for(Node node:this.get_children()){
                    int size = position - node.get_size(isExpand);
                    if(size<0){
                        return node.get(position,isExpand);
                    }else{
                        position=size;
                    }
                }//for
            }//if
        }//if
        return null;
    }
    void setExpand(boolean expand) {
        isExpand = expand;
    }
    boolean getExpand(){
        return isExpand;
    }
    public boolean isLeaf(){
        return this.get_children().size()==0;
    }
    public boolean isExpand(){
        return isExpand;
    }
    public int get_id(){return _id;}
    public int get_level(){
        if(_parent!=null)
            return _parent.get_level()+1;
        else
            return 0;
    }
    public T getObject(){
        return obj;
    }
}
