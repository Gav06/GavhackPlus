package me.gavin.gavhackplus.feature.features;

import com.darkmagician6.eventapi.EventTarget;
import me.gavin.gavhackplus.events.PacketEvent;
import me.gavin.gavhackplus.feature.Category;
import me.gavin.gavhackplus.feature.Feature;
import me.gavin.gavhackplus.mixin.accessor.ICPacketChatMessage;
import me.gavin.gavhackplus.setting.impl.BooleanSetting;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.apache.commons.lang3.StringEscapeUtils;

public class ChatSuffix extends Feature {

    private BooleanSetting unicode = new BooleanSetting("Unicode", this, true);

    public ChatSuffix() {
        super("ChatSuffix", "Append messages with client name", Category.Chat);
        addSettings(unicode);
    }

    @EventTarget
    public void onPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            String suffix = " | gavhack+";
            if (unicode.getValue())
                suffix = StringEscapeUtils.unescapeCsv(" \uff5c \u01e4\u0394\u1d20\u0126\u0394\u0106\u049c+");

            if (!isCommand(((CPacketChatMessage) event.getPacket()).getMessage()))
                ((ICPacketChatMessage)event.getPacket()).setMessageText(((CPacketChatMessage) event.getPacket()).getMessage() + suffix);
        }
    }

    private boolean isCommand(String message) {
        return message.startsWith("/") || message.startsWith(".") || message.startsWith("*");
    }
}
