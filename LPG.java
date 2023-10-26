
import java.util.*;
import java.util.scanner;

// Observer Pattern: Observers to listen to state changes
interface Observer {
    void update(String message);
}

// Concrete Observer
class User implements Observer {
    private String name;
    private String classname;
    int marks;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
}

// Context using State Pattern
class CallCenter {
    private State currentState;
    private List<Observer> observers = new ArrayList<>();

    public CallCenter() {
        this.currentState = new IdleState();
    }

    public void setState(State state) {
        this.currentState = state;
        notifyObservers("State changed to " + state.getClass().getSimpleName());
    }

    public void performAction() {
        currentState.performAction(this);
    }

    // Observer Pattern: Attach an observer
    public void attach(Observer observer) {
        observers.add(observer);
    }

    // Observer Pattern: Notify observers
    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

// State Pattern: Abstract State
interface State {
    void performAction(CallCenter callCenter);
}

// Concrete States
class IdleState implements State {
    @Override
    public void performAction(CallCenter callCenter) {
        System.out.println("Call Center is Idle");
    }
}

class RefillBookingState implements State {
    @Override
    public void performAction(CallCenter callCenter) {
        System.out.println("Refill Booking initiated");
        callCenter.setState(new IdleState());
    }
}

class TransferLPGState implements State {
    @Override
    public void performAction(CallCenter callCenter) {
        System.out.println("Transfer of LPG initiated");
        callCenter.setState(new IdleState());
    }
}

class ChangeRegulatorState implements State {
    @Override
    public void performAction(CallCenter callCenter) {
        System.out.println("Change of Regulator initiated");
        callCenter.setState(new IdleState());
    }
}

// Command Pattern: Command Interface
interface Command {
    void execute();
}

// Concrete Commands
class RefillBookingCommand implements Command {
    private CallCenter callCenter;

    public RefillBookingCommand(CallCenter callCenter) {
        this.callCenter = callCenter;
    }

    @Override
    public void execute() {
        callCenter.setState(new RefillBookingState());
        callCenter.performAction();
    }
}

class TransferLPGCommand implements Command {
    private CallCenter callCenter;

    public TransferLPGCommand(CallCenter callCenter) {
        this.callCenter = callCenter;
    }

    @Override
    public void execute() {
        callCenter.setState(new TransferLPGState());
        callCenter.performAction();
    }
}

class ChangeRegulatorCommand implements Command {
    private CallCenter callCenter;

    public ChangeRegulatorCommand(CallCenter callCenter) {
        this.callCenter = callCenter;
    }

    @Override
    public void execute() {
        callCenter.setState(new ChangeRegulatorState());
        callCenter.performAction();
    }
}

public class LPG {
    public static void main(String[] args) {
        CallCenter callCenter = new CallCenter();
        User user1 = new User("Customer");
   
        callCenter.attach(user1);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Refill Booking");
            System.out.println("2. Transfer of LPG");
            System.out.println("3. Change of Regulator");
            System.out.println("4. Quit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Command refillBookingCommand = new RefillBookingCommand(callCenter);
                    refillBookingCommand.execute();
                    break;
                case 2:
                    Command transferLPGCommand = new TransferLPGCommand(callCenter);
                    transferLPGCommand.execute();
                    break;
                case 3:
                    Command changeRegulatorCommand = new ChangeRegulatorCommand(callCenter);
                    changeRegulatorCommand.execute();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
