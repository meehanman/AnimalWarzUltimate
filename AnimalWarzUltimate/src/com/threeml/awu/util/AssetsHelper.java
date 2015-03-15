package com.threeml.awu.util;

import android.graphics.Bitmap;

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
	
	// Load in the assets used by this layer
	public static void loadAllAssets(Game mGame){

		//Game Assets
		AssetStore assetManager = mGame.getAssetManager();
		
		//Player Assets
		assetManager.loadAndAddBitmap("Player", "img/player/worm_walk_left.png");
		assetManager.loadAndAddBitmap("PlayerBackFlip", "img/player/wbackflp.png");
		assetManager.loadAndAddBitmap("PlayerWalk", "img/player/wwalk.png");
		assetManager.loadAndAddBitmap("PlayerWin", "img/player/wwinner.png");
		assetManager.loadAndAddBitmap("PlayerUp", "img/player/wflyup.png");
		assetManager.loadAndAddBitmap("PlayerFall", "img/player/wfall.png");
		assetManager.loadAndAddBitmap("PlayerDie", "img/player/wdie.png");
		assetManager.loadAndAddBitmap("PlayerGrave", "img/player/grave1.png");
		
		//Game Objects
		assetManager.loadAndAddBitmap("Health", "img/gameObject/healthpack.png");
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
		assetManager.loadAndAddBitmap("WeaponArchive", "img/dashControls/WeaponArchive.png");
		
		//Weapon Menu images
		assetManager.loadAndAddBitmap("Gun", "img/weapons/gun.png");
		assetManager.loadAndAddBitmap("Grenade", "img/weapons/grenade.png");
		assetManager.loadAndAddBitmap("Rocket", "img/weapons/rocket.png");
		assetManager.loadAndAddBitmap("Bat", "img/weapons/bat.png");
		
		//Crosshair
		assetManager.loadAndAddBitmap("Crosshair", "img/weapons/crshairrSingle.png");
		//assetManager.loadAndAddBitmap("Bat", "img/weapons/crshairr.png");
		
		//Weapon and Projectile images
		assetManager.loadAndAddBitmap("Bullet", "img/weapons/Bullet.png");
		assetManager.loadAndAddSound("Bullet_SFX", "sfx/ShotGunFire.wav");
		//Main Menu Items
		//Load in BG Image and assets
		assetManager.loadAndAddBitmap("MainMenuBackground", "img/MainMenu/MenuBackground.jpg");
		assetManager.loadAndAddBitmap("MainMenuLogo", "img/MainMenu/menulogo.png");
		assetManager.loadAndAddBitmap("NewGameButton", "img/MainMenu/newGameButton.png");
		assetManager.loadAndAddBitmap("OptionsButton", "img/MainMenu/OptionsButton.png");
		assetManager.loadAndAddMusic("Dungeon_Boss", "music/Video_Dungeon_Boss.mp3");
		assetManager.loadAndAddSound("ButtonClick", "sfx/CursorSelect.wav");
		//DM - Lower this annoying Music
		assetManager.getMusic("Dungeon_Boss").setVolume(.02f);
		
		
		//Team Selection
		//Load in BG Image and assets
		assetManager.loadAndAddBitmap("TSBackground", "img/TeamSelectionImages/MenuBackground.jpg");
		assetManager.loadAndAddBitmap("TSTitle", "img/TeamSelectionImages/TeamSelectionTitle.png");
		assetManager.loadAndAddBitmap("ContinueButton", "img/TeamSelectionImages/continue.png");
		
		//Options Menu
		assetManager.loadAndAddBitmap("OptionsBackground", "img/OptionsMenuControls/OptionsMenuBackground.jpg");
		assetManager.loadAndAddBitmap("OptionsTitle", "img/OptionsMenuControls/OptionsMenuTitle.png");
		assetManager.loadAndAddBitmap("BackButton", "img/OptionsMenuControls/BackButton.png");
		assetManager.loadAndAddBitmap("SoundYButton", "img/OptionsMenuControls/SoundY.png");
		assetManager.loadAndAddBitmap("SoundNButton", "img/OptionsMenuControls/SoundN.png");
		assetManager.loadAndAddBitmap("AudioYButton", "img/OptionsMenuControls/AudioY.png");
		assetManager.loadAndAddBitmap("AudioNButton", "img/OptionsMenuControls/AudioN.png");
		

	}

	// Load in the assets used by this layer
	public static void loadMapAssets(String MapName,Game mGame){
		//Game Assets
		AssetStore assetManager = mGame.getAssetManager();
		assetManager.loadAndAddBitmap("smallMapImage", "img/TerrainImages/small/"+MapName+"Map.png");
		assetManager.loadAndAddBitmap("TerrainImage", "img/TerrainImages/large/"+MapName+"Map.png",true);
		assetManager.loadAndAddBitmap("TerrainBackground", "img/TerrainImages/background/MapBackgroundDefault.png");
		
		//TODO - Find Backgrounds to Match Other Images
		//assetManager.loadAndAddBitmap("TerrainImage", "img/TerrainImages/background/"+MapName+".png",true);


	}

}
