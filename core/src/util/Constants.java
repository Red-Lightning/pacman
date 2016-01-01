package util;


public class Constants {
    public static final float WIDTH = Assets.instance.levelAssets.maze.getRegionWidth();
    public static final float HEIGHT = Assets.instance.levelAssets.maze.getRegionHeight();
    public static final float EAT_DURATION = 0.075f;
    public static final int MAZE_LENGTH = 28;
    public static final int MAZE_HEIGHT = 30;

    public static final float MOVE_X_SPEED = WIDTH/MAZE_LENGTH/4;
    public static final float MOVE_Y_SPEED = HEIGHT/MAZE_HEIGHT/4;
}
