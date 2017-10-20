
public class GraphDraft {
  private Point[] points;
  private int[] pointstate;
  private Edge[] edges;
  private int[][] edgestate;

  public Point[] getPoints() {
    return points;
  }

  public void setPoints(Point[] points) {
    this.points = points;
  }

  public int[] getPointstate() {
    return pointstate;
  }

  public void setPointstate(int[] pointstate) {
    this.pointstate = pointstate;
  }

  public Edge[] getEdges() {
    return edges;
  }

  public void setEdges(Edge[] edges) {
    this.edges = edges;
  }

  public int[][] getEdgestate() {
    return edgestate;
  }

  public void setEdgestate(int[][] edgestate) {
    this.edgestate = edgestate;
  }

  GraphDraft(Point[] points, Edge[] edges) {
    this.points = points;
    this.edges = edges;
    pointstate = new int[points.length];
    edgestate = new int[points.length][points.length];
  }
}