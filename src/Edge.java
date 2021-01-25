public class Edge {
    String edgeLabel;
    Vertex source;

    public Edge(String edgeLabel, Vertex source) {
        this.edgeLabel = edgeLabel;
        this.source = source;
    }

    public String getEdgeLabel() {
        return edgeLabel;
    }

    public void setEdgeLabel(String edgeLabel) {
        this.edgeLabel = edgeLabel;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }
}

