package minecraftose.client.chat;

import minecraftose.client.Minecraft;
import minecraftose.client.command.ClientCommandDispatcher;
import minecraftose.main.chat.source.MessageSource;
import minecraftose.main.chat.source.MessageSourceOther;
import minecraftose.main.network.packet.s2c.game.S2CPacketChatMessage;
import minecraftose.main.network.packet.c2s.game.C2SPacketChatMessage;
import minecraftose.main.text.Component;
import jpize.util.io.TextProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chat{
    
    private final Minecraft minecraft;
    private final CopyOnWriteArrayList<ChatMessage> messageList;
    private final TextProcessor textProcessor;
    private boolean opened;
    
    private final List<String> history;
    private int historyPointer = 0;
    
    public Chat(Minecraft minecraft){
        this.minecraft = minecraft;
        this.messageList = new CopyOnWriteArrayList<>();
        this.history = new ArrayList<>();
        this.textProcessor = new TextProcessor(false);
        
        textProcessor.setActive(false);
        
        putMessage(new MessageSourceOther(), new Component().text("Enter /help for command list"));
    }
    
    
    public List<ChatMessage> getMessages(){
        return messageList;
    }
    
    public void putMessage(S2CPacketChatMessage messagePacket){
        messageList.add(new ChatMessage(messagePacket.source, messagePacket.components));
    }
    
    public void putMessage(MessageSource source, Component component){
        messageList.add(new ChatMessage(source, component.toFlatList()));
    }
    
    
    public void clear(){
        messageList.clear();
    }
    
    
    public String getEnteringText(){
        return textProcessor.getString();
    }

    private void processMessage(String message){
        if(message.startsWith("/")){
            final ClientCommandDispatcher dispatcher = minecraft.getCommandDispatcher();
            final String command = message.substring(1);

            if(dispatcher.hasCommand(command.split(" ")[0])){
                dispatcher.executeCommand(command);
                return;
            }
        }

        minecraft.getConnection().sendPacket(new C2SPacketChatMessage(message));
    }

    public void enter(){
        final String message = textProcessor.getString();
        processMessage(message);
        textProcessor.clear();
        
        if(!history.isEmpty() && history.get(history.size() - 1).equals(message))
            return;
        
        history.add(history.size(), message);
        historyPointer = history.size() - 1;
    }
    
    public void historyUp(){
        if(!history.isEmpty() && historyPointer == history.size() - 1 && !history.get(history.size() - 1).equals(textProcessor.toString())){
            historyPointer++;
            if(!textProcessor.getString().isBlank())
                history.add(textProcessor.getString());
        }
        
        if(historyPointer - 1 < 0)
            return;
        
        historyPointer--;
        
        textProcessor.removeLine();
        textProcessor.insertLine(history.get(historyPointer));
    }
    
    public void historyDown(){
        if(historyPointer + 2 > history.size())
            return;
        
        historyPointer++;
        
        textProcessor.removeLine();
        textProcessor.insertLine(history.get(historyPointer));
    }
    
    public int getCursorX(){
        return textProcessor.getCursorX();
    }
    
    
    public boolean isOpened(){
        return opened;
    }
    
    private void setOpened(boolean opened){
        this.opened = opened;
        textProcessor.setActive(opened);
        minecraft.getPlayer().getInput().getRotation().setEnabled(!opened);
    }
    
    public void close(){
        minecraft.getPlayer().getInput().getRotation().lockNextFrame();
        historyPointer = history.size() - 1;
        textProcessor.removeLine();
        setOpened(false);
    }
    
    public void open(){
        setOpened(true);
    }
    
    public void openAsCommandLine(){
        open();
        textProcessor.insertChar('/');
    }
    
    
    public TextProcessor getTextProcessor(){
        return textProcessor;
    }
    
}
