package display;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allow message to be display in the message box under the player, each
 * time an action take place.
 * @author antoine
 */

public class Descriptor {
    private List<String> buffer;
    private final static int LINE = 9;

    public Descriptor(){
        this.buffer = new ArrayList<>();
    }

    public void updateDescriptor(String str){
        buffer.add(str);
        if (buffer.size() > LINE) {
            buffer.remove(0);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (!buffer.isEmpty()) {
            sb.append(" ").append("-".repeat(70)).append("\n");
            for (String s : buffer) {
                sb.append(" ").append(s).append("\n");
            }
            sb.append(" ").append("-".repeat(70)).append("\n");
        }

        return sb.toString();
    }
}
