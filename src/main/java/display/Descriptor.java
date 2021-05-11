package display;

import java.util.ArrayList;
import java.util.List;

public class Descriptor {
    private List<String> buffer;
    private final static int LINE = 8;

    public Descriptor(){
        this.buffer = new ArrayList<>();
    }

    public void flushDescriptor() {
        buffer.clear();
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
