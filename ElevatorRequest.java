import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single line of the input data, which is a request for the elevator to come.
 *
 * @author Matteo Golin, 101220709
 * @author Grant Achuzia, 101222695
 * @author Saja Fawagreh, 101217326
 * @author Javeria Sohail, 101197163
 * @author Yousef Hammad, 101217858
 * @version 0.0.0
 */
public class ElevatorRequest {

    /**
     * The time that the elevator request was made.
     */
    private LocalTime timestamp;
    /**
     * The direction that the requester wants to travel in.
     */
    private Direction direction;
    /**
     * The origin floor for the elevator request.
     */
    private int origin;
    /**
     * The destination floor for the elevator request.
     */
    private int destination;

    public ElevatorRequest(String input_line) {
        String[] elements = input_line.split(" ");
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("H:m:s.S");
        this.timestamp = LocalTime.parse(elements[0], parser);
        this.origin = Integer.parseInt(elements[1]);
        this.direction = Direction.valueOf(elements[2]);
        this.destination = Integer.parseInt(elements[3]);
    }

    @Override
    public String toString() {
        String self = "Timestamp: " + this.timestamp + " Direction: " + this.direction;
        self += " To: " + this.destination + " From: " + this.origin;
        return self;
    }
}

/**
 * Represents a direction for the elevator to travel in.
 *
 * @author Matteo Golin, 101220709
 * @author Grant Achuzia, 101222695
 * @author Saja Fawagreh, 101217326
 * @author Javeria Sohail, 101197163
 * @author Yousef Hammad, 101217858
 * @version 0.0.0
 */
enum Direction {
    Up,
    Down,
}
