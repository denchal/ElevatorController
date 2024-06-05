package MichalGomulczak;

public class InteriorRequest {
    private int floor;
    private int elevator;

    public InteriorRequest(int floor, int elevator) {
        this.floor = floor;
        this.elevator = elevator;
    }

    public int getFloor() {
        return floor;
    }

    public int getElevator() {
        return elevator;
    }
}
