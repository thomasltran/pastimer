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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Wordle implements Screen {
    
    private Stage stage;
    private Game game;
    private Button reroll;
    private TextField keybaord;
    private TextButton submit;
    private HashSet<String> possibleWords;
    private HashSet<String> letters;
    private HashSet<String> allowedWords;
    private Time timer;
    private Label[][] board;
    private Table table;
    

    public Wordle(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        possibleWords= new HashSet<String>();
        allowedWords = new HashSet<String>();
        timer = new Time();
        try{ 
            getWord();
            populatePossibleWords();
            
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        
        
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
        
        submit = new TextButton("submit", Pastimer.skin);
        submit.setPosition(540, 60);
        stage.addActor(submit);

        buildTable();
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
    public String getWord() throws FileNotFoundException, IOException{ 
       String word = "hello";
        int rand = (int) (Math.random() * (2315))+1;
        FileInputStream fs= new FileInputStream("someFile.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < rand; i++)
            br.readLine();
        word= br.readLine();
        return word;
    
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
board = new Label[6][5];
for(int i = 0; i < 6; i++){
    for(int j = 0; j < 5; j++){
       board[i][j] = new Label(Integer.toString(i) +"," +Integer.toString(j), Pastimer.skin );
       board[i][j].setColor(Color.BLACK);
       board[i][j].setFontScale(3);
    }
}


}

}

