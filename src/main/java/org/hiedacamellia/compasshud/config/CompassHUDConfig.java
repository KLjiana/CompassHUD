package org.hiedacamellia.compasshud.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.hiedacamellia.compasshud.CompassHUD;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = CompassHUD.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CompassHUDConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<String> MODE = BUILDER
            .comment("The mode of the compass (COMPASS/CLOCK/BOTH)")
            .define("mode", "COMPASS");

    public static final ModConfigSpec.IntValue COMPASS_X = BUILDER
            .comment("The x position of the compass")
            .defineInRange("compassX", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue COMPASS_Y = BUILDER
            .comment("The y position of the compass")
            .defineInRange("compassY", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue  COMPASS_COLOR = BUILDER
            .comment("The color of the compass text")
            .defineInRange("compassCOLOR", -1, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue POS_X = BUILDER
            .comment("The x position of the player position")
            .defineInRange("posX", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue POS_Y = BUILDER
            .comment("The y position of the player position")
            .defineInRange("posY", 36, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue CLOCK_X = BUILDER
            .comment("The x position of the clock")
            .defineInRange("clockX", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue CLOCK_Y = BUILDER
            .comment("The y position of the clock")
            .defineInRange("clockY", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue TIME_X = BUILDER
            .comment("The x position of the time")
            .defineInRange("timeX", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue TIME_Y = BUILDER
            .comment("The y position of the time")
            .defineInRange("timeY", 36, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue DAY_X = BUILDER
            .comment("The x position of the day")
            .defineInRange("dayX", 10, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue DAY_Y = BUILDER
            .comment("The y position of the day")
            .defineInRange("dayY", 52, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue  CLOCK_COLOR = BUILDER
            .comment("The color of the clock text")
            .defineInRange("clockCOLOR", -1, Integer.MIN_VALUE, Integer.MAX_VALUE);


    public static final ModConfigSpec SPEC = BUILDER.build();

    public static String mode;
    public static int compassX;
    public static int compassY;
    public static int compassColor;
    public static int posX;
    public static int posY;
    public static int clockX;
    public static int clockY;
    public static int timeX;
    public static int timeY;
    public static int dayX;
    public static int dayY;
    public static int clockColor;


    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        // Load the config values
        try {
            mode = MODE.get();
            compassX = COMPASS_X.get();
            compassY = COMPASS_Y.get();
            compassColor = COMPASS_COLOR.get();
            posX = POS_X.get();
            posY = POS_Y.get();
            clockX = CLOCK_X.get();
            clockY = CLOCK_Y.get();
            timeX = TIME_X.get();
            timeY = TIME_Y.get();
            dayX = DAY_X.get();
            dayY = DAY_Y.get();
            clockColor = CLOCK_COLOR.get();
        } catch (Exception e) {
            // Failed to parse a value, log an error
            CompassHUD.LOGGER.error("Failed to parse a config value", e);
        }


    }
}
