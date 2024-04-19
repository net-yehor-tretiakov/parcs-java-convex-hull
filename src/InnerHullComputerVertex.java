import java.io.Serializable;
import java.util.Comparator;

public class InnerHullComputerVertex implements Serializable, java.lang.Comparable<InnerHullComputerVertex>, Comparator<InnerHullComputerVertex> {
    private final double x;
    private final double y;
    
    private Double height;
    private Double x_proj;
    private Double y_proj;

    public InnerHullComputerVertex(double x, double y) {
        this.x = x;
        this.y = y;

        this.height = null;
        this.x_proj = null;
        this.y_proj = null;
    }

    public double get_x() {
        return this.x;
    }

    public double get_y() {
        return this.y;
    }

    public Double get_height() {
        return this.height;
    }

    public void set_height(Double height) {
        this.height = height;
    }

    public Double get_x_proj() {
        return this.x_proj;
    }

    public void set_x_proj(Double x_proj) {
        this.x_proj = x_proj;
    }

    public Double get_y_proj() {
        return this.y_proj;
    }

    public void set_y_proj(Double y_proj) {
        this.y_proj = y_proj;
    }

    @Override
    public int compareTo(InnerHullComputerVertex arg0) {
        return this.get_height().compareTo(arg0.get_height());
    }

    @Override
    public int compare(InnerHullComputerVertex arg0, InnerHullComputerVertex arg1) {
        return arg0.compareTo(arg1);
    }
}