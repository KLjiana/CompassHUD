package org.hiedacamellia.compasshud.hud;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import org.hiedacamellia.compasshud.CompassHUD;
import org.hiedacamellia.compasshud.config.CompassHUDConfig;

import java.util.Objects;
import java.util.Optional;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class InGameHUD {

    static ItemStack compassStack = new ItemStack(Items.COMPASS);
    static ItemStack clockStack = new ItemStack(Items.CLOCK);

    private static BlockPos pos;
    private static ResourceKey<Level> dimension;
    private static long daytime;
    private static long worldtime;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void eventHandler(RenderGuiEvent.Pre event) {

        String mode = CompassHUDConfig.mode;
        int compassX = CompassHUDConfig.compassX;
        int compassY = CompassHUDConfig.compassY;
        int compassColor = CompassHUDConfig.compassColor;
        int posX = CompassHUDConfig.posX;
        int posY = CompassHUDConfig.posY;
        int clockX = CompassHUDConfig.clockX;
        int clockY = CompassHUDConfig.clockY;
        int timeX = CompassHUDConfig.timeX;
        int timeY = CompassHUDConfig.timeY;
        String timeText = Component.translatable("string.compasshud.timetext").getString();
        int  dayX = CompassHUDConfig.dayX;
        int dayY = CompassHUDConfig.dayY;
        String dayText = Component.translatable("string.compasshud.daytext").getString();
        int clockColor = CompassHUDConfig.clockColor;

        try {
            Level level = Minecraft.getInstance().level;
            LocalPlayer player = Minecraft.getInstance().player;
            pos = player.getOnPos();
            dimension = level.dimension();
            daytime = level.getDayTime();
            worldtime = level.getGameTime();
            BlockPos bs;
            if(dimension==Level.OVERWORLD) {
                bs = level.getSharedSpawnPos();
            }else {
                bs = BlockPos.containing(0,0,0);
            }

            LodestoneTracker lodestonetracker = new LodestoneTracker(Optional.of(GlobalPos.of(level.dimension(), bs)), true);
            compassStack.set(DataComponents.LODESTONE_TRACKER, lodestonetracker);
            compassStack.inventoryTick(level, Minecraft.getInstance().player, 0, false);
        }catch (Exception e){
            CompassHUD.LOGGER.error("Failed to parse minecraft instance", e);
        }

        GuiGraphics graphics=event.getGuiGraphics();


        int h = graphics.guiHeight();
        int w = graphics.guiWidth();

        if (Objects.equals(mode, "COMPASS") || Objects.equals(mode, "BOTH")) {
            graphics.renderItem(compassStack, compassX, compassY);
            String text = "X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ();
            graphics.drawString(Minecraft.getInstance().font,text, posX, posY , compassColor);
        }

        if (Objects.equals(mode, "CLOCK") || Objects.equals(mode, "BOTH")) {
            String time = timeText + " " + String.format("%02d:%02d", daytime / 1000 ,(daytime % 1000) * 60 / 1000);
            String day = dayText + " " + worldtime / 24000;
            if (Objects.equals(mode, "BOTH")) {
                graphics.renderItem(clockStack, w - clockX - 16, clockY);
                int timelength = Minecraft.getInstance().font.width(time);
                int daylength = Minecraft.getInstance().font.width(day);
                graphics.drawString(Minecraft.getInstance().font, time, w - timeX - timelength, timeY, clockColor);
                graphics.drawString(Minecraft.getInstance().font, day, w - dayX - daylength, dayY, clockColor);
            } else {
                graphics.renderItem(clockStack, clockX, clockY);
                graphics.drawString(Minecraft.getInstance().font, time, timeX, timeY, clockColor);
                graphics.drawString(Minecraft.getInstance().font, day, dayX, dayY, clockColor);
            }
        }
    }
}
