package com.pastimer;


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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Anagrams implements Screen {
    
    private Stage stage;
    private Game game;
    private Button reroll;
    private TextField input;
    private TextButton submit;
    private HashSet<String> possibleWords;
    private ArrayList<String> letters;
    private HashSet<String> allowedWords;
    private HashSet<String> answers;
    private Time timer;
    private Label[][] board;
    private Table table;
    private int points;
   
    public Anagrams(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        possibleWords= new HashSet<String>();
        allowedWords = new HashSet<String>();
        timer = new Time();
        populateLetters(6);  
        points =0;
      
    }
    public static String getRandomLetter(){
        int rand = (int)(Math.random() * 25);
        String a[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
       return a[rand];
       }
    public void populateLetters(int numLetters){
        for(int i = 0; i< numLetters; i++){
            letters.add(getRandomLetter());
       
       }
    }
    public boolean validAnswer(String word){
        if(possibleWords.contains(word)&& !answers.contains(word)){
            return true;
        }
        return false;


    }
    public void populatePossibleWords() {
        try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner input = new Scanner(new FileReader("possibleWords.txt"));
        while(input.hasNextLine()){
            String line = input.nextLine().trim();
            line = line.toLowerCase();
            possibleWords.add(line);
        }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
       
    }
    
    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(!event.isHandled()){
                    stage.unfocusAll();
                }
            }
        });
        
        submit = new TextButton("submit", Pastimer.skin);
        submit.setPosition(540, 60);
        stage.addActor(submit);

        input = new TextField("" , Pastimer.skin);
        input.setPosition(500,500);
        stage.addActor(input);

       
        table = new Table();
        
	table.setFillParent(true);
	table.setDebug(true); // This is optional, but enables debug lines for tables.
    
   for(int i= 0; i<board.length; i++){
    for(int j = 0; j < board[0].length; j++ ){
        table.add(board[i][j]);
        if(j==4){
            table.row();
        }
       
    }

    }
    table.setPosition(250, 300);
    table.setColor(Color.BLACK);
    table.setSize(500, 600);
   
    stage.addActor(table);
    
    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(234 / 255.0f, 172 / 255.0f, 172 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if(submit.isPressed()&& input.getText().length()>=3){
            if(validAnswer(input.getText())){
                
                switch( input.getText().length()) {
                    case 3:
                        points +=100;
                      break;
                    case 4:
                        points +=250;
                      break;
                      case 5:
                      points +=500;
                      case 6:
                        points +=1000;
                      break;
                    default:
                      points +=5000;
                  }
            }
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


}

