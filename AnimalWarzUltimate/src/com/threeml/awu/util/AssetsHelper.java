package com.threeml.awu.util;

import com.threeml.awu.Game;
import com.threeml.awu.engine.AssetStore;

/**
 * A class that just loads assets, can be level dependent 
 * but assets are loaded and never removed so nice to have 
 * a separate class
 * 
 * @see https://trello.com/c/vdDi8ehn
 * 
 * @author Dean
 *
 */
public class AssetsHelper {

	public static void loadAllAssets(Game mGame){
		// Load in the assets used by this layer

		//Game Assets
		AssetStore assetManager = mGame.getAssetManager();
		assetManager.loadAndAddBitmap("Player", "img/player/worm_walk_left.png");
		assetManager.loadAndAddBitmap("PlayerBackFlip", "img/player/wbackflp.png");
		assetManager.loadAndAddBitmap("PlayerWalk", "img/player/wwalk.png");
		assetManager.loadAndAddBitmap("PlayerWin", "img/player/wwinner.png");
		assetManager.loadAndAddBitmap("PlayerUp", "img/player/wflyup.png");
		assetManager.loadAndAddBitmap("PlayerFall", "img/player/wfall.png");
		
		assetManager.loadAndAddBitmap("Terrain", "img/terrain/castles.png");
		assetManager.loadAndAddBitmap("Background", "img/background/lostKingdom.png");
		assetManager.loadAndAddBitmap("Health", "img/gameObject/healthpack.png");
		assetManager.loadAndAddBitmap("Gun", "img/gun.png");
		assetManager.loadAndAddBitmap("Font", "img/fonts/bitmapfont-VCR-OSD-Mono.png");

		//DashboardControls
		assetManager.loadAndAddBitmap("MoveLeft","img/dashControls/MoveLeft.png");
		assetManager.loadAndAddBitmap("MoveRight","img/dashControls/MoveRight.png");
		assetManager.loadAndAddBitmap("JumpLeft","img/dashControls/JumpLeft.png");
		assetManager.loadAndAddBitmap("JumpRight","img/dashControls/JumpRight.png");
		assetManager.loadAndAddBitmap("WeaponsCrate","img/dashControls/WeaponsCrate.png");
		assetManager.loadAndAddBitmap("Fireeee","img/dashControls/Fireeee.png");
		assetManager.loadAndAddBitmap("AimUp","img/dashControls/AimUp.png");
		assetManager.loadAndAddBitmap("AimDown","img/dashControls/AimDown.png");
		
		//Main Menu Items
		//Load in BG Image and assets
		assetManager.loadAndAddBitmap("MainMenuBackground", "img/MainMenu/MenuBackground.jpg");
		assetManager.loadAndAddBitmap("MainMenuLogo", "img/MainMenu/menulogo.png");
		assetManager.loadAndAddBitmap("NewGameButton", "img/MainMenu/newGameButton.png");
		assetManager.loadAndAddMusic("Dungeon_Boss", "music/Video_Dungeon_Boss.mp3");
		assetManager.loadAndAddSound("ButtonClick", "sfx/CursorSelect.wav");
		//DM - Lower this annoying Music
		assetManager.getMusic("Dungeon_Boss").setVolume(.02f);
		
		
		//Team Selection
		//Load in BG Image and assets
		assetManager.loadAndAddBitmap("TSBackground", "img/TeamSelectionImages/MenuBackground.jpg");
		assetManager.loadAndAddBitmap("TSTitle", "img/TeamSelectionImages/TeamSelectionTitle.png");
		assetManager.loadAndAddBitmap("ContinueButton", "img/TeamSelectionImages/continue.png");

	}
}
