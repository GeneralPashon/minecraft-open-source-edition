package minecraftose.client.renderer.chat;

import jpize.Jpize;
import jpize.graphics.font.BitmapFont;
import jpize.graphics.texture.Region;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;
import jpize.math.Maths;
import minecraftose.client.chat.Chat;
import minecraftose.client.chat.ChatMessage;
import minecraftose.client.renderer.GameRenderer;
import minecraftose.client.renderer.text.TextComponentBatch;
import minecraftose.main.text.Component;
import jpize.util.Disposable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRenderer implements Disposable{
    
    private static final float MSG_LIFE_TIME_SEC = 6;
    
    private final GameRenderer gameRenderer;
    private final TextureBatch batch;
    private final TextComponentBatch textBatch;
    private final int chatX, chatY;
    private float scroll;
    private float scrollMotion;
    
    private final Map<Integer, Texture> skins;
    private final Region headRegion, hatRegion;
    
    public ChatRenderer(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        this.textBatch = gameRenderer.getTextComponentBatch();
        this.batch = textBatch.getBatch();
        
        chatX = 10;
        chatY = 10;
        
        skins = new HashMap<>();
        for(int i = 1; i <= 21; i++)
            skins.put(i, new Texture("texture/skin/skin" + i + ".png"));
        
        headRegion = new Region(skins.get(1), 8, 8, 8, 8);
        hatRegion = new Region(skins.get(1), 40, 8, 8, 8);
    }
    
    
    public void render(){
        batch.begin();
        textBatch.setBackgroundColor(0, 0, 0, 0);
        
        final float chatHeight = Jpize.getHeight() / 2F;
        final float chatWidth = Jpize.getWidth() / 2F;
        final BitmapFont font = textBatch.getFont();
        final float scale = font.getScale();
        final float lineAdvance = font.getOptions().getAdvance() * scale;
        
        final Chat chat = gameRenderer.getMinecraft().getChat();
        final List<ChatMessage> messages = chat.getMessages();
        
        final float openedChatY = chatY + (chat.isOpened() ? lineAdvance + 10 : 0);
        float chatMessagesHeight = 0;
        font.getOptions().textAreaWidth = chatWidth;
        for(ChatMessage message: messages)
            chatMessagesHeight += font.getBounds(message.getComponents().toString()).y;
        
        // Enter
        if(chat.isOpened()){
            final String enteringText = chat.getEnteringText();
            final float lineWidth = font.getTextWidth(enteringText);
            
            batch.drawRect(0.4, chatX, chatY + font.getDescentScaled(), Math.max(lineWidth, chatWidth), lineAdvance);
            
            final float cursorLineWidth = font.getTextWidth(enteringText.substring(0, chat.getCursorX()));
            
            textBatch.drawComponent(new Component().text(enteringText), chatX, chatY);

            if(chat.getTextProcessor().isCursorRender())
                batch.drawRect(1, 1, 1, 1, chatX + cursorLineWidth, chatY + font.getDescentScaled(), scale, lineAdvance);
        }
        
        // Scroll
        if(chat.isOpened() && Jpize.input().isInBounds(chatX, openedChatY, chatWidth, chatHeight))
            scrollMotion -= Jpize.input().getScroll() * Jpize.getDt() * lineAdvance * 10;
        
        scroll += scrollMotion;
        scrollMotion *= 0.95F;
        
        if(!chat.isOpened())
            scroll = 0;
        
        scroll = Math.min(0, scroll);
        scroll = Math.max(Math.min(0, chatHeight - chatMessagesHeight), scroll);
        
        // Chat
        final float headSize = lineAdvance + 0;
        final float headAdvance = headSize * 1.5F;
        final float headDrawX = (headAdvance - headSize) / 2;
        final float headDrawY = (lineAdvance - headSize) / 2;
        
        batch.getScissor().begin(0, chatX, openedChatY + font.getDescentScaled(), chatWidth + headAdvance, chatHeight); // Scissors begin
        font.getOptions().textAreaWidth = chatWidth + headAdvance;

        int textAdvanceY = 0;
        for(int i = messages.size() - 1; i >= 0; i--){
            final ChatMessage message = messages.get(i);
            
            float alpha = 1F;
            if(!chat.isOpened()){
                if(message.getSeconds() < MSG_LIFE_TIME_SEC)
                    alpha = (float) Math.min(1, MSG_LIFE_TIME_SEC - message.getSeconds());
                else
                    continue;
            }
            
            final float textHeight = textBatch.getFont().getTextHeight(message.getComponents().toString());
            final float renderChatY = openedChatY + textAdvanceY + scroll;
            final float lineWrapAdvanceY = textHeight - lineAdvance;
            
            final boolean isPlayer = message.getSource().isPlayer();

            // Render background
            batch.drawRect(0.4 * alpha, chatX, renderChatY + font.getDescentScaled(), chatWidth + headAdvance, textHeight);
            
            // Render head
            if(isPlayer){
                final int skinID = Maths.abs(message.getSource().asPlayer().getPlayerName().hashCode()) % 20 + 1;
                final Texture skin = skins.get(skinID);
                
                batch.setAlpha(alpha);
                batch.draw(skin, chatX + headDrawX, renderChatY + lineWrapAdvanceY + headDrawY, headSize, headSize, headRegion);
                
                batch.setTransformOrigin(0.5, 0.5);
                batch.scale(1.1);
                batch.draw(skin, chatX + headDrawX, renderChatY + lineWrapAdvanceY + headDrawY, headSize, headSize, hatRegion);
                batch.scale(1);
            }
            // Render text
            textBatch.drawComponents(message.getComponents(), chatX + headAdvance, renderChatY + lineWrapAdvanceY, chatWidth, alpha);
            
            textAdvanceY += (int) textHeight;
        }
        
        batch.end();
        batch.getScissor().end(0); // Scissors end
    }
    
    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
