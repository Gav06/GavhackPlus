package me.gavin.gavhackplus.mixin;

import me.gavin.gavhackplus.client.Gavhack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public class GuiMainMenuPatch {

    @Inject(method = "drawScreen", at = @At("TAIL"))
    public void drawScreenPatch(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Gavhack.nameVersion, 2, 2, -1);
    }
}
