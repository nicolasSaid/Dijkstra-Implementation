import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.PriorityQueue;

public class WeightedGraph implements WeightedGraphFunctions{
    ArrayList<Integer> vertices;
	ArrayList<EdgeWithWeight> edges;
    private enum Solution {
        HAS_PATH, MIN_WEIGHT, GET_PATH
    };

    public WeightedGraph() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
    
    public boolean hasPath(int fromVertex, int toVertex){
        Object obj = dijkstra(fromVertex, toVertex, Solution.HAS_PATH);
        return (boolean)obj;
    }
	public double getMinimumWeight(int fromVertex, int toVertex){
        Object obj = dijkstra(fromVertex, toVertex, Solution.MIN_WEIGHT);
        if(obj != null) return (double)obj;
        return Double.NaN;
    }
	public EdgeWithWeight[] getPath(int fromVertex, int toVertex) {
        Object obj = dijkstra(fromVertex, toVertex, Solution.GET_PATH);
        if(obj != null) return (EdgeWithWeight[])obj;
        return new EdgeWithWeight[0];
    }
	public boolean addVertex(int v){
        if (!vertices.contains(v)) {
            vertices.add(v);
            return true;
		}
        return false;
    }
	public boolean addWeightedEdge(int from, int to, double weight){
        if (!edges.contains(new EdgeWithWeight(from, to, weight)) && vertices.contains(from) && (vertices.contains(to))) {
            edges.add(new EdgeWithWeight(from, to, weight));
        }
        return false;
    }
	public String toString(){
        String g = "G = (V, E)\n";
		g += "V = {" ;
		for (int i = 0; i < vertices.size(); i++) {
			g += vertices.get(i);
			if (vertices.size() - 1 > i) {
				g += ",";
			}
		}
		g += "}\n";
		g += "E = {";
		for (int i = 0; i < edges.size(); i++) {
			g += "(" + edges.get(i).fromVertex() + "," + edges.get(i).toVertex() + "," + edges.get(i).weight() + ")";
			if (edges.size() - 1 > i) {
				g += ",";
			}
		}
		g += "}";

		return g;
    }

    private Object dijkstra(int sourceVertex, int destVertex, Solution type){
        Map<Integer, VertexWithWeight> vtxTOvtxWithWgt = new TreeMap<>();
        Comparator<VertexWithWeight> comp = new VertexWithWeightComparator();
        PriorityQueue<VertexWithWeight> minPriorityQueueByWeight = new PriorityQueue<>(vertices.size(), comp);
        Map<VertexWithWeight, Integer> parent = new TreeMap<>(comp);
        VertexWithWeight vtxWithWgt = new VertexWithWeight(sourceVertex, 0.0);
        vtxTOvtxWithWgt.put(sourceVertex, vtxWithWgt);
        parent.put(vtxWithWgt, sourceVertex);
        minPriorityQueueByWeight.add(vtxWithWgt);
        for(Integer vtx : vertices) {
            if (!vtx.equals(sourceVertex)){
                vtxWithWgt = new VertexWithWeight(vtx, Double.POSITIVE_INFINITY);
                vtxTOvtxWithWgt.put(vtx, vtxWithWgt);
                parent.put(vtxWithWgt, -1);
                minPriorityQueueByWeight.add(vtxWithWgt);
            }
        }
        while (minPriorityQueueByWeight.size() > 0){
            VertexWithWeight vtxWithWgtFrom = minPriorityQueueByWeight.poll();
            if(parent.get(vtxWithWgtFrom) == -1 || parent.get(vtxWithWgtFrom) == destVertex){
                break;
            }
            for(EdgeWithWeight e : edges){
                if(e.fromVertex().equals(vtxWithWgtFrom.getVertex())){ //changed to from
                    Integer u = e.toVertex();
                    vtxWithWgt = vtxTOvtxWithWgt.get(u);
                    double temp = vtxWithWgtFrom.getWeight() + e.weight();
                    if(temp < vtxWithWgt.getWeight()){
                        minPriorityQueueByWeight.remove(vtxWithWgt);
                        parent.remove(vtxWithWgt);
                        vtxWithWgt.setWeight(temp);
                        minPriorityQueueByWeight.add(vtxWithWgt);
                        parent.put(vtxWithWgt, vtxWithWgtFrom.getVertex());
                    }
                }
            }
        }
        if(type == Solution.HAS_PATH){
            return parent.get(vtxTOvtxWithWgt.get(destVertex)) >= 0;
        }else if (type == Solution.MIN_WEIGHT){
            if(parent.get(vtxTOvtxWithWgt.get(destVertex)) >= 0)
                return vtxTOvtxWithWgt.get(destVertex).getWeight();
        }else if (type == Solution.GET_PATH){
            if(parent.get(vtxTOvtxWithWgt.get(destVertex)) >= 0) {
                ArrayList<Integer> reversePath = new ArrayList<>();
                VertexWithWeight p = vtxTOvtxWithWgt.get(destVertex);
                while (p.getVertex() != sourceVertex){
                    reversePath.add(p.getVertex());
                    p = vtxTOvtxWithWgt.get(parent.get(p));
                }
                reversePath.add(sourceVertex);
                Collections.reverse(reversePath);
                EdgeWithWeight[] array = new EdgeWithWeight[reversePath.size() - 1];
                for(int i = 0; i < reversePath.size() - 1; i++){
                    array[i] = getEdgeWithWeight(reversePath.get(i), reversePath.get(i+1));
                }
                return array;
            }
        }
        return null;
    }

    private EdgeWithWeight getEdgeWithWeight(int fromVertex, int toVertex){ 
        for(EdgeWithWeight e : edges) {
            if(e.fromVertex().equals(fromVertex) && e.toVertex().equals(toVertex)){
                return e;
            }
        }
        return null;
    }
}
