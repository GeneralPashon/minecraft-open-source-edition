package minecraftose.client.renderer.level;

import jpize.util.Disposable;
import jpize.util.file.Resource;
import jpize.graphics.util.Shader;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.block.shape.BlockCursor;
import minecraftose.client.control.camera.PlayerCamera;
import minecraftose.client.control.BlockRayCast;

public class BlockSelectorRenderer implements Disposable{
    
    private final LevelRenderer levelRenderer;
    private final Shader shader;
    private final Matrix4f translationMatrix;
    
    public BlockSelectorRenderer(LevelRenderer levelRenderer){
        this.levelRenderer = levelRenderer;
        
        this.shader = new Shader(Resource.internal("shader/line.vert"), Resource.internal("shader/line.frag"));
        
        this.translationMatrix = new Matrix4f();
    }
    
    
    public void render(PlayerCamera camera){
        final BlockRayCast rayCast = levelRenderer.getGameRenderer().getMinecraft().getBlockRayCast();
        if(!rayCast.isSelected())
            return;
        
        shader.bind();
        shader.setUniform("u_projection", camera.getProjection());
        shader.setUniform("u_view", camera.getView());
        
        translationMatrix.toTranslated(new Vec3f(rayCast.getSelectedBlockPosition()).sub(camera.getX(), 0, camera.getZ()));
        shader.setUniform("u_model", translationMatrix);
        
        final BlockCursor shape = rayCast.getSelectedBlockProps().getCursor();
        if(shape != null)
            shape.render();
    }
    
    @Override
    public void dispose(){
        shader.dispose();
    }
    
}
