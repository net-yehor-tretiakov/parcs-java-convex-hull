import java.util.Vector;

import parcs.AM;
import parcs.AMInfo;

public class MassComputer implements AM {

    @Override
    public void run(AMInfo info) {
        final Vector<Vertex> vertices = (Vector<Vertex>)info.parent.readObject();
        
        double x_sum = 0;
        double y_sum = 0;

        for (int i = 0; i < vertices.size(); i++) {
            x_sum += vertices.get(i).get_x();
            y_sum += vertices.get(i).get_y();
        }

        System.out.println("Sum of the vertexis: [" + x_sum + ", " + y_sum + "]");
        
        info.parent.write(x_sum);
        info.parent.write(y_sum);
    }
    
}
