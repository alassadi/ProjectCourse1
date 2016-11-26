package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture backGround;
	Texture wellcome;
	Texture gameover;
	UserCar userCar;
	Gamestate gamestate = Gamestate.WelcomePage;
	float userCarPositionX = 450f;
	float userCarPositionY = 620f;
	AiCar aiCar;
	float aiCarPositionX = 450f;
	float aiCarPositionY = 660f;



	private enum Gamestate {
		WelcomePage,
		GamePage,
		GameOver
	}


	
	@Override
	public void create () {
		wellcome = new Texture("WellComePage.jpg") ;
		batch = new SpriteBatch();
		backGround= new Texture("BackGround.jpg");
		gameover = new Texture("game-over.jpg");
		createUserCar();
		createAiCar();

	}

	public void createUserCar()
	{
		userCar = new UserCar("userCar1.png",userCarPositionX,userCarPositionY,4 );
	}

	public void createAiCar()
	{
		aiCar = new AiCar("AiCar1.png",aiCarPositionX,aiCarPositionY);
	}

	/*
	public void movements ()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			userCarPositionX -= Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			userCarPositionX += Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			userCarPositionY += Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			userCarPositionY -= Gdx.graphics.getDeltaTime() * carSpeed;

	}
	*/
	public void checkInput(){
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			userCar.accelerate();

		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			userCar.breaks();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			userCar.turnLeft();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			userCar.turnRight();
		}
		userCar.deceleration();
	}

	@Override
	public void render () {


		switch (gamestate)
		{
			case WelcomePage:
				renderWelcomePage();
				break;

			case GamePage:
				renderGamePage();

				break;

			case GameOver:
				renderGameOver();
				break;

		}
	}

	public void renderWelcomePage()
	{
		batch.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(wellcome, 0, 0);
		// if enter key is pressed in the welcome page it will go to game page
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			gamestate = Gamestate.GamePage;
		}
		// if escape key was pressed in the welcome page it will got to the game over page
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			gamestate = Gamestate.GameOver;
		}
		batch.end();
	}


	public void renderGamePage()
	{
		checkInput();
		batch.begin();
		batch.draw(backGround, 0, 0);
		userCar.getSprite().draw(batch);
		userCar.updatePosition();
		aiCar.getSprite().draw(batch);
		aiCar.updatePositionFromSpeed();
		aiCar.Route();

		// exit game
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
		{
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			gamestate = Gamestate.GameOver;
		}
		batch.end();
	}

	public void renderGameOver()
	{
		batch.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(gameover, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		createUserCar();
		createAiCar();
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			gamestate = Gamestate.WelcomePage;
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
		wellcome.dispose();
		gameover.dispose();
	}

}
