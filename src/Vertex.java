import java.util.ArrayList;
import java.util.Objects;

class Vertex {
    String stateLabel;
    boolean start;
    boolean accept;
    ArrayList<Edge> comingEdges;

    public Vertex(String stateLabel, boolean start, boolean accept) {
        this.stateLabel = stateLabel;
        this.start = start;
        this.accept = accept;
        this.comingEdges = new ArrayList<>();
    }
    public Vertex(String stateLabel) {
        this.stateLabel = stateLabel;
        this.start = false;
        this.accept = false;
        this.comingEdges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return start == vertex.start && accept == vertex.accept && Objects.equals(stateLabel, vertex.stateLabel) && Objects.equals(comingEdges, vertex.comingEdges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateLabel, start, accept, comingEdges);
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public void setStateLabel(String stateLabel) {
        this.stateLabel = stateLabel;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public ArrayList<Edge> getComingEdges() {
        return comingEdges;
    }

    public void setComingEdge(Edge comingEdge) {
        comingEdges.add(comingEdge);
    }
}