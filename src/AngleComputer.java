import java.util.Vector;

import parcs.AM;
import parcs.AMInfo;

public class AngleComputer implements AM {

    @Override
    public void run(AMInfo info) {
        Vector<Vertex> vertices = (Vector<Vertex>)info.parent.readObject();
    }
    
}
