package minecraftose.client.renderer.particle;

@FunctionalInterface
public interface ParticleCallback{
    
    void invoke(ParticleInstance instance);
    
}
