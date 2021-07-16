package ua.com.alevel.entity;

public class Problem {
    private int id;
    private int fromId;
    private int toId;

    public Problem(int id, int fromId, int toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
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

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                '}';
    }
}