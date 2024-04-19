import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import parcs.AMInfo;
import parcs.channel;
import parcs.point;
import parcs.task;

public class TaskRunner {

    @SuppressWarnings({ 
        "unchecked",
        // "unused"
    })
    public static void main(String[] args) throws Exception {
        final int AMOUNT_OF_POINTS = 100;
        final int AMOUNT_OF_POINTS_IN_SECTOR = 5;
        
        final int FIELD_X_LEFT_BORDER = -100;
        final int FIELD_X_RIGHT_BORDER = 100;
        final int FIELD_Y_LEFT_BORDER = -100;
        final int FIELD_Y_RIGHT_BORDER = 100;
        
        final int NUM_OF_THREADS_FOR_PRECOMPUTING = 10;


        Vector<Vertex> vertices = new Vector<Vertex>(AMOUNT_OF_POINTS);
        Vector<Vertex> filtered_vertices = new Vector<Vertex>();
        
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
        task center_of_mass_task = new task();
        
        center_of_mass_task.addJarFile("MassComputer.jar");
        
        AMInfo info = new AMInfo(center_of_mass_task, null);

        point[] points = new point[NUM_OF_THREADS_FOR_PRECOMPUTING];
        channel[] channels = new channel[NUM_OF_THREADS_FOR_PRECOMPUTING];

        final int AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING = AMOUNT_OF_POINTS / NUM_OF_THREADS_FOR_PRECOMPUTING;
        for (int i = 0; i < NUM_OF_THREADS_FOR_PRECOMPUTING; i++) {
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

        for (int i = 0; i < NUM_OF_THREADS_FOR_PRECOMPUTING; ++i) {
            x_coord += channels[i].readDouble();
            y_coord += channels[i].readDouble();
        }

        Vertex center_of_mass = new Vertex(x_coord / AMOUNT_OF_POINTS, y_coord / AMOUNT_OF_POINTS);

        System.out.println("Center of mass:");
        System.out.println(center_of_mass.get_x());
        System.out.println(center_of_mass.get_y());

        center_of_mass_task.end();


        // Angle computing
        task angle_task = new task();
        
        angle_task.addJarFile("AngleComputer.jar");

        info = new AMInfo(angle_task, null);

        points = new point[NUM_OF_THREADS_FOR_PRECOMPUTING];
        channels = new channel[NUM_OF_THREADS_FOR_PRECOMPUTING];

        for (int i = 0; i < NUM_OF_THREADS_FOR_PRECOMPUTING; i++) {
            Vector<Vertex> vertices_for_thread = new Vector<Vertex>(AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING);
            
            for (int j = 0; j < AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING; j++) {
                vertices_for_thread.add(vertices.get(i * AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING + j));
            }

            points[i] = info.createPoint();
            channels[i] = points[i].createChannel();
            points[i].execute("AngleComputer");
            channels[i].write(center_of_mass);
            channels[i].write(vertices_for_thread);
        }

        for (int i = 0; i < NUM_OF_THREADS_FOR_PRECOMPUTING; ++i) {
            final Vector<Vertex> angled_vertices = (Vector<Vertex>)channels[i].readObject();

            for (int j = 0; j < AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING; j++) {
                vertices
                    .get(i * AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING + j)
                    .set_angle(
                        angled_vertices
                            .get(j)
                            .get_angle()
                    );

                // System.out.println("Angle for computing module [" + i + ", " + j + "] is: "
                //     + vertices.get(i * AMOUNT_OF_POINTS_IN_ANGLE_COMPUTING + j).get_angle());
            }
        }

        System.out.println("Vertices polar angles has been computed!");

        angle_task.end();

        //Sorting vertices depending on its polar angle
        Collections.sort(vertices);

        System.out.println("Vertices has been sorted by angle!");


        // Sector hull computing
        final int AMOUNT_OF_SECTORS = AMOUNT_OF_POINTS / AMOUNT_OF_POINTS_IN_SECTOR;

        task hull_task = new task();
        
        hull_task.addJarFile("HullComputing.jar");

        info = new AMInfo(hull_task, null);

        points = new point[AMOUNT_OF_SECTORS];
        channels = new channel[AMOUNT_OF_SECTORS];

        for (int i = 0; i < AMOUNT_OF_SECTORS; i++) {
            Vector<Vertex> vertices_for_thread = new Vector<Vertex>(AMOUNT_OF_POINTS_IN_SECTOR);
            
            for (int j = 0; j < AMOUNT_OF_POINTS_IN_SECTOR; j++) {
                vertices_for_thread.add(vertices.get(i * AMOUNT_OF_POINTS_IN_SECTOR + j));
            }

            points[i] = info.createPoint();
            channels[i] = points[i].createChannel();
            points[i].execute("HullComputing");
            channels[i].write(center_of_mass);
            channels[i].write(vertices_for_thread);
        }


        for (int i = 0; i < AMOUNT_OF_SECTORS; ++i) {
            final Vector<Vertex> thread_filtered_vertices = (Vector<Vertex>)channels[i].readObject();
            
            filtered_vertices.addAll(thread_filtered_vertices);
        }

        System.out.println("Vertices has been filtered out!");

        hull_task.end();

        for (int i = 0; i < filtered_vertices.size(); i++) {
            System.out.println("Hull vertex [" + i + "] is: (" + 
                filtered_vertices.get(i).get_x() + ", " + 
                filtered_vertices.get(i).get_y() + ")"
            );
        }
    }
}
