public class VertexWithWeight implements VertexWithWeightFunction{
    private final Integer vertex;
    private Double weight;

    public VertexWithWeight(int v, Double w){
        vertex = v;
        weight = w;
    }

    public Integer getVertex(){
        return vertex;
    }

    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double w){
        weight = w;
    }

    public String toString(){
        return "(" + vertex +"," + weight +")";
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null) return false;
        if(getClass() == o.getClass()){
            VertexWithWeight other = (VertexWithWeight)o;
            if(vertex.equals(other.vertex)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode(){
        return vertex.intValue();
    }
}
