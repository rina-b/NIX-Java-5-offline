package ua.com.alevel.entity;

public class Route {
    private int id;
    private int fromId;
    private int toId;
    private int cost;

    public Route(int id, int toId, int fromId, int cost) {
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int id) {
        this.fromId = id;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int id) {
        this.toId = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", cost=" + cost +
                '}';
    }
}