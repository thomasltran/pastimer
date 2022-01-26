package com.pastimer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Input;

import java.util.*;

public class MineSweeperScreen implements Screen {
   private Game game;

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
   Texture flagIcon;
   Set<Integer> mineLocation;
   OrthographicCamera camera;
   Array<Rectangle> raindrops;
   long lastDropTime;
   int bombCount;
   int[][] board, pieces;
   int size = 60;
   int flags;
   SpriteBatch batch;
   TextButton back;
   Stage stage;
   boolean active;
   private static BitmapFont font;

   public MineSweeperScreen(Game game) {
      this.game = game;

      // load the images for the droplet and the bucket, 64x64 pixels each

      zero = new Texture(Gdx.files.internal("minesweeper/0.png"));
      one = new Texture(Gdx.files.internal("minesweeper/1.png"));
      two = new Texture(Gdx.files.internal("minesweeper/2.png"));
      three = new Texture(Gdx.files.internal("minesweeper/3.png"));
      four = new Texture(Gdx.files.internal("minesweeper/4.png"));
      five = new Texture(Gdx.files.internal("minesweeper/5.png"));
      six = new Texture(Gdx.files.internal("minesweeper/6.png"));
      seven = new Texture(Gdx.files.internal("minesweeper/7.png"));
      eight = new Texture(Gdx.files.internal("minesweeper/8.png"));
      flag = new Texture(Gdx.files.internal("minesweeper/flagged.png"));
      flagIcon = new Texture(Gdx.files.internal("minesweeper/flagIcon.png"));
      starting = new Texture(Gdx.files.internal("minesweeper/facingDown.png"));
      bomb = new Texture(Gdx.files.internal("minesweeper/bomb.png"));
      flags = 40;

      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 1920, 1080);
      batch = new SpriteBatch();
      font = new BitmapFont();
      board = new int[16][16];
      pieces = new int[16][16];
      generateMineLocation();
      addBombs();
      active = true;

      for (int r = 0; r < board.length; r++) {
         for (int c = 0; c < board[0].length; c++) {

            if (board[r][c] != -1) {
               board[r][c] = bombCount(r, c);
            }
         }
      }

