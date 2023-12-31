package minecraftose.server.level.gen.generator.testnew;

public abstract class GenLayerVC{ //  extends GenLayer


    protected long worldGenSeed;
    protected GenLayerSimplexNoise parent;
    protected long chunkSeed;
    protected long baseSeed;


    public GenLayerVC(long ID){
        // super(ID);
    }

    /*
    public static GenLayerVC genHorizontalRockOffsetMap(long seed){
        GenLayerSimplexNoise noise = new GenLayerSimplexNoise(seed, 2, 0.6f, 50, 0);
        noise.scale = 128;
        // GenLayerVC.drawImageGrayScale(512, noise, "HorizontalRockOffsetMap 0 Noise");

        return noise;
    }
    */
    public static GenLayerVC genNoiseFieldModifier(long seed, int offset){
        GenLayerSimplexNoise noise = new GenLayerSimplexNoise(seed, 5, 0.96f, 170, offset, 1024D);
        // GenLayerVC.drawImageGrayScale(512, noise, "NoiseFieldModifier 0 Noise");

        GenLayerVC noisemod = new GenLayerBlurAll(seed, 2, 6, noise);
        // GenLayerVC.drawImageGrayScale(512, noisemod, "NoiseFieldModifier 1 Blur");

        return noisemod;
    }

    /*
    public static GenLayerVC genAgemap(long seed){
        GenLayerSimplexNoiseUnclamped noise = new GenLayerSimplexNoiseUnclamped(seed, 1, 1f, 90, 0);
        noise.resolution = 512;

        // GenLayerVC.drawImageGrayScale(512, noise, "Age 0 Noise");

        return noise;
    }
    */

    /*
    // Currently unused
    public static GenLayerVC genHeightmap(long seed){
        GenLayerSimplexNoise noise = new GenLayerSimplexNoise(seed, 6, 0.6f, 67, 80);
        // GenLayerVC.drawImageGrayScale(512, noise, "Heightmap 0 Noise");

        return noise;
    }
    */
    // Generates Fertility, Temperature and Rainfall map
    // R = Temperature    temp = R-Value / 4.25 - 30 ( = Temp range from -30 to +30)      | inverse R-Value = (temp + 30) * 4.25
    // G = Fertility      = generated from temp * rain                         (+ water nearby?)
    // B = Rain           between 0 - 255
    /*
    public static GenLayerVC genClimate(long seed){
        GenLayerVC noise = new GenLayerClimateRGBNoise(1L);
        // GenLayerVC.drawImageRGB(512, noise, "Climate 0 Noise");

        noise.initWorldGenSeed(seed);

        GenLayerVC climate = new GenLayerBlurAll(2L, 1, 3, noise);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 2 Blur ");

        climate = new GenLayerContrastAndBrightnessAll(3L, 0.5f, 0, climate);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 2 Contrast");

        climate = new GenLayerClampedSubstractAll(4L, 0, 1, climate, new GenLayerBlurAll(seed, 1, 15, noise));
        // GenLayerVC.drawImageRGB(512, climate, "Climate 3 With Unsharp Mask");

        climate = GenLayerZoom.magnify(1000L, climate, 2);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 4 2xZoom");

        climate = new GenLayerBlurAll(2L, 1, 3, climate);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 5 Blur");

        climate = GenLayerZoom.magnify(1000L, climate, 2);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 6 2xZoom");

        climate = new GenLayerBlurAll(2L, 1, 3, climate);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 7 Blur");

        climate = GenLayerZoom.magnify(1000L, climate, 3);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 8 4xZoom");

        climate = new GenLayerBlurAll(2L, 1, 3, climate);
        // GenLayerVC.drawImageRGB(512, climate, "Climate 9 Blur");


        climate.initWorldGenSeed(seed);

        return climate;
    }
    */
    /*
    public static GenLayerVC genDeposits(long seed){
        GenLayerVC noise = new GenLayerWeightedNoise(1L, EnumMaterialDeposit.getLayerGenDeposits());
        // GenLayerVC.drawImageRGB(512, noise, "Deposits 0 Noise");

        noise.initWorldGenSeed(seed);

        GenLayerVC deposits = GenLayerZoom.magnify(1L, noise, 4);
        // GenLayerVC.drawImageRGB(512, deposits, "Deposits 1 8x Magnify");

        deposits = new GenLayerAddPerlin(seed, 3, 0.5f, 20, 15, 8, 148, deposits);
        // GenLayerVC.drawImageRGB(512, deposits, "Deposits 2 Add height noise (green)");


        //GenLayerVC deposits = GenLayerZoom.magnify(2L, noise, 2);
        //GenLayeVC.drawImageRGB(512, deposits, "Deposits 1 2x Magnify");

        //deposits = new GenLayerAddNoise(3L, 70, 10, 8, 70, 22, deposits);
        //GenLayerVC.drawImageRGB(512, deposits, "Deposits 2 Add height variation (green)");

        //deposits = GenLayerZoom.magnify(4L, deposits, 2);
        //GenLayerVC.drawImageRGB(512, deposits, "Deposits 3 2x Magnify");

        //deposits = new GenLayerBlurSelective(2L, 2, 5, false, 8, deposits);
        //GenLayerVC.drawImageRGB(512, deposits, "Deposits 4 Blur height variation (green)");


        deposits.initWorldGenSeed(seed);

        return deposits;
    }
    */
    /*
    public static GenLayerVC genForest(long seed){
        GenLayerSimplexNoise forest = new GenLayerSimplexNoise(seed, 6, 0.6f, 120, -25, 256D);
        // GenLayerVC.drawImageGrayScale(512, forest, "Forest 0 Noise");

        return forest;
    }


    public static GenLayerVC genLowVegetation(long seed){
        GenLayerSimplexNoise noise = new GenLayerSimplexNoise(seed, 5, 0.5f, 100, 0);
        // GenLayerVC.drawImageGrayScale(512, noise, "Low Vegetation 0 Noise");
        return noise;
    }
    */
	
	
	
