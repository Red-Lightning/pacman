package game.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import util.Assets;


public class GameScreen implements Screen {

    ExtendViewport viewport;
    SpriteBatch batch;
    Level level;
    ShapeRenderer renderer;


    @Override
    public void show() {
        viewport = new ExtendViewport(256, 256);
        batch = new SpriteBatch();
        Assets.instance.init();
        level = new Level();
        renderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(level);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        float width = Assets.instance.levelAssets.maze.getRegionWidth();
        float height = Assets.instance.levelAssets.maze.getRegionHeight();

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(new Color(0.7f, 0.3f, 0.2f, 1));
        int[][] tiles = Level.tiles;

        for(int i = 0; i < 31; i++) {
            for(int j = 0; j < 28; j++) {
                if( tiles[i][j] == 1 ) {
                    renderer.rect(width/28*j + 15, 15 + height - height/31*(i+1), width/28, height/31);
                }
            }
        }
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.DARK_GRAY);
        for(int i = 0; i < 29; i++) {
            for(int j = 0; j < 32; j++) {
                renderer.line(width/28*i + 15, 15, width/28*i + 15, height + 15);
                renderer.line(15, height/31*j + 15, width + 15, height/31*j + 15);
            }
        }
        renderer.end();

        // update current state of the game
        level.update();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        level.render(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
