package model;

public class Agent extends Thread {

    public String id;
    private Environment env;

    public Agent(String id, Environment environment) {
        this.id = id;
        this.env = environment;
    }

    @Override
    public void run() {
        while (true) {
            // Sense
            try {
                this.sense();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//             Decide
            this.decide(); // No Sync
//            // Act
            try {
                this.act();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sense() throws InterruptedException {
        this.env.sense(this);
//        System.out.println("[AGENT: " + id + "] Sense");
    }

    private void decide() {
//        this.env.decide(this);
//        System.out.println("[AGENT: " + id + "] Decide");
    }

    private void act() throws InterruptedException {
        this.env.act(this);
//        System.out.println("[AGENT: " + id + "] Act");
    }

}
