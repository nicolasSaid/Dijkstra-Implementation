public record EdgeWithWeight(Integer fromVertex, Integer toVertex, Double weight) {
	public String toString() {
		return "(" + fromVertex + "," + toVertex + "," + weight + ")";
	}
}