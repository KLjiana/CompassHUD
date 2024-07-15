package com.kljiana.compasshud.mixin;

import com.kljiana.compasshud.config.CompassHudConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "render", at = @At("HEAD"))
    public void renderClock(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo ci) {
        CompassHudConfig.Mode mode = CompassHudConfig.get().mode;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.player != null && !client.options.hudHidden) {
            if (mode == CompassHudConfig.Mode.COMPASS || mode == CompassHudConfig.Mode.BOTH) {
                CompassHudConfig.CompassSettings config = CompassHudConfig.get().compassSettings;
                int compassX = config.compassX;
                int compassY = config.compassY;
                float scale = config.scale;

                ItemStack compassStack = new ItemStack(Items.COMPASS);

                BlockPos pos = new BlockPos(0, 0, 0);
                RegistryKey<World> dimension = World.OVERWORLD;

                if (client.player.getWorld().getRegistryKey() == World.OVERWORLD) {
                    pos = client.world.getSpawnPos();
                }
                if (client.player.getWorld().getRegistryKey() == World.NETHER) {
                    dimension = World.NETHER;
                }
                if (client.player.getWorld().getRegistryKey() == World.END) {
                    dimension = World.END;
                }
                LodestoneTrackerComponent lodestoneTrackerComponent = new LodestoneTrackerComponent(Optional.of(GlobalPos.create(dimension, pos)), false);
                compassStack.set(DataComponentTypes.LODESTONE_TRACKER, lodestoneTrackerComponent);

                MatrixStack matrixStack = drawContext.getMatrices();
                matrixStack.push();
                matrixStack.translate(compassX, compassY, 0);
                matrixStack.scale(scale, scale, scale);
                drawContext.drawItem(compassStack, 0, 0);
                matrixStack.pop();

                String textString = String.format("X: %.0f, Y: %.0f, Z: %.0f", client.player.getX(), client.player.getY(), client.player.getZ());
                Text textText = Text.literal(textString);
                int positionX = config.textX;
                int positionY = config.textY;
                int color = config.color;

                Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
                VertexConsumerProvider vertexConsumers = client.getBufferBuilders().getOutlineVertexConsumers();
                TextRenderer.TextLayerType layerType = TextRenderer.TextLayerType.NORMAL;
                int backgroundColor = config.backgroundColor;
                int light = config.light;

                client.textRenderer.draw(textText, positionX, positionY, color, true, matrix4f, vertexConsumers, layerType, backgroundColor, light);
            }
            if (mode == CompassHudConfig.Mode.CLOCK || mode == CompassHudConfig.Mode.BOTH) {
                CompassHudConfig.ClockSettings config = CompassHudConfig.get().clockSettings;
                int compassX = config.compassX;
                int compassY = config.compassY;
                float scale = config.scale;

                ItemStack compassStack = new ItemStack(Items.CLOCK);

                MatrixStack matrixStack = drawContext.getMatrices();
                matrixStack.push();
                matrixStack.translate(compassX, compassY, 0);
                matrixStack.scale(scale, scale, scale);
                drawContext.drawItem(compassStack, 0, 0);
                matrixStack.pop();

                long dayTime = client.world.getTimeOfDay() % 24000L;
                long days = client.world.getTimeOfDay() / 24000L;
                long hours = dayTime / 1000L;
                long minutes = (dayTime % 1000L) * 60 / 1000;


                Text time = Text.of(config.timeText).copy().append(Text.translatable("hud.compasshud.time", hours, minutes));
                Text day = Text.of(config.dayText).copy().append(Text.translatable("hud.compasshud.day", days));
                int color = config.color;

                Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
                VertexConsumerProvider vertexConsumers = client.getBufferBuilders().getOutlineVertexConsumers();
                TextRenderer.TextLayerType layerType = TextRenderer.TextLayerType.NORMAL;
                int backgroundColor = config.backgroundColor;
                int light = config.light;

                client.textRenderer.draw(time, config.timeX, config.timeY, color, true, matrix4f, vertexConsumers, layerType, backgroundColor, light);
                client.textRenderer.draw(day, config.dayX, config.dayY, color, true, matrix4f, vertexConsumers, layerType, backgroundColor, light);
            }
        }
    }
}
