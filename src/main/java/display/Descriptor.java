package display;

import java.util.List;

public class Descriptor {
    StringBuilder descriptor;

    public Descriptor(){
        this.descriptor = new StringBuilder();
    }

    public StringBuilder getDescriptor() { return descriptor; }
    public void setDescriptor(StringBuilder descriptor) { this.descriptor = descriptor; }

    public void updateDescriptor(String str){
        descriptor.append(str).append("\n");
    }
    public void clearDescriptor(){
        descriptor = new StringBuilder();
    }

    @Override
    public String toString() {
        return descriptor.toString();
    }
}
