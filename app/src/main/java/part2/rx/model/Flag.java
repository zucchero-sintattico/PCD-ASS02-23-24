package part2.rx.model;

public class Flag {
    private boolean flag = true;

    synchronized public void setFlag() {
        this.flag = true;
    }

    synchronized public void unsetFlag() {
        this.flag = false;
    }

    synchronized public boolean getFlag() {
        return this.flag;
    }
}
