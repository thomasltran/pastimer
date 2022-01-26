package com.pastimer;
//bugger bruh

import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TimerScreen implements Screen {

    private Stage stage;
    private Game game;
    private Time timer;
    private long currTime;
    private Button add;
    private Button subtract;
    private TextButton welcomeScreen;
    private TextButton mineSweeperScreen;
    private TextField toDoInput;
    private TextField[] timeEdit;
    private Label[] timeDisplay;
    private boolean editActive;
    private Label colon;
    int count;

    public TimerScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        timer = new Time();
        count = 0;
        timeEdit = new TextField[2];
        timeDisplay = new Label[2];
        editActive = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (!event.isHandled() && editActive) {
                    stage.unfocusAll();
                    editActive = false;
                }
            }
        });

        buildTimer();
        buildTimerEdit();
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

        timeDisplay[0].setText(timer.getString(timer.getMinute()));
        timeDisplay[1].setText(timer.getString(timer.getSecond()));
        System.out.println(timeDisplay[1].getWidth());

        if (TimeUtils.nanosToMillis(currTime) + 1000 < TimeUtils.nanosToMillis(TimeUtils.nanoTime())) {
            currTime = TimeUtils.nanoTime();
            timer.subtract();
        }

        if (subtract.isPressed()) {
            timer.subtract();
        }

        if (welcomeScreen.isPressed())
            game.setScreen(new WelcomeScreen(game));
        if (mineSweeperScreen.isPressed())
            game.setScreen(new MineSweeperScreen(game));

        if (editActive) {
            int[] newTime = new int[2];
            for (int i = 0; i < timeEdit.length; i++) {
                if (timeEdit[i].getText().equals(""))
                    newTime[i] = 0;
                else
                    newTime[i] = Integer.parseInt(timeEdit[i].getText());
            }

            timer = new Time(newTime[0], newTime[1]);
        } else {
            removeTimerEdit();
        }
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
        colon = new Label(":", Pastimer.skin, "time");
        colon.setPosition(Gdx.graphics.getWidth() / 2 - colon.getWidth() / 2, Gdx.graphics.getHeight() - 225);
        colon.setAlignment(1);

        for (int i = 0; i < timeDisplay.length; i++) {
            if (i == 0) {
                timeDisplay[i] = new Label(timer.getString(timer.getMinute()), Pastimer.skin, "time");
                timeDisplay[i].setPosition(Gdx.graphics.getWidth()/2-timeDisplay[i].getWidth()-colon.getWidth()-10, Gdx.graphics.getHeight()-239);
            } else if (i == 1) {
                timeDisplay[i] = new Label(timer.getString(timer.getSecond()), Pastimer.skin, "time");
                timeDisplay[i].setPosition(Gdx.graphics.getWidth()/2+colon.getWidth()+10, Gdx.graphics.getHeight()-239);
            }
            timeDisplay[i].setTouchable(Touchable.enabled);
            timeDisplay[i].setAlignment(1);
            timeDisplay[i].addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    editActive = true;
                    return true;
                }
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    count++;
                    System.out.println("touch" + count);
                    buildTimerEdit();
                    displayTimerEdit();
                    
                }
            });
            stage.addActor(timeDisplay[i]);
        }
        stage.addActor(colon);
        //stage.addActor(timeDisplay);
    }

    private void buildTimerEdit() {

        for (int i = 0; i < timeEdit.length; i++) {
            if (i == 0) {
                timeEdit[i] = new TextField(timer.getString(timer.getMinute()), Pastimer.skin, "time");
                timeEdit[i].setSize(237, 215);
                timeEdit[i].setPosition(colon.getX()+colon.getWidth()/2-timeEdit[i].getWidth()-22, Gdx.graphics.getHeight() - 227);
            } else if (i == 1) {
                timeEdit[i] = new TextField(timer.getString(timer.getSecond()), Pastimer.skin, "time");
                timeEdit[i].setSize(237, 215);
                timeEdit[i].setPosition(colon.getX()+colon.getWidth()/2+21, Gdx.graphics.getHeight() - 227);
            }
            timeEdit[i].setFocusTraversal(true);
            timeEdit[i].setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
            timeEdit[i].setAlignment(1);
            timeEdit[i].setMaxLength(2);
        }

    }

    private void displayTimerEdit() {
        for (int i = 0; i < timeEdit.length; i++) {
            stage.addActor(timeEdit[i]);
        }
    }

    private void removeTimerEdit() {
        for (int i = 0; i < timeEdit.length; i++) {
            timeEdit[i].remove();
        }
    }


    private void buildButtons() {
        subtract = new Button(Pastimer.skin);
        subtract.setPosition(timeDisplay[0].getX() + 30, timeDisplay[0].getY() - 30);
        stage.addActor(subtract);
        currTime = TimeUtils.nanoTime();
    }
}