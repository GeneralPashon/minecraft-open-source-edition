package minecraftose.client.entity.model;

import jpize.graphics.mesh.IndexedMesh;
import jpize.graphics.util.Shader;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import minecraftose.client.control.camera.PlayerCamera;

public class ModelPart{
    
    private ModelPart parent;
    private final IndexedMesh mesh;
    private final Pose pose, initialPose;
    private boolean show;
    
    public ModelPart(IndexedMesh mesh){
        this.mesh = mesh;
        pose = new Pose();
        initialPose = new Pose();
        show = true;
    }
    
    
    public void render(PlayerCamera camera, Shader shader, String modelMatrixUniform){
        initialPose.updateMatrices(camera, initialPose);
        if(parent == null)
            pose.updateMatrices(camera, initialPose);
        else
            pose.updateMatrices(camera, initialPose, parent.getPose());
        
        if(shader == null || !show)
            return;
        shader.setUniform(modelMatrixUniform, pose.getModelMatrix());
        mesh.render();
    }
    
    
    public void setParent(ModelPart parent){
        this.parent = parent;
    }
    
    public IndexedMesh getMesh(){
        return mesh;
    }
    
    
    public Pose getPose(){
        return pose;
    }
    
    public Pose getInitialPose(){
        return initialPose;
    }
    
    
    public void setInitialPose(float x, float y, float z, float yaw, float pitch, float roll){
        initialPose.getPosition().set(x, y, z);
        initialPose.getRotation().set(yaw, pitch, roll);
    }
    
    public void setInitialPose(Vec3f position, EulerAngles rotation){
        setInitialPose(position.x, position.y, position.z, rotation.yaw, rotation.pitch, rotation.roll);
    }
    
    public void setInitialPose(double x, double y, double z){
        initialPose.getPosition().set(x, y, z);
    }
    
    public void setInitialPose(Vec3f position){
        setInitialPose(position.x, position.y, position.z);
    }
    
    public Vec3f getPosition(){
        return pose.getPosition();
    }
    
    public EulerAngles getRotation(){
        return pose.getRotation();
    }
    
    public boolean isShow(){
        return show;
    }
    
    public void setShow(boolean show){
        this.show = show;
    }
    
}
