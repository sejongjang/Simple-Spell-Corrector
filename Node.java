package spell;

public class Node implements ITrie.INode {
    private int count;
    public Node[] nodes = new Node[26];

    public Node(){
        count = 0;
        nodes = new Node[26];
    }

    public void increaseCount(){
        count++;
    }

    @Override
    public int getValue() {
        return count;
    }
}
