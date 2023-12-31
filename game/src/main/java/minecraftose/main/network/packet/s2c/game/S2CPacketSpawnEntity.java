package minecraftose.main.network.packet.s2c.game;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.main.entity.Entity;
import minecraftose.main.entity.EntityType;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class S2CPacketSpawnEntity extends IPacket<ClientPacketHandler>{

    public static final int PACKET_ID = 14;

    public S2CPacketSpawnEntity(){
        super(PACKET_ID);
    }
    
    public EntityType<?> type;
    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;

    public S2CPacketSpawnEntity(Entity entity){
        this();
        type = entity.getEntityType();
        uuid = entity.getUUID();
        position = entity.getPosition();
        rotation = entity.getRotation();
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeInt(type.getID());
        stream.writeUTF(uuid.toString());
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        type = EntityType.fromEntityID(stream.readInt());
        uuid = UUID.fromString(stream.readUTF());
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.spawnEntity(this);
    }
    
}