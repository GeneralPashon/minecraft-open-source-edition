package minecraftose.client.renderer.level;

import jpize.graphics.mesh.Mesh;
import jpize.util.Disposable;
import jpize.util.file.Resource;
import jpize.gl.Gl;
import jpize.gl.vertex.GlVertexAttr;
import jpize.gl.tesselation.GlFace;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.graphics.util.BufferBuilder;
import jpize.graphics.util.Shader;
import jpize.graphics.util.SkyBox;
import jpize.graphics.util.color.Color;
import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.main.time.GameTime;

public class SkyRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    
    private final SkyBox skyBox;
    private final Matrix4f skyViewMat;
    
    private final Shader shader;
    private final Mesh mesh;

    private float skyBrightness;

    public SkyRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.skyBox = new SkyBox(
            "texture/skybox/1/skybox_positive_x.png",
            "texture/skybox/1/skybox_negative_x.png",
            "texture/skybox/1/skybox_positive_y.png",
            "texture/skybox/1/skybox_negative_y.png",
            "texture/skybox/1/skybox_positive_z.png",
            "texture/skybox/1/skybox_negative_z.png"
        );
        
        this.skyViewMat = new Matrix4f();
        this.shader = new Shader(Resource.internal("shader/level/sky/sky.vert"), Resource.internal("shader/level/sky/sky.frag"));

        this.mesh = new Mesh(new GlVertexAttr(3, GlType.FLOAT)); // pos3
        this.mesh.setMode(GlPrimitive.TRIANGLE_FAN);
        buildSkyDisc().end(mesh.getBuffer());
    }
    
    
    private BufferBuilder buildSkyDisc(){
        final float radius = 16;
        final BufferBuilder builder = new BufferBuilder();
        
        builder.vertex(0.0, radius, 0.0);
        
        for(int i = 0; i <= 360; i += 120){
            builder.vertex(
                radius * Maths.cosDeg(i),
                0,
                radius * Maths.sinDeg(i)
            );
        }
        
        return builder;
    }
    
    
    public void render(PlayerCamera camera){
        // Matrix
        skyViewMat.set(camera.getView());
        skyViewMat.cullPosition();

        // Skybox
        // [Skybox]: skyBox.render(camera.getProjection(), skyViewMat);

        // Calc brightness
        final GameTime time = levelRenderer.getGameRenderer().getMinecraft().getTime();
        this.skyBrightness = calcSkyBrightness(time);

        // Color
        final Color skyColor = getSkyColor();
        final Color fogColor = getFogColor(camera);

        Gl.clearColor(fogColor);
        Gl.depthMask(false);
        Gl.cullFace(GlFace.FRONT);
        
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", skyViewMat);
        shader.setUniform("u_skyColor", skyColor);
        mesh.render();
        
        Gl.cullFace(GlFace.BACK);
        Gl.depthMask(true);
    }


    private float calcSkyBrightness(GameTime time){
        final float sin = Maths.cosDeg(time.getDays() * 360);
        final float nor = sin * 0.5F + 0.5F;
        final float pow = Mathc.pow(nor, 2);
        return Maths.map(pow, 0, 1, 0.15F, 1F);
    }

    public float getSkyBrightness(){
        return skyBrightness;
    }
    
    public Color getFogColor(PlayerCamera camera){
        final float playerY = camera.getPlayer().getLerpPosition().y;
        final float voidFactor = Maths.clamp(playerY + 10, 0, 10) / 10F;
        final float brightness = camera.getPlayer().getMinecraft().getOptions().getBrightness() * 0.1F + 0.9F;
        return new Color(0.6, 0.75, 0.9, 1).mul3(skyBrightness * voidFactor * brightness);
    }
    
    public Color getSkyColor(){
        return new Color(0.1, 0.4, 0.9, 1F).mul3(skyBrightness);
    }

    public float getFogStart(){
        return 0.6F;
    }
    
    
    @Override
    public void dispose(){
        skyBox.dispose();
        shader.dispose();
        mesh.dispose();
    }
    
}
