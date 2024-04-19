import java.util.Collections;
import java.util.Vector;

import parcs.AM;
import parcs.AMInfo;

public class HullComputing implements AM {

    @SuppressWarnings("unchecked")
    @Override
    public void run(AMInfo info) {
        System.out.println("Hull");
        Vector<Vertex> filtered_vertices = new Vector<Vertex>();

        final Vertex center = (Vertex)info.parent.readObject();
        final Vector<Vertex> vertices = (Vector<Vertex>)info.parent.readObject();

        double x1 = vertices.get(0).get_x();
        double y1 = vertices.get(0).get_y();
        double x2 = vertices.get(vertices.size() - 1).get_x();
        double y2 = vertices.get(vertices.size() - 1).get_y();

        double cx1 = Math.sqrt((center.get_x() - x1) * (center.get_x() - x1) + (center.get_y() - y1) * (center.get_y() - y1));
        double cx2 = Math.sqrt((center.get_x() - x2) * (center.get_x() - x2) + (center.get_y() - y2) * (center.get_y() - y2));

        double lambda = cx1 / (cx2 - cx1);

        double x_star = (center.get_x() + lambda * x2) / (1 + lambda);
        double y_star = (center.get_y() + lambda * y2) / (1 + lambda);

        double a = x_star - x1;
        double b = y_star - y1;

        final Vector<InnerHullComputerVertex> height_vertices = new Vector<InnerHullComputerVertex>(vertices.size());

        for (int i = 0; i < vertices.size(); i++) {
            height_vertices.add(new InnerHullComputerVertex(vertices.get(i).get_x(), vertices.get(i).get_y()));
        }

        for (int i = 0; i < vertices.size(); i++) {
            double x_p = height_vertices.get(i).get_x();
            double y_p = height_vertices.get(i).get_y();

            double y_h = (a * a * center.get_y() + a * b * (x_p - center.get_x()) + b * b * y_p) / (a * a + b * b);
            double x_h = (a / b) * (y_h - center.get_y()) + center.get_x();

            height_vertices.get(i).set_x_proj(x_h);
            height_vertices.get(i).set_y_proj(y_h);
            height_vertices.get(i).set_height(Math.sqrt((x_p - x_h) * (x_p - x_h) + (y_p - y_h) * (y_p - y_h)));
        }

        Collections.sort(height_vertices);

        double lx = 0.0;
        double ly = 0.0;
        double rx = 0.0;
        double ry = 0.0;

        filtered_vertices.add(
            new Vertex(
                height_vertices.get(vertices.size() - 1).get_x(),
                height_vertices.get(vertices.size() - 1).get_y()
            )  
        );

        lx = height_vertices.get(vertices.size() - 1).get_x_proj();
        rx = height_vertices.get(vertices.size() - 1).get_x_proj();

        ly = height_vertices.get(vertices.size() - 1).get_y_proj();
        ry = height_vertices.get(vertices.size() - 1).get_y_proj();

        for (int i = vertices.size() - 2; i > 0; i--) {
            if (!(height_vertices.get(i).get_x_proj() > lx && height_vertices.get(i).get_x_proj() < rx &&
            ((height_vertices.get(i).get_y_proj() > ly && height_vertices.get(i).get_y_proj() < ry) || (height_vertices.get(i).get_y_proj() < ly && height_vertices.get(i).get_y_proj() > ry)))) {
                filtered_vertices.add(
                    new Vertex(
                        height_vertices.get(i).get_x(),
                        height_vertices.get(i).get_y()
                    )  
                );

                if (height_vertices.get(i).get_x_proj() > rx) {
                    rx = height_vertices.get(i).get_x_proj();
                    ry = height_vertices.get(i).get_y_proj();
                } else {
                    lx = height_vertices.get(i).get_x_proj();
                    ly = height_vertices.get(i).get_y_proj();
                }
            }
        }

        info.parent.write(filtered_vertices);
    }
    
}