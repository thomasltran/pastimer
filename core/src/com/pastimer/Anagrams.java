package com.pastimer;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.io.*;
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
    private Label showPoints;
   
    public Anagrams(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        possibleWords= new HashSet<String>();
        allowedWords = new HashSet<String>();
        letters = new ArrayList<String>();
        answers = new HashSet<String>();
        timer = new Time();
        populateLetters(10);  
        populatePossibleWords();
        points =0;
        
      
    }
    public String getRandomLetter(){
        int rand = (int)(Math.random() * 25);
        String a[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
      
       return a[rand];
       }
       public String getRandomVowel(){
        int rand = (int)(Math.random() *4);
        String a[] = {"a","e","i","o","u"};
        return a[rand];
       
       }
    public void populateLetters(int numLetters){
        for(int i=0; i<3; i++){
            letters.add(getRandomVowel());

        }
        for(int i = 0; i< numLetters-3; i++){
            letters.add(getRandomLetter());
       
       }
    }
    public boolean validAnswer(String word){
        if(!possibleWords.contains(word) && answers.contains(word)){
            return false;
        }
        //for(int i = 0; i<word.length(); i++){
          //  if(!letters.contains(word.substring(i, i++))){
            //    return false;
           // }

        //}
        return true;


    }
    public void populatePossibleWords() {
        try{
        Scanner input = new Scanner(new FileReader("C:\\Users\\1524966\\pastimer\\core\\src\\com\\pastimer\\words.txt"));
        while(input.hasNextLine()){
            String line = input.nextLine().trim();
            line = line.toLowerCase();
            possibleWords.add(line);
            System.out.println("new word add");
        }
        }
        catch(FileNotFoundException e){
            System.out.println("bruh this ducks");
        }
       
    }
    public void scrambleLetters(){


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
       
        showPoints = new Label(String.valueOf(points), Pastimer.skin);
        showPoints.setPosition(560,600);
        showPoints.setScale(40);
        stage.addActor(showPoints);

      

        table = new Table();
        table.setPosition(700,600);
        for(int i =0; i<letters.size(); i++){
        table.add(new Label(letters.get(i), Pastimer.skin));
        }
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
             // if(true) { 
                switch( input.getText().length()) {
                    case 3:
                        points +=100;
                      break;
                    case 4:
                        points +=250;
                      break;
                      case 5:
                      points +=500;
                      break;
                      case 6:
                        points +=1000;
                      break;
                    default:
                      points +=5000;
                  }
                  answers.add(input.getText());
                  
            }
        }
        showPoints.setText(String.valueOf(points));
      
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

