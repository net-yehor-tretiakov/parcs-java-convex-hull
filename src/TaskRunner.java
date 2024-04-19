import java.util.Random;
import java.util.Vector;

import parcs.AMInfo;
import parcs.channel;
import parcs.point;
import parcs.task;

public class TaskRunner {
    public static void main(String[] args) throws Exception {
        final int AMOUNT_OF_POINTS = 100000;
        
        final int FIELD_X_LEFT_BORDER = -100;
        final int FIELD_X_RIGHT_BORDER = 100;
        final int FIELD_Y_LEFT_BORDER = -100;
        final int FIELD_Y_RIGHT_BORDER = 100;
        
        final int NUM_OF_THREADS = 10;


        Vector<Vertex> vertices = new Vector<Vertex>(AMOUNT_OF_POINTS * 3);
        
        // Vertices generation
        Random random = new Random();
        for (int i = 0; i < (int) AMOUNT_OF_POINTS; i++) {
            vertices.add(
                new Vertex(
                    FIELD_X_LEFT_BORDER + random.nextDouble() * (FIELD_X_RIGHT_BORDER - FIELD_X_LEFT_BORDER),
                    FIELD_Y_LEFT_BORDER + random.nextDouble() * (FIELD_Y_RIGHT_BORDER - FIELD_Y_LEFT_BORDER)
                )
            );
        }

        // Center of mass computing
        task current_task = new task();
        
        current_task.addJarFile("MassComputer.jar");
        
        AMInfo info = new AMInfo(current_task, null);

        point[] points = new point[NUM_OF_THREADS];
        channel[] channels = new channel[NUM_OF_THREADS];

        final int AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING = AMOUNT_OF_POINTS / NUM_OF_THREADS;
        for (int i = 0; i < NUM_OF_THREADS; i++) {
            Vector<Vertex> vertices_for_thread = new Vector<Vertex>(AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING);
            
            for (int j = 0; j < AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING; j++) {
                vertices_for_thread.add(vertices.get(i * AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING + j));
            }

            points[i] = info.createPoint();
            channels[i] = points[i].createChannel();
            points[i].execute("MassComputer");
            channels[i].write(vertices_for_thread);
        }

        double x_coord = 0.0;
        double y_coord = 0.0;

        for (int i = 0; i < NUM_OF_THREADS; ++i) {
            x_coord += channels[i].readDouble();
            y_coord += channels[i].readDouble();
        }

        Vertex center_of_mass = new Vertex(x_coord / AMOUNT_OF_POINTS, y_coord / AMOUNT_OF_POINTS);

        System.out.println("Center of mass:");
        System.out.println(center_of_mass.get_x());
        System.out.println(center_of_mass.get_y());
    }
}
