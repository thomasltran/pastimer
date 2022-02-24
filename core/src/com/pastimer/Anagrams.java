package com.pastimer;

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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Anagrams implements Screen {
    
    private Stage stage;
    private Game game;
    private Button reroll;
    private TextField keybaord;
    private TextButton submit;
    private HashSet<String> words;
    private HashSet<String> letters;
    private HashSet<String> answers;
    private Time timer;
    private int[][] board;
    private Table table;
    

    public Anagrams(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        words= new HashSet<String>();
        answers = new HashSet<String>();
        timer = new Time();
        keybaord = new TextField("testing", new TextField.TextFieldStyle());
        
        
    }
    public void populateHashSet() throws FileNotFoundException, IOException{
        try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner input = new Scanner(new FileReader("words.txt"));
        while(input.hasNextLine()){
            String line = input.nextLine().trim();
            line = line.toLowerCase();
            words.add(line);
        }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(IOException e){
            System.out.println("IOException");
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
        try{
            populateHashSet();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        submit = new TextButton("submit", Pastimer.skin);
        submit.setPosition(250, 10);
        stage.addActor(submit);

        buildTable();
      
    
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(234 / 255.0f, 172 / 255.0f, 172 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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
private void buildTable(){
board = new int[5][6];
for(int i = 0; i < 5; i++){
    for(int j = 0; j < 6; j++){
        board[i][j] = 0;
        table.add(TextField("hello world")).width(50).height(50);
    }
}


}

}

