package com.kljiana.compasshud.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.text.Text;

@Config(name = "compasshud")
public class CompassHudConfig implements ConfigData {
    public enum Mode {
        COMPASS,
        CLOCK,
        BOTH
    }

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Mode mode = Mode.COMPASS;

    @ConfigEntry.Gui.CollapsibleObject
    public CompassSettings compassSettings = new CompassSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public ClockSettings clockSettings = new ClockSettings();

    public static class CompassSettings {
        @Comment
        public float scale = 2;

        @Comment
        public int compassX = 10;

        @Comment
        public int compassY = 10;

        @Comment
        public int textX = 10;

        @Comment
        public int textY = 50;


        @ConfigEntry.ColorPicker
        public int color = 0xFFFFFF;

        @ConfigEntry.ColorPicker(allowAlpha = true)
        public int backgroundColor = 0x30000000;

        @ConfigEntry.ColorPicker
        public int light = 0xFFFFFF;
    }

    public static class ClockSettings {
        @Comment
        public float scale = 2;

        @Comment
        public int compassX = 10;

        @Comment
        public int compassY = 10;

        @Comment
        public int timeX = 10;

        @Comment
        public int timeY = 50;

        @Comment
        public String timeText = "游玩时间：";

        @Comment
        public int dayX = 10;

        @Comment
        public int dayY = 59;

        @Comment
        public String dayText = "总计天数：";

        @ConfigEntry.ColorPicker
        public int color = 0xFFFFFF;

        @ConfigEntry.ColorPicker(allowAlpha = true)
        public int backgroundColor = 0x30000000;

        @ConfigEntry.ColorPicker
        public int light = 0xFFFFFF;
    }

    public static void register() {
        AutoConfig.register(CompassHudConfig.class, Toml4jConfigSerializer::new);
    }

    public static CompassHudConfig get() {
        return AutoConfig.getConfigHolder(CompassHudConfig.class).getConfig();
    }
}