      stage = new Stage(new ScreenViewport());
      back = new TextButton("Return", Pastimer.skin);
      back.setSize(500, 50);
      back.setPosition(Gdx.graphics.getWidth()/2-back.getWidth()/2, Gdx.graphics.getHeight()-back.getHeight());
      stage.addActor(back);

   }

   public void addBombs() {
      int temp = 0;
      board = new int[16][16];
      for (int r = 0; r < 16; r++) {
         for (int c = 0; c < 16; c++) {
            if (mineLocation.contains(temp)) {
               board[r][c] = -1;
            } else {
               board[r][c] = 0;
            }
            temp++;
            pieces[r][c] = 0; // starts all things blank
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

   public int bombCount(int r, int c) {
      int bombCount = 0;
      while (r != 0 && c != 0 && r != board.length - 1 && c != board[0].length - 1) {
         if (board[r][c + 1] == -1) { // right
            bombCount++;
         }
         if (board[r][c - 1] == -1) { // left
            bombCount++;
         }
         if (board[r + 1][c] == -1) { // down
            bombCount++;
         }
         if (board[r - 1][c] == -1) { // up
            bombCount++;
         }
         if (board[r - 1][c - 1] == -1) { // top-left
            bombCount++;
         }
         if (board[r - 1][c + 1] == -1) { // top-right
            bombCount++;
         }
         if (board[r + 1][c - 1] == -1) { // bottom-left
            bombCount++;
         }
         if (board[r + 1][c + 1] == -1) { // bottom-right
            bombCount++;

         }
         break;
      }
      if (r == 0 && c == 0) {
         if (board[r][c + 1] == -1) { // right
            bombCount++;
         }
         if (board[r + 1][c] == -1) { // down
            bombCount++;
         }
         if (board[r + 1][c + 1] == -1) { // bottom-right
            bombCount++;
         }

      }
      if (r == 0 && c == board[0].length - 1) {
         if (board[r][c - 1] == -1) { // left
            bombCount++;
         }
         if (board[r + 1][c] == -1) { // down
            bombCount++;
         }
         if (board[r + 1][c - 1] == -1) { // bottom-left
            bombCount++;
         }

      }
      if (r == board.length - 1 && c == 0) {
         if (board[r - 1][c] == -1) { // up
            bombCount++;
         }
         if (board[r][c + 1] == -1) { // right
            bombCount++;
         }
         if (board[r - 1][c + 1] == -1) { // top-right
            bombCount++;
         }
      }
      if (r == board.length - 1 && c == board[0].length - 1) {
         if (board[r - 1][c] == -1) { // up
            bombCount++;
         }
         if (board[r][c - 1] == -1) { // left
            bombCount++;
         }
         if (board[r - 1][c - 1] == -1) { // top-left
            bombCount++;
         }

      }
      if (c == 0 && r != 0 && r != board.length - 1) {
         if (board[r][c + 1] == -1) { // right
            bombCount++;
         }
         if (board[r - 1][c + 1] == -1) { // top-right
            bombCount++;
         }
         if (board[r + 1][c + 1] == -1) { // bottom-right
            bombCount++;
         }
         if (board[r - 1][c] == -1) { // up
            bombCount++;
         }
         if (board[r + 1][c] == -1) { // down
            bombCount++;
         }
      }
      if (r == 0 && c != 0 && c != board[0].length - 1) {
         if (board[r + 1][c] == -1) { // down
            bombCount++;
         }
         if (board[r + 1][c + 1] == -1) { // bottom-right
            bombCount++;
         }
         if (board[r + 1][c - 1] == -1) { // bottom-left
            bombCount++;
         }
         if (board[r][c + 1] == -1) { // right
            bombCount++;
         }
         if (board[r][c - 1] == -1) { // left
            bombCount++;
         }
      }
      if (c == board[0].length - 1 && r != 0 && r != board.length - 1) {

         if (board[r - 1][c] == -1) { // up
            bombCount++;
         }
         if (board[r + 1][c] == -1) { // down
            bombCount++;
         }
         if (board[r][c - 1] == -1) { // left
            bombCount++;
         }
         if (board[r - 1][c - 1] == -1) { // top-left
            bombCount++;
         }
         if (board[r + 1][c - 1] == -1) { // bottom-left
            bombCount++;
         }
      }
      if (r == board.length - 1 && c != 0 && c != board[0].length - 1) {
         if (board[r][c + 1] == -1) { // right
            bombCount++;
         }
         if (board[r][c - 1] == -1) { // left
            bombCount++;
         }
         if (board[r - 1][c] == -1) { // up
            bombCount++;
         }
         if (board[r - 1][c - 1] == -1) { // top-left
            bombCount++;
         }
         if (board[r - 1][c + 1] == -1) { // top-right
            bombCount++;
         }
      }

      return bombCount;

   }

   public void flood(int r, int c) {
      if (board[r][c] == 0 && r >= 0 && r < board.length && c >= 0 && c < board[0].length && pieces[r][c] != 1) {
         pieces[r][c] = 4;
         if (c != 15)
            flood(r, c + 1);
         if (r != 15)
            flood(r + 1, c);
         if (r != 0)
            flood(r - 1, c);
         if (c != 0)
            flood(r, c - 1);
      } else {
         return;
      }
      return;

   }

   public boolean isBomb(int x, int y) {
      if (board[x][y] == -1) {
         return true;
      }
      return false;

   }

   public boolean checkWin() {
      int temp = 255;
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[0].length; j++) {
            if (pieces[i][j] != 1 || pieces[i][j] != 0) {
               temp--;
            }
         }
      }
      if (temp == 40) {
         return true;
      }
      return false;
   }

   @Override
   public void render(float delta) {
      // clear the screen with a dark blue color. The
      // arguments to clear are the red, green
      // blue and alpha component in the range [0,1]
      // of the color to be used to clear the screen.
      ScreenUtils.clear(185 / 255f, 185 / 255f, 185 / 255f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      // tell the camera to update its matrices.
      camera.update();
      // tell the SpriteBatch to render in the
      // coordinate system specified by the camera.
      batch.setProjectionMatrix(camera.combined);

      // begin a new batch and draw the bucket and
      // all drops
      batch.begin();

      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[0].length; j++) {
            if (board[i][j] == -1) {
               batch.draw(bomb, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 0) {
               batch.draw(zero, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 1) {
               batch.draw(one, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 2) {
               batch.draw(two, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 3) {
               batch.draw(three, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 4) {
               batch.draw(four, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 5) {
               batch.draw(five, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 6) {
               batch.draw(six, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 7) {
               batch.draw(seven, i *(size)+480, j * (size), size, size);
            } else if (board[i][j] == 8) {
               batch.draw(eight, i *(size)+480, j * (size), size, size);
            }
         }
      }
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[0].length; j++) {
            if (pieces[i][j] == 0) {
               batch.draw(starting, j * (size)+480, i *(size), size, size);
            } else if (pieces[i][j] == 1) {
               batch.draw(flag, j * (size)+480, i *(size), size, size);
            }
         }
      }
      String flagCounter = String.valueOf(flags);
      batch.draw(flagIcon, 930-flagIcon.getWidth()/2, 960, 100, 100);
      MineSweeperScreen.font.draw(batch, flagCounter, 1000, 1030);


      // process user input
      if (active && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         int y = ((int) (touchPos.x / size))-8;
         int x = (int) ((touchPos.y) / size);
         String strX = String.valueOf(x);
         String strY = String.valueOf(y);
         if (y <= 15 && y >= 0 && x >= 0 && x <= 15) {
            if (isBomb(y, x) && pieces[x][y] != 1) {
               for (int i = 0; i < pieces.length; i++) {
                  for (int g = 0; g < pieces[0].length; g++) {
                     pieces[i][g] = 3;
                  }
               }
               MineSweeperScreen.font.draw(batch, "THE PEACHES WIN AGAIN!!!! :)", 190, 515);
               active = false;
            } else if (pieces[x][y] != 1) {
               pieces[x][y] = 3;

            }
            MineSweeperScreen.font.draw(batch, "I clicked " + strX + " and " + strY, 240, 240);
         } else
         MineSweeperScreen.font.draw(batch, "I clicked " + strX + " and " + strY, 240, 240);
      }
      if (active && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         int x = (int) (touchPos.x / size);
         int y = (int) ((touchPos.y) / size);
         String strX = String.valueOf(x);
         String strY = String.valueOf(y);
         if (y <= 15 && y >= 0 && x >= 0 && x <= 15) {
            if (pieces[y][x] == 0) {
               pieces[y][x] = 1;
               flags--;
            } else if (pieces[y][x] == 1) {
               pieces[y][x] = 0;
               flags++;

            }

            MineSweeperScreen.font.draw(batch, "I clicked " + strX + " and " + strY, 240, 240);
         }
      }
      if (checkWin()) {

         MineSweeperScreen.font.draw(batch, "You Win", 240, 240);
         active = false;
      }

      batch.end();

      if (!active) {
         Gdx.input.setInputProcessor(stage);
         stage.act(delta);
         stage.draw();
         if (back.isPressed()) {
            game.setScreen(new TimerScreen(game));
         }
      }
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
      stage.dispose();
      batch.dispose();
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