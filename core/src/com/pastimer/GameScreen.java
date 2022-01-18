package com.pastimer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Input;

import java.util.*;

public class GameScreen implements Screen {
   private Game game;
   private Stage stage;

   Image zero;
   Image one;
   Image two;
   Image three;
   Image four;
   Image five;
   Image six;
   Image seven;
   Image eight;
   Image flag;
   Image starting;
   Image bomb;
   Image flagIcon;
   Label tempLabel;
   Set<Integer> mineLocation;
   OrthographicCamera camera;
   long lastDropTime;
   int bombCount;
   int[][] board, pieces;
   float size = 0.1f;
   int flags;

   public GameScreen(Game game) {
      this.game = game;

      zero = new Image(new Texture(Gdx.files.internal("0.png")));
      one = new Image(new Texture(Gdx.files.internal("1.png")));
      two = new Image(new Texture(Gdx.files.internal("2.png")));
      three = new Image(new Texture(Gdx.files.internal("3.png")));
      four = new Image(new Texture(Gdx.files.internal("4.png")));
      five = new Image(new Texture(Gdx.files.internal("5.png")));
      six = new Image(new Texture(Gdx.files.internal("6.png")));
      seven = new Image(new Texture(Gdx.files.internal("7.png")));
      eight = new Image(new Texture(Gdx.files.internal("8.png")));
      flag = new Image(new Texture(Gdx.files.internal("flagged.png")));
      flagIcon = new Image(new Texture(Gdx.files.internal("flagIcon.png")));
      starting = new Image(new Texture(Gdx.files.internal("facingDown.png")));
      bomb = new Image(new Texture(Gdx.files.internal("bomb.png")));

      zero.setScale(size, size);
      one.setScale(size, size);
      two.setScale(size, size);
      three.setScale(size, size);
      four.setScale(size, size);
      five.setScale(size, size);
      six.setScale(size, size);
      seven.setScale(size, size);
      eight.setScale(size, size);
      flag.setScale(size, size);
      flagIcon.setScale(100, 100);
      starting.setScale(size, size);
      bomb.setScale(size, size);

      flags = 40;

      board = new int[16][16];
      pieces = new int[16][16];

      generateMineLocation();
      addBombs();
      for (int r = 0; r < board.length; r++) {
         for (int c = 0; c < board[0].length; c++) {

            if (board[r][c] != -1) {
               board[r][c] = bombCount(r, c);
            }
         }
      }

      stage = new Stage(new ScreenViewport());
   }
      @Override
      public void show(){
         
         for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
               if (board[i][j] == -1) {
                  bomb.setPosition(i* 16, j * 16);
                  stage.addActor(bomb);
               } else if (board[i][j] == 0) {
                  zero.setPosition(i * 16, j * 16);
                  stage.addActor(zero);
               } else if (board[i][j] == 1) {
                  one.setPosition(i * 16, j * 16);
                  stage.addActor(one);
               } else if (board[i][j] == 2) {
                  two.setPosition(i * 16, j * 16);
                  stage.addActor(two);
               } else if (board[i][j] == 3) {
                  three.setPosition(i * 16, j * 16);
                  stage.addActor(three);
               } else if (board[i][j] == 4) {
                  four.setPosition(i * 16, j * 16);
                  stage.addActor(four);
               } else if (board[i][j] == 5) {
                  five.setPosition(i * 16, j * 16);
                  stage.addActor(five);
               } else if (board[i][j] == 6) {
                  six.setPosition(i * 16, j * 16);
                  stage.addActor(six);
               } else if (board[i][j] == 7) {
                  seven.setPosition(i * 16, j * 16);
                  stage.addActor(seven);
               } else if (board[i][j] == 8) {
                  eight.setPosition(i * 16, j * 16);
                  stage.addActor(eight);
               }
            }
         }
         for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
               if (pieces[i][j] == 0) {
                  starting.setPosition(j * 16, i * 16);
                  stage.addActor(starting);
               } else if (pieces[i][j] == 1) {
                  flag.setPosition(j * 16, i * 16);
                  stage.addActor(flag);
               }
            }
         }
         String flagCounter = String.valueOf(flags);
         flagIcon.setPosition(170, 450);
         stage.addActor(flagIcon);
         tempLabel = new Label(flagCounter, Pastimer.skin, "default");
         tempLabel.setPosition(230, 515);
         stage.addActor(tempLabel);
      Gdx.input.setInputProcessor(stage);
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

      Gdx.gl.glClearColor(185 / 255f, 185 / 255f, 185 / 255f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      stage.act();
      stage.draw();

      // process user input
    /* if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         int y = (int) (touchPos.x / size);
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
               tempLabel = new Label("THE PEACHES WIN AGAIN!!!! :)", Pastimer.skin, "default");
               tempLabel.setPosition(190, 515);
               stage.addActor(tempLabel);
            } else if (pieces[x][y] != 1) {
               pieces[x][y] = 3;

            }
            tempLabel = new Label("I clicked " + strX + " and " + strY, Pastimer.skin, "default");
            tempLabel.setPosition(240, 240);
            stage.addActor(tempLabel);
         } else
            tempLabel = new Label("I clicked " + strX + " and " + strY, Pastimer.skin, "default");
            tempLabel.setPosition(240, 240);
            stage.addActor(tempLabel);
      }
      if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
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

            tempLabel = new Label("I clicked " + strX + " and " + strY, Pastimer.skin, "default");
            tempLabel.setPosition(240, 240);
            stage.addActor(tempLabel);
         }
      }
      if (checkWin()) {
         tempLabel = new Label("You Win", Pastimer.skin, "default");
            tempLabel.setPosition(240, 240);
            stage.addActor(tempLabel);
      }*/
   }

   @Override
   public void resize(int width, int height) {
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
   }
}