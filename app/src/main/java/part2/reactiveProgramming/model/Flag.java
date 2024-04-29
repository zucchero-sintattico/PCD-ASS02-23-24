package part2.reactiveProgramming.model;

public class Flag {
    private boolean flag = false;

    synchronized public void set() {this.flag = true;}
    synchronized public void unset() {this.flag = false;}
    synchronized public boolean isSet() {return this.flag;}
}