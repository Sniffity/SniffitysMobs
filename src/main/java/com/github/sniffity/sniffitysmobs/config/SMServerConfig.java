package com.github.sniffity.sniffitysmobs.config;

import com.github.sniffity.sniffitysmobs.SniffitysMobs;
import net.minecraftforge.common.ForgeConfigSpec;


/**
 * Sniffity's Mobs - Class: SMCommonConfig <br></br?>
 *
 * Acknowledgements: The following class was developed after studying how Mowzie's Mobs handles
 * their own config.
 */

public final class SMServerConfig {

    public static ForgeConfigSpec SERVER_CONFIG;
    public static final Server SERVER;
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    private static final String LANG_PREFIX = "config." + SniffitysMobs.MODID + ".";

    static {
        SERVER = new Server(SERVER_BUILDER);
        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static class GeneralConfig {

        GeneralConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            builder.pop();
        }
    }

    public static class Server {
        private Server(final ForgeConfigSpec.Builder builder) {
            GENERAL = new GeneralConfig(builder);
            ENTITIES = new Entities(builder);
        }

        public final GeneralConfig GENERAL;
        public final Entities ENTITIES;
    }

    public static class Entities {
        Entities(final ForgeConfigSpec.Builder builder) {
            builder.push("mobs");
            WEREWOLF = new Werewolf(builder);
            builder.pop();
        }

        public final Werewolf WEREWOLF;

        public static class Werewolf {
            Werewolf(final ForgeConfigSpec.Builder builder) {
                builder.push("werewolf");
                this.enableWerewolf = builder.comment("Whether to enable werewolves or not")
                        .translation(LANG_PREFIX + "enable_werewolf")
                        .define("enable_werewolf", true);
                this.werewolfSpawnWeight = builder.comment("Spawn Weight for werewolves")
                        .translation(LANG_PREFIX + "spawn_weight_werewolf")
                        .defineInRange("spawn_weight_werewolf", 140,0,150);
                builder.pop();
            }

            public final ForgeConfigSpec.BooleanValue enableWerewolf;
            public final ForgeConfigSpec.IntValue werewolfSpawnWeight;
        }
    }
}