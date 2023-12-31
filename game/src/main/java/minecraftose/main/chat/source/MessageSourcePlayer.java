package minecraftose.main.chat.source;

public class MessageSourcePlayer extends MessageSource{
    
    private final String playerName;
    
    public MessageSourcePlayer(String playerName){
        super(MessageSources.PLAYER);
        this.playerName = playerName;
    }
    
    public String getPlayerName(){
        return playerName;
    }
    
}
