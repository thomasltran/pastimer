//to-do: animate in letters, don't have to click to enter timer
package com.pastimer.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class WelcomeScreen implements Screen {

    final Pastimer app;
    OrthographicCamera camera;

    public WelcomeScreen(final Pastimer in) {
        app = in;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(220/255.0f, 133/255.0f, 133/255.0f, 255f);

        camera.update();
        app.batch.setProjectionMatrix(camera.combined);

        app.batch.begin();
        app.font.draw(app.batch, "Pastimer ", 1920/2, 1080/2);
        app.font.draw(app.batch, "Airvent", 1920/2, 100);
        app.batch.end();

        /*if (Gdx.input.isTouched()) {
            app.setScreen(new Timer(app));
            dispose();
        }*/
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}