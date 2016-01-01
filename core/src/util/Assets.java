package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import java.util.ArrayList;

/*
 * Assets singleton for convenient storage of game image and sound assets
 */
public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    public LevelAssets levelAssets;
    public PacmanAssets pacmanAssets;


    private Assets() { /* prevent class creation */ }

    public void init() {
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load("images/pacman.pack.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get("images/pacman.pack.atlas");
        levelAssets = new LevelAssets(atlas);
        pacmanAssets = new PacmanAssets(atlas);

    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class PacmanAssets {
        public final AtlasRegion pacman0;
        public final AtlasRegion pacmanLeft1;
        public final AtlasRegion pacmanLeft2;
        public final AtlasRegion pacmanRight1;
        public final AtlasRegion pacmanRight2;
        public final AtlasRegion pacmanUp1;
        public final AtlasRegion pacmanUp2;
        public final AtlasRegion pacmanDown1;
        public final AtlasRegion pacmanDown2;

        public final Animation pacmanLeft;
        public final Animation pacmanUp;
        public final Animation pacmanRight;
        public final Animation pacmanDown;

        public PacmanAssets(TextureAtlas atlas) {
            pacman0 = atlas.findRegion("pacman0");
            pacmanLeft1 = atlas.findRegion("pacman1-left");
            pacmanLeft2 = atlas.findRegion("pacman2-left");
            pacmanRight1 = atlas.findRegion("pacman1-right");
            pacmanRight2 = atlas.findRegion("pacman2-right");
            pacmanUp1 = atlas.findRegion("pacman1-up");
            pacmanUp2 = atlas.findRegion("pacman2-up");
            pacmanDown1 = atlas.findRegion("pacman1-down");
            pacmanDown2 = atlas.findRegion("pacman2-down");

            Array<AtlasRegion> pacLeftFrames = new Array<AtlasRegion>();
            pacLeftFrames.add(pacman0);
            pacLeftFrames.add(pacmanLeft1);
            pacLeftFrames.add(pacmanLeft2);
            pacmanLeft = new Animation(Constants.EAT_DURATION, pacLeftFrames, Animation.PlayMode.LOOP_PINGPONG);

            Array<AtlasRegion> pacUpFrames = new Array<AtlasRegion>();
            pacUpFrames.add(pacman0);
            pacUpFrames.add(pacmanUp1);
            pacUpFrames.add(pacmanUp2);
            pacmanUp = new Animation(Constants.EAT_DURATION, pacUpFrames, Animation.PlayMode.LOOP_PINGPONG);

            Array<AtlasRegion> pacRightFrames = new Array<AtlasRegion>();
            pacRightFrames.add(pacman0);
            pacRightFrames.add(pacmanRight1);
            pacRightFrames.add(pacmanRight2);
            pacmanRight = new Animation(Constants.EAT_DURATION, pacRightFrames, Animation.PlayMode.LOOP_PINGPONG);

            Array<AtlasRegion> pacDownFrames = new Array<AtlasRegion>();
            pacDownFrames.add(pacman0);
            pacDownFrames.add(pacmanDown1);
            pacDownFrames.add(pacmanDown2);
            pacmanDown = new Animation(Constants.EAT_DURATION, pacDownFrames, Animation.PlayMode.LOOP_PINGPONG);

        }

    }


    public class LevelAssets {

        public final AtlasRegion maze;

        public LevelAssets(TextureAtlas atlas) {
            maze = atlas.findRegion("maze");
        }


    }

}
