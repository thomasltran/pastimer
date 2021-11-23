package com.pastimer.desktop;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.*;

public class GameScreen implements Screen {
    final Minesweeper game;

   
    Texture zero;
    Texture one;
    Texture two;
    Texture three;
    Texture four;
    Texture five;
    Texture six;
    Texture seven;
    Texture eight;
    Texture flag;
    Texture starting;
    Texture bomb;
    Set<Integer> mineLocation;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera;
    Array<Rectangle> raindrops;
    long lastDropTime;
    int bombCount;
    int[][] board, pieces;
    int size = 30;
    

    public GameScreen(final Minesweeper game) {
        this.game = game;

        // load the images for the droplet and the bucket, 64x64 pixels each
       
        zero = new Texture(Gdx.files.internal("0.png"));
        one = new Texture(Gdx.files.internal("1.png"));
        two = new Texture(Gdx.files.internal("2.png"));
        three = new Texture(Gdx.files.internal("3.png"));
        four = new Texture(Gdx.files.internal("4.png"));
        five = new Texture(Gdx.files.internal("5.png"));
        six = new Texture(Gdx.files.internal("6.png"));
        seven = new Texture(Gdx.files.internal("7.png"));
        eight = new Texture(Gdx.files.internal("8.png"));
        flag = new Texture(Gdx.files.internal("flagged.png"));
        starting = new Texture(Gdx.files.internal("facingDown.png"));
        bomb = new Texture(Gdx.files.internal("bomb.png"));


        // load the drop sound effect and the rain background "music"
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 480);
        board =new int[16][16];
        pieces = new int[16][16];
        generateMineLocation();
        addBombs();
        for(int r=0;r<board.length;r++)	
        {
           for(int c=0;c<board[0].length;c++)
           {
           
              if(board[r][c] != -1){
                 board[r][c] = bombCount(r,c);
              }
           } 
        }
  

    }

   
    public void addBombs() {
    int temp = 0;
        board = new int[16][16];
     for(int r = 0; r < 16; r++) {
         for(int c = 0; c < 16; c++) {
            if(mineLocation.contains(temp)){
                board[r][c]=-1;
             }
             else{
                board[r][c]=0;
             }
             temp++;        
             pieces[r][c] = 0; //starts all things blank
          }
 
        
    }
}

    public void generateMineLocation() {
        mineLocation = new HashSet<Integer>();
        int mineCount = 0;
        while (mineCount < 40) {
            int random = (int) (Math.random() * (256));
            if (!mineLocation.contains(random)) {
                mineLocation.add(random);
                mineCount++;
            }
        }
    }
    public int bombCount(int r, int c){
        int bombCount=0;
        while(r !=0 && c!=0 && r!=board.length-1 && c!= board[0].length-1){
           if(board[r][c+1]==-1){ //right
              bombCount++;
           }
           if(board[r][c-1]==-1){   //left
              bombCount++;
           }
           if(board[r+1][c]==-1){  //down
              bombCount++;
           }
           if(board[r-1][c]==-1){ //up
              bombCount++;
           }
           if(board[r-1][c-1]==-1){  //top-left
              bombCount++;
           }
           if(board[r-1][c+1]==-1){  //top-right
              bombCount++;
           }
           if(board[r+1][c-1]==-1){   //bottom-left
              bombCount++;
           }
           if(board[r+1][c+1]==-1){   //bottom-right
              bombCount++;
           
           }
           break;
        }
        if(r==0 && c==0){
           if(board[r][c+1]==-1){ //right
              bombCount++;
           }
           if(board[r+1][c]==-1){  //down
              bombCount++;
           }
           if(board[r+1][c+1]==-1){   //bottom-right
              bombCount++;
           }
        
        }
        if(r==0 && c==board[0].length-1){
           if(board[r][c-1]==-1){   //left
              bombCount++;
           }
           if(board[r+1][c]==-1){  //down
              bombCount++;
           }
           if(board[r+1][c-1]==-1){   //bottom-left
              bombCount++;
           }
           
        }
        if(r==board.length-1 && c==0){
           if(board[r-1][c]==-1){ //up
              bombCount++;
           }
           if(board[r][c+1]==-1){ //right
              bombCount++;
           }
           if(board[r-1][c+1]==-1){  //top-right
              bombCount++;
           }
        }
        if(r==board.length-1 && c==board[0].length-1){
           if(board[r-1][c]==-1){ //up
              bombCount++;
           }
           if(board[r][c-1]==-1){   //left
              bombCount++;
           }
           if(board[r-1][c-1]==-1){  //top-left
              bombCount++;
           }
           
        }
        if(c==0 && r != 0 && r !=board.length-1){
           if(board[r][c+1]==-1){ //right
              bombCount++;
           }
           if(board[r-1][c+1]==-1){  //top-right
              bombCount++;
           }
           if(board[r+1][c+1]==-1){   //bottom-right
              bombCount++;
           }
           if(board[r-1][c]==-1){ //up
              bombCount++;
           }
           if(board[r+1][c]==-1){  //down
              bombCount++;
           }
        }
        if(r==0 && c != 0 && c !=board[0].length-1){
           if(board[r+1][c]==-1){  //down
              bombCount++;
           }
           if(board[r+1][c+1]==-1){   //bottom-right
              bombCount++;
           }
           if(board[r+1][c-1]==-1){   //bottom-left
              bombCount++;
           }
           if(board[r][c+1]==-1){ //right
              bombCount++;
           }
           if(board[r][c-1]==-1){   //left
              bombCount++;
           }
        }
        if(c==board[0].length-1 && r != 0 && r !=board.length-1){
           
           if(board[r-1][c]==-1){ //up
              bombCount++;
           }
           if(board[r+1][c]==-1){  //down
              bombCount++;
           }
           if(board[r][c-1]==-1){   //left
              bombCount++;
           }
           if(board[r-1][c-1]==-1){  //top-left
              bombCount++;
           }
           if(board[r+1][c-1]==-1){   //bottom-left
              bombCount++;
           }
        }
        if(r==board.length-1 && c != 0 && c !=board[0].length-1){
           if(board[r][c+1]==-1){ //right
              bombCount++;
           }
           if(board[r][c-1]==-1){   //left
              bombCount++;
           }
           if(board[r-1][c]==-1){ //up
              bombCount++;
           }
           if(board[r-1][c-1]==-1){  //top-left
              bombCount++;
           }
           if(board[r-1][c+1]==-1){  //top-right
              bombCount++;
           }
        }
     
      
        
        return bombCount;
     
     }
     public void floodFill(int r, int c){
      int[][] vis = new int[16][16]; 
      if (r < 0 || r >= pieces.length || c < 0 || c >= pieces[0].length)
         return;
      if(board[r][c]==-1 || vis[r][c]==1)
         return;
      vis[r][c]=1;
      pieces[r][c]=-1;
      floodFill(r,c+1);
         floodFill(r+1, c);
         floodFill(r-1,c);
         floodFill(r,c-1);
     
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
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(board[i][j]==-1){
                    game.batch.draw(bomb, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==0){
                    game.batch.draw(zero, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==1){
                    game.batch.draw(one, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==2){
                    game.batch.draw(two, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==3){
                    game.batch.draw(three, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==4){
                    game.batch.draw(four, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==5){
                    game.batch.draw(five, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==6){
                    game.batch.draw(six, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==7){
                    game.batch.draw(seven, j*(size), i*(size), size, size);
                }
                else if(board[i][j]==8){
                    game.batch.draw(eight, j*(size), i*(size), size, size);
                }
            }
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
         if(pieces[i][j]==0){
             game.batch.draw(starting, j*(size), i*(size), size, size);
            }
            else if(pieces[i][j]==1){
                game.batch.draw(flag, j*(size), i*(size), size, size);
                }
            }}
       
        
        game.batch.end();

        // process user input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            int x = (int)(touchPos.x / size)-1;
            int y = (int)(touchPos.y / size)-1;
            if(pieces[x][y]==0){
                if(board[x][y]==-1){
                    game.setScreen(new GameOverScreen(game));
                }
                else{
                    pieces[x][y]=1;
                    if(board[x][y]==0){
                        floodFill(x,y);
                    }
                }
            }
            else if(pieces[x][y]==1){
                pieces[x][y]=0;
            }
        }
        

        // make sure the bucket stays within the screen bounds
       
        // check if we need to create a new raindrop
        

        // move the raindrops, remove any that are beneath the bottom edge of
        // the screen or that hit the bucket. In the later case we increase the
        // value our drops counter and add a sound effect.
       
    }
    

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
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
        
        dropSound.dispose();
        rainMusic.dispose();
         zero.dispose();
            one.dispose();
            two.dispose();
            three.dispose();
            four.dispose();
            five.dispose();
            six.dispose();
            seven.dispose();
            eight.dispose();
            bomb.dispose();
            flag.dispose();
            starting.dispose();
            

}
}