import java.io.Serializable;

public class Vertex implements Serializable {
    private final double x;
    private final double y;
    
    private Double angle;
    private Double height;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;

        this.angle = null;
        this.height = null;
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

    public Double get_height() {
        return this.height;
    }

    public void set_height(Double height) {
        this.height = height;
    }
}
