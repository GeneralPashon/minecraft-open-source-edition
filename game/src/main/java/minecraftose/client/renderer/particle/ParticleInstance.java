package minecraftose.client.renderer.particle;

import jpize.graphics.texture.Region;
import jpize.graphics.util.color.Color;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.utils.Velocity3f;

public class ParticleInstance{
    
    private final Particle particle;
    private final long creationTime;
    private float elapsedTimeSeconds;
    private float alpha;
    
    public Region region;
    public float lifeTimeSeconds;
    public Vec3f position;
    public Velocity3f velocity;
    public float rotation, size;
    public Color color;
    
    public ParticleInstance(Particle particle, Vec3f position){
        this.region = new Region();
        this.particle = particle;
        this.position = position.copy();
        this.creationTime = System.currentTimeMillis();
        this.color = particle.color.copy();
        this.velocity = new Velocity3f();
    }
    
    public void update(){
        // Alpha
        elapsedTimeSeconds = (System.currentTimeMillis() - creationTime) / 1000F;
        alpha = particle.alphaFunc.apply(elapsedTimeSeconds / lifeTimeSeconds);
        alpha = Maths.clamp(alpha, 0, 1);
        // Animate
        particle.onAnimate.invoke(this);
    }
    
    public Particle getParticle(){
        return particle;
    }
    
    public float getElapsedTimeSeconds(){
        return elapsedTimeSeconds;
    }
    
    public float getAlpha(){
        return alpha;
    }
    
}
