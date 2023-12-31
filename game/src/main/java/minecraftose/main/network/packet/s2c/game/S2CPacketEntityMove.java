package minecraftose.main.network.packet.s2c.game;

import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec3f;
import jpize.net.tcp.packet.IPacket;
import jpize.physic.utils.Velocity3f;
import minecraftose.client.network.ClientPacketHandler;
import minecraftose.server.player.ServerPlayer;
import jpize.util.io.JpizeInputStream;
import jpize.util.io.JpizeOutputStream;

import java.io.IOException;
import java.util.UUID;

public class S2CPacketEntityMove extends IPacket<ClientPacketHandler>{

    public static final int PACKET_ID = 9;

    public S2CPacketEntityMove(){
        super(PACKET_ID);
    }


    public UUID uuid;
    public Vec3f position;
    public EulerAngles rotation;
    public Velocity3f velocity;

    public S2CPacketEntityMove(ServerPlayer serverPlayer){
        this();
        uuid = serverPlayer.getUUID();
        position = serverPlayer.getPosition();
        rotation = serverPlayer.getRotation();
        velocity = serverPlayer.getVelocity();
    }


    @Override
    public void write(JpizeOutputStream stream) throws IOException{
        stream.writeUUID(uuid);
        stream.writeVec3f(position);
        stream.writeEulerAngles(rotation);
        stream.writeVec3f(velocity);
    }

    @Override
    public void read(JpizeInputStream stream) throws IOException{
        uuid = stream.readUUID();
        position = stream.readVec3f();
        rotation = stream.readEulerAngles();
        velocity = new Velocity3f(stream.readVec3f());
    }

    @Override
    public void handle(ClientPacketHandler handler){
        handler.entityMove(this);
    }

}