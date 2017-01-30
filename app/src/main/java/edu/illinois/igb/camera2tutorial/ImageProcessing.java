package edu.illinois.igb.camera2tutorial;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by jleigh on 1/20/17.
 */

public class ImageProcessing {

    public static int identifyCoin(int[] colors, int width){
        return identifyObject(colors, width, 180, 230, 0.45f, true, Color.rgb(0,0,255));
    }

    public static int identifyLeaf(int[] colors, int width){
        return identifyObject(colors, width, 70, 170, 0.15f, true, Color.rgb(0,255,0));
    }

    public static int identifyObject(int[] colors, int width, int lowHue, int highHue, float lowSat, boolean colorPixels, int tagColor){
        int pixelsInCoin = 0;
        int height = colors.length/width;
        int[] tags = new int[colors.length];
        int nexttag = 1;
        // Threshold colors to find "blue" pixels, label regions
        float[] hsv = new float[3];
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                getHSV(colors[x+y*width],hsv);
                if(hsv[0]>=lowHue && hsv[0]<=highHue && hsv[1]>=lowSat){
                    int neighbortag = checkTaggedNeighbors(tags, x, y, width);
                    if(neighbortag>0){
                        tags[x+y*width] = neighbortag;
                    } else {
                        tags[x + y * width] = nexttag;
                        nexttag++;
                    }
                }
            }
        }

        Log.e(Camera2TutActivity.TAG,"Number of tags: "+(nexttag-1));
        // Merge tagged regions
        int[] tagset = makeset(nexttag);
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++) {
                int tag = tags[x+y*width];
                if(tag>0){
                    if(x>0 && tags[x-1+y*width]!=0){
                        union(tagset,tag,tags[x-1+y*width]);
                    }
                    if(y>0 && tags[x+(y-1)*width]!=0){
                        union(tagset,tag,tags[x+(y-1)*width]);
                    }
                    if(x<width-1 && tags[x+1+y*width]!=0){
                        union(tagset,tag,tags[x+1+y*width]);
                    }
                    if(y<height-1 && tags[x+(y+1)*width]!=0){
                        union(tagset,tag,tags[x+(y+1)*width]);
                    }
                }
            }
        }
        for(int x=0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                tags[x+y*width] = find(tagset,tags[x+y*width]);
            }
        }

        // Find biggest circular region
        // TODO measure circular-ness too (if necessary)
        int[] hist = new int[tagset.length];
        for(int i=0; i<tags.length; i++){
            hist[tags[i]]++;
        }
        int max = 0;
        int maxindex = -1;
        for(int i=1; i<hist.length; i++){
            if(hist[i]>max){
                max = hist[i];
                maxindex = i;
            }
        }
        Log.e(Camera2TutActivity.TAG,"Biggest tag: "+maxindex+", "+max+" pixels");
        for(int i=0; i<tags.length; i++){
            if(tags[i]!=maxindex){
                tags[i] = 0;
            }
        }

        // Finally: colorize
        if(colorPixels) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (tags[x + y * width] > 0) {
                        if( (x>0&&tags[x-1+y*width]==0) ||
                            (y>0&&tags[x+(y-1)*width]==0) ||
                            (x<width-1&&tags[x+1+y*width]==0) ||
                            (y<height-1&&tags[x+(y+1)*width]==0) ) {
                            colors[x + y * width] = tagColor;
                        }
                        pixelsInCoin++;
                    }
                }
            }
        }
        return pixelsInCoin;
    }

    protected static void getHSV(int color, float[] hsv){
        float red = Color.red(color)/255.0f;
        float green = Color.green(color)/255.0f;
        float blue = Color.blue(color)/255.0f;
        float minRGB = Math.min(red,Math.min(green,blue));
        float maxRGB = Math.max(red,Math.max(green,blue));
        if(maxRGB==minRGB){
            hsv[0] = 0;
            hsv[1] = 0;
        } else {
            float d = (red == minRGB) ? green - blue : ((blue == minRGB) ? red - green : blue - red);
            float h = (red == minRGB) ? 3 : ((blue == minRGB) ? 1 : 5);
            hsv[0] = 60 * (h - d / (maxRGB - minRGB));
            hsv[1] = (maxRGB - minRGB) / (1.0f * maxRGB);
        }
        hsv[2] = maxRGB;
    }

    // TODO inline this
    protected static int checkTaggedNeighbors(int[] tags, int x, int y, int width){
        if (x > 0 && tags[x-1+y*width] != 0) {
            return tags[x-1+y*width];
        }
        if (y > 0 && tags[x+(y-1)*width] != 0){
            return tags[x+(y-1)*width];
        }
        return -1;
    }

    // Put on your disjoint-set hats, everyone
    protected static int[] makeset(int length){
        int[] set = new int[length];
        for(int i=0; i<set.length; i++){
            set[i] = i;
        }
        return set;
    }

    // Disjoint-set find with path compression
    protected static int find(int[] set, int x){
        if(set[x]!=x) {
            set[x] = find(set, set[x]);
        }
        return set[x];
    }

    // TODO implement union by rank
    // Disjoint-set union
    protected static void union(int[] set, int x, int y){
        int xroot = find(set, x);
        int yroot = find(set, y);
        set[xroot] = yroot;
    }

}
