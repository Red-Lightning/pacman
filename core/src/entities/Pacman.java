package entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import util.Assets;
import util.Constants;

public class Pacman {

    int[] loc = new int[2];
    Vector2 pos;
    TextureRegion region;
    long startTime;

    public Pacman() {
        loc[0] = 23; loc[1] = 14;
        this.pos = new Vector2(Constants.WIDTH/28*loc[1] + 15, 15 + Constants.HEIGHT - Constants.HEIGHT/31*(loc[0]+1));
        region = Assets.instance.pacmanAssets.pacmanRight1;
        startTime = TimeUtils.nanoTime();
    }

    private Vector2 locToPos() {
        Vector2 initialPos = new Vector2(Constants.WIDTH/28*loc[1] + 15, 15 + Constants.HEIGHT - Constants.HEIGHT/31*(loc[0]+1));
        return initialPos;
    }

    public void moveRight() {
        //Gdx.app.log("Loc", "" + loc[1]);
        pos.x += 1.5f;
    }

    public void update() {
        float currentTime = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
        region = Assets.instance.pacmanAssets.pacmanRight.getKeyFrame(currentTime);
        loc[1] = (int) ((pos.x - 15)/(Constants.WIDTH/Constants.MAZE_LENGTH));
    }

    public void render(SpriteBatch batch) {
        batch.draw(
                region.getTexture(),
                pos.x - 2, pos.y - 2,
                0, 0,
                region.getRegionWidth(), region.getRegionHeight(),
                1, 1,
                0,
                region.getRegionX(), region.getRegionY(),
                region.getRegionWidth(), region.getRegionHeight(),
                false,
                false);
    }

    public int[] getLoc() {
        return loc;
    }
}
