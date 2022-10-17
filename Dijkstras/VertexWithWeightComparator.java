import java.util.Comparator;

public class VertexWithWeightComparator implements Comparator<VertexWithWeight>{

    @Override
    public int compare(VertexWithWeight o1, VertexWithWeight o2) {
        if(o1.getWeight().doubleValue() > o2.getWeight().doubleValue()){
            return 1;
        } else if (o2.getWeight().doubleValue() > o1.getWeight().doubleValue()){
            return -1;
        }

        if(o1.getVertex().intValue() > o2.getVertex().intValue()){
            return 1;
        } else if(o2.getVertex().intValue() > o1.getVertex().intValue()){
            return -1;
        }

        return 0;
    }

}