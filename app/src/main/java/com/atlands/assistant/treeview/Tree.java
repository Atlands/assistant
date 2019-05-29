package com.atlands.assistant.treeview;


import android.nfc.Tag;
import android.util.Log;

import java.util.List;

import static android.support.constraint.Constraints.TAG;


//本质是森林
public class Tree <T>{
    private Node root;

    public Tree(){
        root = new Node(-1,null);
    }

    public boolean addRoot(int id,T t){
        if(id<0){
            Log.w(TAG, "addRoot: node.id cannot be less than 0" );
            return false;
        }
        //必须在所有的节点中查找
        if(findNode(id)==null){
            addNodeToParent(new Node(id,t),root);
        }else {
            Log.w(TAG, String.format("addRoot: node.id=%d exists!",id));
            return false;
        }

        return true;
    }

    public boolean addLeaf(int id, int pid, Object object){
        if(id<0 || pid<0){
            Log.w(TAG, "addNode: id or pid should not be less than 0");
            return false;
        }
        Node node = findNode(id);
        if(node!=null){
            Log.w(TAG, String.format("addNode: node.id=%d exists",id));
            return false;
        }
        Node parent = findNode(pid);
        if(parent==null){
            Log.w(TAG, String.format("addNode: cannot find parent with id=",pid));
            return false;
        }
        node = new Node(id,object);

        addNodeToParent(node,parent);
        return true;
    }

    //将一个节点挂在父节点下面
    private void addNodeToParent(Node node, Node parent){
        node.set_parent(parent);
        parent.get_children().add(node);

        while(parent!=null){
            parent.set_size(parent.get_size(false)+node.get_size(false),false);
            parent.set_size(parent.get_size(true)+node.get_size(true),true);
            parent = parent.get_parent();
        }
    }
    //删除一个节点
    public void deleteNode(int id){
        if(id<0){
            Log.w(TAG, "deleteNode: id should not be less than 0");
            return;
        }
        Node node = findNode(id);
        if(node==null){
            Log.w(TAG, "deleteNode: cannot find the node.id="+id );
            return;
        }
        Node parent = node.get_parent();
        parent.get_children().remove(node);
        while(parent!=null){
            parent.set_size(parent.get_size(false)-node.get_size(false),false);
            parent.set_size(parent.get_size(true)-node.get_size(true),true);
            parent=parent.get_parent();
        }
    }
    public Node findNode(int id){
        return root.find_Node_By_Id(id,false);
    }
    //按照深度优先，遍历整个树，返回第position个元素的node
    //position从0开始
    public Node getInAll(int position){
        return get(position,false);
    }
    //仅遍历可见部分
    public Node getInCollapse(int position){
        return get(position,true);
    }
    private Node get(int position, boolean isExpand){
        return root.get(position+1,isExpand);
    }
    public int sizeOfAll(){
        return size(false);
    }
    public int sizeOfCollapse(){
        return size(true);
    }
    //获取树的大小
    //isExpand:
    // true 表示展开可见的大小
    // false 表示整棵树的大小
    private int size(boolean isExpand){
        return root.get_size(isExpand) - 1;
    }
    //点击位置position，折叠或者展开节点
    //return
    // false: 表示没有做任何事情
    public boolean expandOrCollapse(int position){
        Node node = getInCollapse(position);
        if(node==null) {
            Log.w(TAG, "expandOrCollapse: cannot find node at position="+position );
            return false;
        }
        if(node.isLeaf())
            return false;
        if(node.getExpand()){
            //折叠本节点
            int sizedelta = node.get_size(true) - 1;
            node.set_size(1,true);
            Node parent = node.get_parent();
            while(parent!=null){
                if(parent.getExpand()==false){
                    Log.e(TAG, String.format("expandOrCollapse: node.id should be collapsed!",parent.get_id()) );
                    return false;
                }
                parent.set_size(parent.get_size(true) - sizedelta,true);
                parent = parent.get_parent();
            }
        }else{
            //展开本节点
            int sizedelta=0;
            List<Node> children=node.get_children();
            for(Node son:children){
                sizedelta +=son.get_size(true);
            }
            node.set_size(1+sizedelta,true);
            Node parent = node.get_parent();
            while(parent!=null){
                if(parent.getExpand()==false){
                    Log.e(TAG, String.format("expandOrCollapse: node.id should be collapsed!",parent.get_id()) );
                    return false;
                }
                parent.set_size(parent.get_size(true) + sizedelta,true);
                parent = parent.get_parent();
            }
        }
        node.setExpand(!node.getExpand());
        return true;
    }
}
