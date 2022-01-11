package com.pastimer.desktop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import org.lwjgl.opengl.Display;

public class FrogScreen implements Screen {
    
    final Drop game;
    Texture frogImage;
    Texture car1Image;
    Texture car2Image;
    OrthographicCamera camera;
    int[][] objects;
    int[] direction;
    int frogX;
    int frogY;
    int length = Display.getWidth();
    int height = Display.getHeight();
    InputProcessor input;
    boolean hit = false;
    float elapsed;


        public FrogScreen(final Drop game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        frogImage = new Texture(Gdx.files.internal("peachLogo.png"));
        car1Image = new Texture(Gdx.files.internal("opensans2.png"));
        car2Image = new Texture(Gdx.files.internal("opensans1.png"));
        objects = new int[17][25];
        direction = new int[25];
        for (int i = 1; i < objects.length-1; i++){
            for (int j = 1; j < objects[0].length; j++){

                if (Math.random() < .06){
                    objects[i][j] = 2;
                    if (Math.random() <.5)
                    direction[j] = 1;
                    else 
                    direction[j] = -1;
                }
                if (Math.random() < .01){
                    objects[i][j] = 3;
                    objects[i+1][j] = 3;
                    if (Math.random() <.5)
                    direction[j] = 1;
                    else 
                    direction[j] = -1;
                }
            }            
        }
        frogX = 8;
        frogY = 0;
        objects[frogX][frogY] = 1;
        
        
        }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        boolean hit = false;
        game.font.draw(game.batch, length + " " + height , 0, 800);

        elapsed += delta; 
        if (elapsed > .3){
            elapsed = 0;
        moveCars();
        if (Gdx.input.isKeyPressed(Keys.LEFT)){
            objects[frogX][frogY] = 0;
            if (frogX > 1)
                frogX--;
            
            objects[frogX][frogY] = 1;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT)){
            objects[frogX][frogY] = 0;
            if (frogX < 15)
                frogX++;
      
            objects[frogX][frogY] = 1;
        }

        if (Gdx.input.isKeyPressed(Keys.UP)){
            objects[frogX][frogY] = 0;
            if (frogY < 24)
                frogY++;
     
            objects[frogX][frogY] = 1;
        }
        hit = collide();
        addCars();
        if (frogY == 12)
            moveUp();
        
        
        
    }
    for (int i = 1; i < objects.length-1; i++){
        for (int j = 0; j < objects[0].length; j++){
            if (objects[i][j] == 1)
                game.batch.draw(frogImage, length/15*(i-1), height/25*j, length/15, height/25);
            if (objects[i][j] == 2)
                game.batch.draw(car1Image, length/15*(i-1), height/25*j, length/15, height/25);
            if (objects[i][j] == 3)
                game.batch.draw(car2Image, length/15*(i-1), height/25*j, length/15, height/25);
        }   
    
    }
        game.batch.end();
    

        
    }

    public void moveCars(){
        for (int i = 0; i < objects[0].length; i++){
            if (direction[i] == 1){
                for (int j = objects.length-1; j > 0; j--){
                    if (objects[j][i] == 1){}
                    else if (objects[j-1][i] != 1)
                        objects[j][i] = objects[j-1][i];
                        
                }
                objects[0][i] = 0;
            }
            else{
                for (int j = 0; j < objects.length-1; j++){
                    if (objects[j][i] == 1){}
                    else if (objects[j+1][i] != 1)
                        objects[j][i] = objects[j+1][i];
                }
                objects[objects.length-1][i] = 0;
            }

        
        }
       
    
    }

    public void addCars(){
        for (int i = 0; i < objects[0].length; i++){
            if (direction[i] == 1){
                if (objects[1][i] == 0){
                    if (Math.random() < .07)
                        objects[0][i] = 2;
                    if (Math.random() < .01){
                        objects[0][i] = 3;
                        objects[1][i] = 3;
                }
            }
            }
            else{
                if (objects[objects.length-2][i] == 0){
                    if (Math.random() < .07)
                        objects[objects.length-1][i] = 2;
                    if (Math.random() < .01){
                        objects[objects.length-1][i] = 3;
                        objects[objects.length-2][i] = 3;
                }
            }
            }
        }
    }

    public void addLine(int line){
        for (int i = 0; i < objects.length; i++){
            
            if (Math.random() < .01){
                objects[i][line] = 3;
                objects[i+1][line] = 3;
                i++;

            }
            else if (Math.random() < .07)
                objects[i][line] = 2;
            else
                objects[i][line] = 0;
        }
        
    }

    public void moveUp(){   
      
        for (int i = 0; i < objects.length; i++){
            for (int j = 0; j < objects[0].length-1 ; j++){
                objects[i][j] = objects[i][j+1];
            }
            addLine(objects[0].length-1);
        }
        frogY--;
    }
    public boolean collide(){
       
        for (int i = 0; i < objects.length; i++){
            for (int j = 0; j < objects[0].length; j++){
                if (objects[frogX][frogY] == 2 || objects[frogX][frogY] == 3)
                    return true;
            }
        }
        return false;
    
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
