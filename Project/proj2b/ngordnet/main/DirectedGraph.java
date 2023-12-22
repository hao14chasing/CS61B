package ngordnet.main;
import java.util.ArrayList;
import java.util.List;

public class DirectedGraph<T> {
    private int verticesNum;
    private List<Integer>[] vertices;
    private int edgeNum;
    public DirectedGraph(int v) {
        verticesNum = v;
        vertices = new List[v];
        constructLists();
        edgeNum = 0;
    }
    public void addEdge(int v, int w) {
        if (vertices[v].contains(w)) {
            return;
        }
        vertices[v].add(w);
        edgeNum++;
    }

    public List<Integer> adj(int v) {
        return vertices[v];
    }

    public int V() {
        return verticesNum;
    }

    public int E() {
        return edgeNum;
    }

    public void constructLists() {
        for (int i = 0; i < verticesNum; i++) {
            vertices[i] = new ArrayList<>();
            vertices[i].add(i);
        }
    }
}
