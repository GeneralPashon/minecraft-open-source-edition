package minecraftose.main.network.packet.s2c.game;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;

public class S2CPacketTeleportPlayer extends IPacket<ClientPacketHandler>{
    
    public static final int PACKET_ID = 20;
    
    public S2CPacketTeleportPlayer(){
        super(PACKET_ID);
    }
    
    
    public Vec3f position;
    public EulerAngles rotation;
    public String levelName;
    
    public S2CPacketTeleportPlayer(String levelName, Vec3f position, EulerAngles rotation){
        this();
        this.levelName = levelName;
        this.position = position;
        this.rotation = rotation;
    }
    
    
    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUTF(levelName);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }
    
    @Override
    public void read(JpizeInputStream stream) throws IOException{
        levelName = stream.readUTF();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.teleportPlayer(this);
    }
    
}