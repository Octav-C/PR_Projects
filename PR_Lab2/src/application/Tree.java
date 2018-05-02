package application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Tree<T>{
    private T data = null;
    private List<Tree> children = new ArrayList<>();
    private Tree parent = null;
    private int idNode;
    private BigDecimal total = BigDecimal.ZERO;

    
    public void setidNode(int idNode) {
    	this.idNode = idNode;
    }
    public int getidNode() {
    	return idNode;
    }
    public Tree(T data) {
        this.data = data;
    }

    public void addChild(Tree<T> child) {
        child.setParent(this);
        this.children.add(child);
    }
    

    public void addChild(T data) {
        Tree<T> newChild = new Tree<>(data);
        newChild.setParent(this);
        children.add(newChild);
    }
   

    public void addChildren(List<Tree> children) {
        for(Tree t : children) {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public List<Tree> getChildren() {
        return children;
    }
    
   

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Tree parent) {
        this.parent = parent;
    }

    public Tree getParent() {
        return parent;
    }
    public void print(int level) {
        for (int i = 1; i < level; i++) {
            System.out.print("\t");
        }
        if(data != "root")
        System.out.println(data + "\t" + this.total);
        for (Tree child : children) {
            child.print(level + 1);
        }
    }
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}