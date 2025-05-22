package GameSystem;

public class GameSystem {
    public double wireLimit;
    public double wireUsed = 0;
    public int coins = 0;
    public int packettri;
    public int packetrect;
    public int totalPackets;
    public int packetsReceived = 0;
    public int timelimit;
    public int destroyedpackets = 0;

    public GameSystem(double wireLimit, int packetrect, int packettri, int timelimit) {
        this.wireLimit = wireLimit;
        this.packetrect = packetrect;
        this.packettri = packettri;
        totalPackets = packetrect + packettri;
        this.timelimit = timelimit;
    }

}
