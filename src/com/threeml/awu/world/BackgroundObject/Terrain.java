package com.threeml.awu.world.BackgroundObject;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.threeml.awu.util.BoundingBox;
import com.threeml.awu.world.GameScreen;
import com.threeml.awu.world.Sprite;

/**
 * 
 * @author Dean
 *
 * Terrain holds the location of the walkable terrain
 * By scanning on create of the terrain, we are able to draw boxes
 * to represent the walkable location on the map allowing for AABB 
 * collision detection. The "walkable" rectangles then can be updated
 * only when the terrain bitmap changes (destructible).
 * 
 * AABB can then be easily detected by checking any collisions between
 * the bounding boxes and another boundingBox i.e. player, Healthpack etc
 * 
 */

/*
 Example of how the code works                                                                              
 +----------------------------+              +----------------------------+   
 | 			                  |              |                            |   
 |   XXXXX                    | +------------+   +---+                    |   
 | XXXXXXXXX        XXXXXXX   | BitMap       | +-------+        +-----+   |   
 |XXXXXXXXXXX     XXXXXXXXXXXX|     to       +----------+     +-----------+   
 |XXXXXXXXXXXXXXXXXXXXXXXXXXXX|       BBoxes |----------------------------|   
 |XXXXXXXXXXXXXXXXXXXXXXXXXXXX| +--------->  |----------------------------|   
 +----------------------------+              +----------------------------+  
 			  i:	Each BBox is at least 4px wide and every one is 5px height
 */
public class Terrain extends Sprite {
	
	//Holds the locations of all the boxes
	private List<BoundingBox> TerrainBlocks = new ArrayList<BoundingBox>();
	
	public Terrain(GameScreen gameScreen) {
		super(gameScreen);
		mBitmap = gameScreen.getGame().getAssetManager().getBitmap("Terrain");
		
		mBound.halfWidth = 1000.0f;
		mBound.halfHeight = 300.0f;
		
		//Initially create AABB bounding boxes for the terrain
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
		
		//Initially create AABB bounding boxes for the terrain
		this.CreateTerrainPhysics();
	}
	
	/* Loops through each row and column 4px at a time across and 5px down
	 *  then calls makeBlock() to add a new boundingBox to the array 
	*/
	public void CreateTerrainPhysics(){
		int rectWidth = 0;
		int rectHeight = 20;
		
		int rectsCreated = 0;
		
		for (int yPos = 0; yPos <= this.getBound().getHeight(); yPos += rectHeight)
        {
            rectWidth = 0;

            for (int xPos = 0; xPos <= this.getBound().getWidth(); xPos += 10)
            {
                if(Color.alpha(mBitmap.getPixel(xPos, yPos)) == 255){ //If the current Pixel is non-Alpha
                	
                	rectWidth = rectWidth + 4; //Increase the size of the box
                	
                	//When the rect starts (less that 5px wide)
            		if(rectWidth < 5){
            			Log.v("lc","StartRect: ("+xPos+","+yPos+") rectWidth?"+rectWidth);
            		}
            		
                    //Check if the box spans the full width of the image.
                    if (rectWidth >= this.mBitmap.getWidth())
                    {
                        Log.v("lc","EndRect: ("+xPos+","+yPos+") [w:"+rectWidth+",h:"+rectHeight+"]");
                        // if so make the box and reset for the next line
                        makeBlock(rectWidth, rectHeight, xPos, yPos);
                        rectsCreated++;                        
                        rectWidth = 0; //reset rect
                    }

                }
                //If it is an alpha pixel and the width is greater than 1
                else if (rectWidth > 1)
                    {
                    Log.v("lc","EI EndRect: ("+xPos+","+yPos+") [w:"+rectWidth+",h:"+rectHeight+"]");
                    // if so make the box and reset for the next line
                    makeBlock(rectWidth, rectHeight, xPos, yPos);
                    rectsCreated++;
                    rectWidth = 0; //reset rect

                }
            }
        }
		
		Log.v("CreateTerrainPhysics","Recs Created: "+rectsCreated);
	}
	
	/**
	 * Creates a boundingBox and adds to the TerrainBlocks ArrayList
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 */
	private void makeBlock(int width, int height, int x, int y) {
		//Create a new rect with the properties       
		BoundingBox aabBlock = new BoundingBox(x+width,(height+y),width/2, height/2);
		//Add to list of rect's for the current map
		TerrainBlocks.add(aabBlock);
		
		Log.v("makeBlock", 	"BoundingBox{x: " + x + 
										" y: " + y + 
										" width: " + width + 
										" height: " + height+"}");

	}
	
	
	/**
	 * Returns List of Bounding Boxes that represent Terrain boundaries
	 */
	public List<BoundingBox> getTerrainBlocks(){
		return TerrainBlocks;
	}
	
	/**
	 * ToString Method that outputs the BB details at a position in 
	 * the TerrainBlocks List
	 */
	public String toString(int i){
		BoundingBox bb = getTerrainBlocks().get(i);
		return "width: " + bb.getWidth() + " height: " + bb.getHeight() + " x: " + bb.x + " y: " + bb.y + "}";
	}


}
