import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.io.IOException;

/**
 * The scheduler that will be responsible for assigning the correct elevators to
 * the correct floor.
 * 
 * @author Matteo Golin, 101220709
 * @author Grant Achuzia, 101222695
 * @author Saja Fawagreh, 101217326
 * @author Javeria Sohail, 101197163
 * @author Yousef Hammad, 101217858
 * @version 0.0.0
 */

public class Scheduler {

    /** The current state of the Scheduler (starts in Idle). */
    static SchedulerState state = SchedulerState.Idle;

    /** The port for sending and receiving messages. */
    static final int PORT = 2002;

    /** The port for sending to the floor subsystem. */
    static final int FLOOR_PORT = 2001;

    /** The port for sending to the elevator system. */
    static final int ELEVATOR_PORT = 2004;

    /** The length of the buffer for receiving UDP messages. */
    static final int BUFFER_LEN = 100;

    /** Executes the main logical loop of the Scheduler subsystem. */
    public static void main(String[] args) throws SocketException, IOException {

        // Create socket for receiving and sending
        DatagramSocket channel = new DatagramSocket(PORT);

        // The message buffer for receiving new UDP messages
        DatagramPacket message = null;

        // While there are still messages
        while (true) {
            switch (state) {

                case SchedulerState.Idle:
                    message = new DatagramPacket(new byte[BUFFER_LEN], BUFFER_LEN);
                    channel.receive(message);
                    state = SchedulerState.Thinking;
                    break;

                case SchedulerState.Thinking:

                    switch (message.getPort()) {
                        // If there is a message from the floor, forward it to the elevator subsystem
                        case FLOOR_PORT:
                            state = SchedulerState.Thinking;
                            System.out.println("Scheduler forwarded floor message.");
                            // TODO: handle IPs from different computers
                            message.setPort(ELEVATOR_PORT);
                            channel.send(message);
                            break;

                        // If there is a message from the elevator subsystem, forward it to the floor
                        case ELEVATOR_PORT:
                            state = SchedulerState.Thinking;
                            System.out.println("Scheduler forwarded elevator message.");
                            message.setPort(FLOOR_PORT);
                            channel.send(message);
                            break;
                    }

                    // Set state back to idle
                    state = SchedulerState.Idle;
                    break;
            }
        }
    }
}

/**
 * Describes the current state of the Scheduler.
 *
 * @author Matteo Golin, 101220709
 * @author Grant Achuzia, 101222695
 * @author Saja Fawagreh, 101217326
 * @author Javeria Sohail, 101197163
 * @author Yousef Hammad, 101217858
 * @version 0.0.0
 */
enum SchedulerState {
    Thinking,
    Idle,
}
