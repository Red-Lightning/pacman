package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

/*
 * Assets singleton for convenient storage of game image and sound assets
 */
public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();

    public static final Assets instance = new Assets();

    private AssetManager assetManager;

    public LevelAssets levelAssets;


    private Assets() { /* prevent class creation */ }

    public void init() {
        this.assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load("images/pacman.pack.atlas", TextureAtlas.class);
        assetManager.finishLoading();

        TextureAtlas atlas = assetManager.get("images/pacman.pack.atlas");
        levelAssets = new LevelAssets(atlas);

    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }


    public class LevelAssets {

        public final TextureAtlas.AtlasRegion maze;

        public LevelAssets(TextureAtlas atlas) {
            maze = atlas.findRegion("maze");
        }


    }

}
