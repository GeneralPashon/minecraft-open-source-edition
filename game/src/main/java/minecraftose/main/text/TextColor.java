package minecraftose.main.text;

import jpize.graphics.util.color.ImmutableColor;

public enum TextColor{
    
    BLACK        (new ImmutableColor(0  , 0  , 0  )),
    DARK_BLUE    (new ImmutableColor(0  , 0  , 170)),
    DARK_GREEN   (new ImmutableColor(0  , 170, 0  )),
    DARK_AQUA    (new ImmutableColor(0  , 170, 170)),
    DARK_RED     (new ImmutableColor(200, 0  , 0  )),
    DARK_PURPLE  (new ImmutableColor(170, 0  , 170)),
    ORANGE       (new ImmutableColor(255, 170, 0  )),
    GRAY         (new ImmutableColor(170, 170, 170)),
    DARK_GRAY    (new ImmutableColor(85 , 85 , 85 )),
    BLUE         (new ImmutableColor(30 , 144, 255)),
    GREEN        (new ImmutableColor(85 , 255, 85 )),
    AQUA         (new ImmutableColor(85 , 255, 255)),
    RED          (new ImmutableColor(255, 85 , 85 )),
    LIGHT_PURPLE (new ImmutableColor(255, 85 , 255)),
    YELLOW       (new ImmutableColor(255, 255, 85 )),
    WHITE        (new ImmutableColor(255, 255, 255));
    
    
    public final ImmutableColor color;
    
    TextColor(ImmutableColor color){
        this.color = color;
    }
    
}
