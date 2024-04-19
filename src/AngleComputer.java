import java.util.Vector;

import parcs.AM;
import parcs.AMInfo;

public class AngleComputer implements AM {

    @Override
    @SuppressWarnings("unchecked")
    public void run(AMInfo info) {
        final Vertex center = (Vertex)info.parent.readObject();

        final Vector<Vertex> vertices = (Vector<Vertex>)info.parent.readObject();

        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).set_angle(
                Math.atan2(
                    center.get_x() * (vertices.get(i).get_y() - center.get_y()) - center.get_y() * (vertices.get(i).get_x() - center.get_x()),
                    center.get_x() * (vertices.get(i).get_x() - center.get_x()) + center.get_y() * (vertices.get(i).get_y() - center.get_y())
                )
            );
        }

        info.parent.write(vertices);
    }
    
}
