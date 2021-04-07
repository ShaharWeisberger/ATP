package algorithms.search;

import java.util.Objects;

public abstract class AState  {

    private String key;
    private AState cameFrom;
    private int cost;

    public AState(String name, AState cameFrom, int cost) {
        this.key = name;
        this.cameFrom = cameFrom;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AState aState = (AState) o;
        return cost == aState.cost && Objects.equals(key, aState.key) && Objects.equals(cameFrom, aState.cameFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, cameFrom, cost);
    }

    public String getKey() {
        return key;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