	/*public static GenLayerVC genStars(long seed) {
		GenLayerVC noise = new GenLayerNoise(1L, 46);
		GenLayerVC.drawImageGrayScale(512, noise, "Forest 0 Noise");
		
		GenLayerVC forest = new GenLayerBlurAll(2L, 1, 4, noise);
		GenLayerVC.drawImageGrayScale(512, forest, "Forest 1 Blur");
		
		forest = new GenLayerContrastAndBrightnessSelective(3L, 4f, 0, forest);
		GenLayerVC.drawImageGrayScale(512, forest, "Forest 2 Contrast");
		
		forest = new GenLayerClampedSubstractSelective(4L, 0, 1, forest, new GenLayerBlurAll(seed, 1, 15,  noise));
		GenLayerVC.drawImageGrayScale(512, forest, "Forest 3 Unsharp Mask");
		
		forest = GenLayerZoom.magnify(1000L, forest, 2);
		GenLayerVC.drawImageGrayScale(512, forest, "Forest 4 Zoom");

		forest = new GenLayerBlurAll(2L, 1, 3, forest);
		GenLayerVC.drawImageGrayScale(512, forest, "Forest 5 Blur");

		
		forest.initWorldGenSeed(seed);
		
		return forest;
	}*/


    // Formerly Biomes
    /*
    public static GenLayerVC genErosion(long seed){
        GenLayerVC noise = new GenLayerWeightedNoise(1L, BiomeVC.getBiomes());
        drawImageBiome(512, noise, "Erosion 1 noise");

        GenLayerVC erosion = GenLayerZoom.magnify(seed, noise, 8);
        drawImageBiome(512, erosion, "Erosion 2 8x magnify");

        erosion.initWorldGenSeed(seed);

        return erosion;
    }
    */

