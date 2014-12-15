package com.threeml.awu.world.BackgroundObject;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

public class Terrain extends Sprite {
	
	public Terrain(GameScreen gameScreen) {
		super(gameScreen);
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Terrain");
		
		mBound.halfWidth = 1000.0f;
		mBound.halfHeight = 300.0f;
		
		this.CreateTerrainPhysics();
		
	}
	/**
	 * Create a new Terrain object
	 * 
	 * @param x
	 *            x location of the object
	 * @param y
	 *            y location of the object
	 * @param width
	 *            width of the object
	 * @param height
	 *            height of the object
	 * @param bitmap
	 *            Bitmap used to represent this object
	 * @param gameScreen
	 *            Gamescreen to which this object belongs
	 */
	public Terrain(float x, float y, float width, float height,
			Bitmap bitmap, GameScreen gameScreen) {
		super(x,y,width,height,bitmap,gameScreen);
		this.CreateTerrainPhysics();
	}
	
	List<BoundingBox> TerrainBlocks = new ArrayList<BoundingBox>();
	
	public List<BoundingBox> getTerrainBlocks(){
		return TerrainBlocks;
	}
	public void CreateTerrainPhysics(){
		int rectWidth = 0;
		int rectHeight = 5;
		
		int rectsCreated = 0;
		
		for (int yPos = 0; yPos <= this.getBound().getHeight(); yPos += rectHeight)
        {
            rectWidth = 0;

            for (int xPos = 0; xPos <= this.getBound().getWidth(); xPos += 4)
            {

                //if (data[xPos + (yPos * width) + theAlphaByte] == 255) //if not alpha pixel
                if(Color.alpha(mBitmap.getPixel(xPos, yPos)) > 200){
                	
                    rectWidth++;

                    //Check if the box spans the full width of the image.
                    if (rectWidth >= this.mBitmap.getWidth())
                    {

                        // if so make the box and reset for the next line
                        makeBlock(rectWidth, rectHeight, xPos, yPos);
                        rectWidth = 0; //reset rect
                    }

                }
                else if (rectWidth > 1)
                    {
                	
                    makeBlock(rectWidth, rectHeight, xPos, yPos);
                    rectsCreated++;
                    rectWidth = 0; //reset rect

                }
            }
        }
		
		Log.v("CreateTerrainPhysics","Recs Created: "+rectsCreated);
	}
	private void makeBlock(int width, int height, int x, int y) {
		// TODO check this is right?
		//Create a new rect with the properties
		BoundingBox aabBlock = new BoundingBox(width, height, x + width, y + height);
		//Add to list of rect's for the current map
		TerrainBlocks.add(aabBlock);
		
		Log.v("makeBlock", 	"Rect {width: " + width + 
							" height: " + height + 
							" x: " + x + 
							" y: " + y + "}");

	}
	
	public String toString(int i){
		
		BoundingBox bb = getTerrainBlocks().get(i);
		return "width: " + bb.getWidth() + " height: " + bb.getHeight() + " x: " + bb.x + " y: " + bb.y + "}";
	}


}
