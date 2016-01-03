package entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import game.pacman.Level;
import util.Assets;
import util.Constants;

public class Pacman {

    private int[] loc = new int[2];
    private Vector2 pos;
    private TextureRegion region;
    private long startTime;
    private MoveState moveState;


    public Pacman() {
        loc[0] = 23; loc[1] = 14;
        this.pos = new Vector2(Constants.WIDTH/28*loc[1] + 15, 15 + Constants.HEIGHT/31*(30 - loc[0]));
        region = Assets.instance.pacmanAssets.pacmanRight1;
        startTime = TimeUtils.nanoTime();
        moveState = MoveState.STATIONARY;
    }

    private Vector2 locToPos() {
        Vector2 initialPos = new Vector2(15 + (loc[1])*Constants.WIDTH/28.0f, 15 + (30-loc[0])*Constants.HEIGHT/31.0f);
        Gdx.app.log("pos", pos.x+"");
        Gdx.app.log("calc pos", ""+(15 + (loc[1]+1)*Constants.WIDTH/28.0f));
        return initialPos;
    }

    /* Helper methods for checking valid move locations */
    private boolean canMoveRight() {
        int[][] tiles = Level.tiles;
        if(tiles[loc[0]][loc[1]+1] != 0) return true;
        return false;
    }

    private boolean canMoveUp() {
        int[][] tiles = Level.tiles;
        //Gdx.app.log("LOC", 30-loc[0]+"");
        if(tiles[loc[0]-1][loc[1]] != 0 || pos.y < locToPos().y) return true;
        return false;
    }

    private boolean canMoveDown() {
        int[][] tiles = Level.tiles;
        if(tiles[loc[0]+1][loc[1]] != 0 || pos.y > locToPos().y) return true;
        return false;
    }

    private boolean canMoveLeft() {
        int[][] tiles = Level.tiles;
        if(tiles[loc[0]][loc[1]-1] != 0 || pos.x > locToPos().x) return true;
        return false;
    }


    /* Methods to move pacman in the four directions */
    public void moveRight() {
        if(canMoveRight()) {
            moveState = MoveState.RIGHT;
            pos.x += Constants.MOVE_X_SPEED;
        }
    }

    public void moveUp() {
        if(canMoveUp()) {
            moveState = MoveState.UP;
            pos.y += Constants.MOVE_Y_SPEED;
        }
    }

    public void moveLeft() {
        if(canMoveLeft()) {
            moveState = MoveState.LEFT;
            pos.x += -Constants.MOVE_X_SPEED;
        }
    }

    public void moveDown() {
        if(canMoveDown()) {
            moveState = MoveState.DOWN;
            pos.y += -Constants.MOVE_Y_SPEED;
        }
    }


    private float rowToY() {
        return 15 + Constants.HEIGHT/30*(30 - loc[0]);
    }

    private float colToX() {
        return Constants.WIDTH/28*loc[1] + 15;
    }

    /* Update pacman state */
    public void update() {
        float currentTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        region = Assets.instance.pacmanAssets.pacmanRight.getKeyFrame(currentTime);

        switch (moveState) {
            case UP:
                region = Assets.instance.pacmanAssets.pacmanUp.getKeyFrame(currentTime);
                moveUp();
                break;
            case DOWN:
                region = Assets.instance.pacmanAssets.pacmanDown.getKeyFrame(currentTime);
                moveDown();
                break;
            case LEFT:
                region = Assets.instance.pacmanAssets.pacmanLeft.getKeyFrame(currentTime);
                moveLeft();
                break;
            case RIGHT:
                region = Assets.instance.pacmanAssets.pacmanRight.getKeyFrame(currentTime);
                moveRight();
                break;
            case STATIONARY:
                break;
        }

        //Gdx.app.log("Row", "" + loc[0]);
        //Gdx.app.log("Col", "" + loc[1]);
        if(loc[1] > 1) {
            loc[1] = (int) ((pos.x - 15) / (Constants.WIDTH / Constants.MAZE_LENGTH));
        }
        loc[0] = (int) ((15 + Constants.HEIGHT - pos.y)/(Constants.HEIGHT/Constants.MAZE_HEIGHT));
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                region.getTexture(),
                pos.x , pos.y ,
                0, 0,
                region.getRegionWidth(), region.getRegionHeight(),
                0.9f, 0.9f,
                0,
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight(),
                false,
                false);
    }

    public void renderBoundingBox(ShapeRenderer renderer) {
        renderer.setColor(Color.GOLD);
        renderer.rect(pos.x, pos.y, region.getRegionWidth(), region.getRegionHeight());
    }

    public int[] getLoc() {
        return loc;
    }

    /* Enum to hold current state of pacman */
    enum MoveState {
        UP, DOWN, LEFT, RIGHT, STATIONARY;
    }
}