    // public static boolean shouldDraw = false;
    /*
    public static void drawImageBiome(int size, GenLayerVC genlayer, String name){
        drawImage(size, genlayer, name, 0);
    }
    */
    /*
    public static void drawImageRGB(int size, GenLayerVC genlayer, String name){
        drawImage(size, genlayer, name, 1);
    }
    */
    /*
    public static void drawImageGrayScale(int size, GenLayerVC genlayer, String name){
        drawImage(size, genlayer, name, 3);
    }
    */
    /*
    public static void drawImageRocks(int size, GenLayerVC genlayer, String name){
        drawImage(size, genlayer, name, 2);
    }
    */
    /*
    public static void drawImage(int size, GenLayerVC genlayer, String name, int type){
        if(!shouldDraw)
            return;
        drawImage(size, genlayer.getInts(0, 0, size, size), name, type);
    }
    */
    /*
    public static void drawImage(int x, int z, GenLayerVC genlayer, String name, int type){
        if(!shouldDraw)
            return;
        drawImage(512, genlayer.getInts(x, z, 512, 512), name, type);
    }
    */

    // type:
    // 0 .. biome
    // 1 .. rgb
    // 2 .. rocklayer
    // 3 .. grayscale from lowest byte
    /*
    public static void drawImage(int size, int[] ints, String name, int type){
        if(!shouldDraw)
            return;

        try{
            File outFile = new File(name + ".bmp");

            Color c;

            BufferedImage outBitmap = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) outBitmap.getGraphics();
            graphics.clearRect(0, 0, size, size);

            System.out.println(name + ".bmp");

            for(int x = 0; x < size; x++){
                for(int z = 0; z < size; z++){
                    int id = ints[x * size + z];

                    switch(type){
                        case 0:
                            if(id != -1 && BiomeVC.getBiomeGenArray()[id] != null){
                                graphics.setColor(new Color(BiomeVC.getBiome(id).getBiomeColor()));
                                graphics.drawRect(x, z, 1, 1);
                            }
                            break;

                        case 1:
                            c = new Color((id >> 16) & 0xff, (id >> 8) & 0xff, id & 0xff);
                            graphics.setColor(c);
                            graphics.drawRect(x, z, 1, 1);

                            break;

                        case 2:
                            int color = ((id * 8) << 16) + ((id * 8) << 8) + ((id * 8));
                            graphics.setColor(Color.getColor("", color));
                            graphics.drawRect(x, z, 1, 1);
                            break;

                        case 3:
                            c = new Color(id & 0xff, id & 0xff, id & 0xff);
                            graphics.setColor(c);
                            graphics.drawRect(x, z, 1, 1);

                    }
                }
            }

            ImageIO.write(outBitmap, "BMP", outFile);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    */


    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public abstract int[] getInts(int x, int z, int xSize, int zSize);

    public static int validateInt(int[] array, int index){
		/*if(TFCBiome.biomeList[array[index]] == null)
			System.out.println("Error garbage data: "+array[index]);*/
        return array[index];
    }

    public static void validateBiomeIntArray(int[] array, int xSize, int zSize){
        for(int z = 0; z < zSize; z++){
            for(int x = 0; x < xSize; x++){
                if(BiomeVC.biomeList[array[x + z * xSize]] == null){
                    System.out.println("Error Array garbage data: " + array[x + z * xSize]);
                    return;
                }
            }
        }
    }


    public int[] cutMargins(int[] inInts, int sizeX, int sizeZ, int margin){
        int[] resultInts = new int[(sizeX - 2 * margin) * (sizeZ - 2 * margin)];
        int j = 0;

        for(int i = 0; i < inInts.length; i++){
            int xpos = i % sizeX;
            int zpos = i / sizeX;


            if(xpos >= margin && xpos < sizeX - margin && zpos >= margin && zpos < sizeZ - margin){
                resultInts[j++] = inInts[i];
            }
        }

        return resultInts;
    }


    public int[] cutRightAndBottom(int[] inInts, int sizeX, int sizeZ, int margin){
        int[] resultInts = new int[(sizeX - margin) * (sizeZ - margin)];
        int j = 0;

        for(int i = 0; i < inInts.length; i++){
            int xpos = i % sizeX;
            int zpos = i / sizeX;


            if(xpos < sizeX - margin && zpos < sizeZ - margin){
                resultInts[j++] = inInts[i];
            }
        }

        return resultInts;
    }

}