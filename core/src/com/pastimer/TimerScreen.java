package com.pastimer;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TimerScreen implements Screen, InputProcessor {

    private Stage stage;
    private Game game;
    private Time timer;
    private Label timeDisplay;
    private long currTime;
    private Button add;
    private Button subtract;
    private TextButton welcomeScreen;
    private TextButton mineSweeperScreen;
    private TextField toDoInput;
    private TextField[] timeEdit;
    private TextField timeEditHour;
    private TextField timeEditMinute;
    private TextField timeEditSecond;
    int count;

    public TimerScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        timer = new Time();
        count = 0;
        timeEdit = new TextField[3];
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        buildTimer();
        buildButtons();

        welcomeScreen = new TextButton("Welcome", Pastimer.skin);
        welcomeScreen.setPosition(10, 10);
        stage.addActor(welcomeScreen);

        mineSweeperScreen = new TextButton("Mine Sweeper", Pastimer.skin);
        mineSweeperScreen.setPosition(10, 40);
        stage.addActor(mineSweeperScreen);

       /* toDoInput = new TextField("hello world", Pastimer.skin);

        toDoInput.setPosition(500, 500);
        toDoInput.setSize(150, 150);
        stage.addActor(toDoInput);*/
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(234 / 255.0f, 172 / 255.0f, 172 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        timeDisplay.setText(timer.getString(timer.getHour()) + ":"
                + timer.getString(timer.getMinute()) + ":"
                + timer.getString(timer.getSecond()));

        if (TimeUtils.nanosToMillis(currTime) + 1000 < TimeUtils.nanosToMillis(TimeUtils.nanoTime())) {
            currTime = TimeUtils.nanoTime();
            timer.subtract();
        }

        if (add.isPressed()) {
            timer.add();
            stage.setKeyboardFocus(null);
        }
        if (subtract.isPressed()) {
            timer.subtract();
        }

        if (welcomeScreen.isPressed())
            game.setScreen(new WelcomeScreen(game));
        if (mineSweeperScreen.isPressed())
            game.setScreen(new MineSweeperScreen(game));
    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }

    private void buildTimer() {
        timeDisplay = new Label(timer.getString(timer.getHour()) + ":"
                + timer.getString(timer.getMinute()) + ":"
                + timer.getString(timer.getSecond()), Pastimer.skin, "time");
        timeDisplay.setPosition(Gdx.graphics.getWidth() / 2 - 386, Gdx.graphics.getHeight() - 239);
        timeDisplay.setTouchable(Touchable.enabled);
        System.out.println(timeDisplay.getWidth() + " " + timeDisplay.getHeight());
        timeDisplay.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                count++;
                System.out.println("touch" + count);
                buildTimerEdit();
            }
        });
        stage.addActor(timeDisplay);
    }

    private void buildTimerEdit() {

        for (int i = 0; i < timeEdit.length; i++) {
            if (i == 0) {
                timeEdit[i] = new TextField(timer.getString(timer.getHour()), Pastimer.skin, "time");
                timeEdit[i].setPosition(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() - 227);
                timeEdit[i].setFocusTraversal(true);
            } else if (i == 1) {
                timeEdit[i] = new TextField(timer.getString(timer.getMinute()), Pastimer.skin, "time");
                timeEdit[i].setPosition(Gdx.graphics.getWidth() / 2 - 119, Gdx.graphics.getHeight() - 227);
                timeEdit[i].setFocusTraversal(true);
            } else if (i == 2) {
                timeEdit[i] = new TextField(timer.getString(timer.getSecond()), Pastimer.skin, "time");
                timeEdit[i].setPosition(Gdx.graphics.getWidth() / 2 + 162, Gdx.graphics.getHeight() - 227);
                timeEdit[i].setFocusTraversal(false);
            }

            timeEdit[i].setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            timeEdit[i].setSize(237, 215);
            timeEdit[i].setAlignment(1);
            timeEdit[i].setMaxLength(2);
        }

        for (int i = 0; i < timeEdit.length; i++) {
            stage.addActor(timeEdit[i]);
        }
    }


    private void buildButtons() {
        add = new Button(Pastimer.skin);
        add.setPosition(timeDisplay.getX(), timeDisplay.getY() - 30);
        stage.addActor(add);
        subtract = new Button(Pastimer.skin);
        subtract.setPosition(timeDisplay.getX() + 30, timeDisplay.getY() - 30);
        stage.addActor(subtract);
        currTime = TimeUtils.nanoTime();
    }

    @Override
    public boolean keyDown(int var1) {
        return false;
    }

    @Override
    public boolean keyUp(int var1) {
        return false;
    }

    @Override
    public boolean keyTyped(char var1) {
        return false;
    }

    @Override
    public boolean touchDown(int var1, int var2, int var3, int var4) {
        return true;
    }

    @Override
    public boolean touchUp(int var1, int var2, int var3, int var4) {
        System.out.println("t");
        return true;
    }

    @Override
    public boolean touchDragged(int var1, int var2, int var3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int var1, int var2) {
        return false;
    }

    @Override
    public boolean scrolled(float var1, float var2) {
        return false;
    }
}