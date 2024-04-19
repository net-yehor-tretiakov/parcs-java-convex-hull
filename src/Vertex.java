import java.io.Serializable;
import java.util.Comparator;

public class Vertex implements Serializable, java.lang.Comparable<Vertex>, Comparator<Vertex> {
    private final double x;
    private final double y;
    
    private Double angle;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;

        this.angle = null;
    }

    public double get_x() {
        return this.x;
    }

    public double get_y() {
        return this.y;
    }

    public Double get_angle() {
        return this.angle;
    }

    public void set_angle(Double angle) {
        this.angle = angle;
    }

    @Override
    public int compareTo(Vertex arg0) {
        return this.get_angle().compareTo(arg0.get_angle());
    }

    @Override
    public int compare(Vertex arg0, Vertex arg1) {
        return arg0.compareTo(arg1);
    }
}
