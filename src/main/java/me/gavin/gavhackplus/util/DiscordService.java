package me.gavin.gavhackplus.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.gavin.gavhackplus.client.Gavhack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class DiscordService {
    public static final DiscordService INSTANCE = new DiscordService();

    private final DiscordRPC rpc = DiscordRPC.INSTANCE;

    private final String[] appIds = new String[] { "835021677292421152" };

    private final String[] boonoImages = new String[] {"bed", "closeup", "lay", "cute", "lick"};

    private final long startTime = System.currentTimeMillis();

    private final Minecraft mc = Minecraft.getMinecraft();

    private Thread thread = null;

    @SuppressWarnings("BusyWait")
    public void enable() {
        rpc.Discord_ClearPresence();

        DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRichPresence presence = new DiscordRichPresence();

        presence.largeImageText = Gavhack.nameVersion;
        presence.startTimestamp = startTime;

        handlers.ready = user -> System.out.println("Discord RPC ready! Username: " + user.username);
        handlers.disconnected = (code, message) -> System.out.println("Disconnected with error code " + code + " and trace:\n" + message);
        handlers.joinRequest = user -> {
            if (!mc.isIntegratedServerRunning() && mc.getCurrentServerData() == null) {
                mc.setServerData(new ServerData("2b2t", "2b2t.org", false));
            }
        };

        rpc.Discord_Initialize(appIds[0], handlers, true, "");

        thread = new Thread(() -> {
            int boonoIndex = 0;
            while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();

                presence.largeImageKey = boonoImages[boonoIndex];

                presence.details = "gavhack!!!!!!! (reap is cool)";
                presence.state = mc.getSession().getUsername();
                rpc.Discord_UpdatePresence(presence);
                if (boonoIndex != boonoImages.length - 1)
                    boonoIndex++;
                else
                    boonoIndex = 0;
                try { Thread.sleep(2000L); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        thread.start();
    }

    public void disable() {
        if (thread == null) return;

        thread.interrupt();
        thread = null;

        rpc.Discord_ClearPresence();
        rpc.Discord_Shutdown();
    }

    private DiscordService() {}
}