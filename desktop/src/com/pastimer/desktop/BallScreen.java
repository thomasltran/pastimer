package com.pastimer.desktop;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
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

public class BallScreen implements Screen {
    final Drop game;

    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Rectangle bucket;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int dropsGathered;

    Rectangle platform;
    Rectangle ball;
    int[][] boxes;
    int xChange;
    double angle = 270;
    int yVel = 1;
    int xVel = 1;
    int length = Display.getWidth();
    int height = Display.getHeight();

    public BallScreen(final Drop game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
        dropImage = new Texture(Gdx.files.internal("peachLogo.png"));
        bucketImage = new Texture(Gdx.files.internal("peachLogo.png"));
        

        // load the drop sound effect and the rain background "music"
        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
       // rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
/*
        // create a Rectangle to logically represent the bucket
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
        // the bottom screen edge
        bucket.width = 64;
        bucket.height = 64;

        // create the raindrops array and spawn the first raindrop
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
*/
        boxes = new int [5][10];
        ball = new Rectangle(400, 150, 30, 30);
        platform = new Rectangle(Display.getWidth()/2-100, 50, 200, 20);
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        game.batch.begin();

        

        
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 10; j++){
                if (length/10*j + 15 > ball.x && ball.x > length/10*j - 15 && height/2+height/10*(i)+15 > ball.y && ball.y > height/2+height/10*(i) -15)
                    boxes[i][j]++;
                if (boxes[i][j] == 0){
                    game.batch.draw(bucketImage, length/10*j, height/2+height/10*(i), length/10, height/10);
                }
            
            }
        
        }
        game.font.draw(game.batch, "HI" , 0, 480);
        game.batch.draw(bucketImage, platform.x, platform.y, platform.width,platform.height);
        game.batch.draw(bucketImage, ball.x, ball.y, ball.width, ball.height);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            platform.x -= 200 * Gdx.graphics.getDeltaTime();
            
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            platform.x += 200 * Gdx.graphics.getDeltaTime();

        if (platform.x < 0)
            platform.x = 0;

        if (platform.x > 800 - 100)
            platform.x = 800 - 100;
        /*
        if (ball.x < 0)
            ball.x = 0;
        if (ball.x > 800-ball.height)
            ball.x = 800-ball.height;
        if (ball.y < 0 + ball.height)
            ball.y = 0 + ball.height;
        if (ball.y > 480-ball.height)
            ball.y = 480-ball.height;
            */
        collide();
        ball.x += 5 * Math.cos(Math.toRadians(angle));
        ball.y += 5 * Math.sin(Math.toRadians(angle));
        
        /*
        game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // make sure the bucket stays within the screen bounds
        if (bucket.x < 0)
            bucket.x = 0;
        if (bucket.x > 800 - 64)
            bucket.x = 800 - 64;

        // check if we need to create a new raindrop
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
            spawnRaindrop();

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0)
                iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGathered++;
                dropSound.play();
                iter.remove();
            }
        }*/
    }

    public boolean collide(){



        for (int i = 0; i < boxes.length; i++){
           for (int j = 0; j < boxes[0].length; j++){
              int size = length/20;
              if (boxes[i][j] == 0 && distance(j*length/10+size, height/2 + (i)*height/10+size, (int)(ball.x+ball.height/2), (int) (ball.y+ball.height/2)) < size+5){
                 boxes[i][j]++;
                 calcAng(j*length/10, height/2 + (i)*height/10+size, size);
                 
                 //angle = 270;
                 return true;
              }
                 
           }
        }
           for (int i = 0; i < platform.x; i++){
              if (distance((int) (platform.x+i), (int) platform.y, (int) ball.x, (int) ball.y) < 5){
                 calcAng();
                 //angle = 90;
                 
                 return true;
              }
           }
        if (ball.getX() < 0 || ball.getX() > length ){
           angle = Math.abs(180 - angle);
          
        }
        if (ball.getY() < 0 || ball.getY() > height){
           angle = Math.abs(360 - angle);
           
        }
        
        return false;
     }

     public void calcAng(){
       
        double midx = (double)(platform.x+platform.width/2 - ball.x)/(double)(platform.width/2);
        double bounce = midx * 7*Math.PI/12;
        angle =  bounce*180/Math.PI;
        if (Math.cos(angle) < 0)
            angle = 180*(Math.PI/4*(ball.x - (platform.x-platform.width))/platform.width-Math.PI/8)/Math.PI;
        else
            angle =  - 180*(Math.PI/4*(ball.x - (platform.x-platform.width))/platform.width-Math.PI/8)/Math.PI;
     }

     public void calcAng(int x, int y, int size){
        if (ball.y  == y)
           angle = 360 - angle;
        else
           angle = 180-angle;
     }
  
  

    public static double distance(int x1, int y1, int x2, int y2){
      return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
     }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        //rainMusic.play();
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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

}