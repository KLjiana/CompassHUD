package com.kljiana.compasshud;

import com.kljiana.compasshud.config.CompassHudConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompassHUD implements ModInitializer {
    public static final String MOD_ID = "CompassHUD";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        CompassHudConfig.register();
    }
}
