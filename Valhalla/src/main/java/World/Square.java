package World;

public class Square {
    Terrain terrain;
    boolean visible;

    public Square(Terrain terrain, boolean visible) {
        this.terrain = terrain;
        this.visible = visible;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
