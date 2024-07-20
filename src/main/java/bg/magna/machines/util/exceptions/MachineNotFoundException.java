package bg.magna.machines.util.exceptions;

import org.hibernate.ObjectNotFoundException;

public class MachineNotFoundException extends IllegalArgumentException {

    public MachineNotFoundException() {
    }

    public MachineNotFoundException(String s) {
        super(s);
    }
}
