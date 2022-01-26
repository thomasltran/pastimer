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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Anagrams implements Screen {
    
    private Stage stage;
    private Game game;
    private Button reroll;
    private TextField input;
    private HashSet<String> words;
    private HashSet<String> letters;
    private HashSet<String> answers;
    private Time timer;

    public Anagrams(Game game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        words= new HashSet<String>();
        answers = new HashSet<String>();
        timer = new Time();
        
        
    }
    public void populateHashSet() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner input = new Scanner(new FileReader("words.txt"));
        while(input.hasNextLine()){
            String line = input.nextLine().trim();
            line = line.toLowerCase();
            words.add(line);
        }
    
    }
    @Override
    public void Show(){
        Gdx.input.setInputProcessor();
    
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
