package entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

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
        this.pos = new Vector2(Constants.WIDTH/28*loc[1] + 15, 15 + Constants.HEIGHT - Constants.HEIGHT/31*(loc[0]+1));
        region = Assets.instance.pacmanAssets.pacmanRight1;
        startTime = TimeUtils.nanoTime();
        moveState = MoveState.RIGHT;
    }

    private Vector2 locToPos() {
        Vector2 initialPos = new Vector2(Constants.WIDTH/28*loc[1] + 15, 15 + Constants.HEIGHT - Constants.HEIGHT/31*(loc[0]+1));
        return initialPos;
    }

    /* Methods to move pacman in the four directions */
    public void moveRight() {
        moveState = MoveState.RIGHT;
        pos.x += Constants.MOVE_X_SPEED;
    }

    public void moveUp() {
        moveState = MoveState.UP;
        pos.y += Constants.MOVE_Y_SPEED;
    }

    public void moveLeft() {
        moveState = MoveState.LEFT;
        pos.x += -Constants.MOVE_X_SPEED;
    }

    public void moveDown() {
        moveState = MoveState.DOWN;
        pos.y += -Constants.MOVE_Y_SPEED;
    }

    /* Update pacman state */
    public void update() {
        float currentTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        region = Assets.instance.pacmanAssets.pacmanRight.getKeyFrame(currentTime);

        switch (moveState) {
            case UP:
                region = Assets.instance.pacmanAssets.pacmanUp.getKeyFrame(currentTime);
                break;
            case DOWN:
                region = Assets.instance.pacmanAssets.pacmanDown.getKeyFrame(currentTime);
                break;
            case LEFT:
                region = Assets.instance.pacmanAssets.pacmanLeft.getKeyFrame(currentTime);
                break;
            case RIGHT:
                region = Assets.instance.pacmanAssets.pacmanRight.getKeyFrame(currentTime);
                break;
        }

        Gdx.app.log("Loc", "" + loc[1]);
        loc[1] = (int) ((pos.x - 15)/(Constants.WIDTH/Constants.MAZE_LENGTH));
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

    enum MoveState {
        UP, DOWN, LEFT, RIGHT;
    }
}
