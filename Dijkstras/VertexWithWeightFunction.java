public interface VertexWithWeightFunction {
    Double getWeight();
    Integer getVertex();
    void setWeight(Double w);
    boolean equals(Object o);
    int hashCode();
    String toString();
}
