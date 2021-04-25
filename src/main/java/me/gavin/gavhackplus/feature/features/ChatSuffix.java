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
                suffix = StringEscapeUtils.unescapeCsv(" \uff5c \u24d6\u24d0\u24e5\u24d7\u24d0\u24d2\u24da+");
            ((ICPacketChatMessage)event.getPacket()).setMessageText(((CPacketChatMessage) event.getPacket()).getMessage() + suffix);
        }
    }
}
