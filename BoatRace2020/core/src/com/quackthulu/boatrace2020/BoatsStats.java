package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.TimedTextureTemplate;

public class BoatsStats {
        public static final int TANK_BOAT = 0;
        public static final int SPEED_BOAT = 1;
        public static final int VIKING_BOAT = 2;
        public static final int JETSKI_BOAT = 3;
        public static final int NORMAL_BOAT = 4;

        private static final BoatStats[] BOATS_STATS = new BoatStats[] {
                new BoatStats("Tank", new TimedTextureTemplate[] {new TimedTextureTemplate(Assets.tankBoatTexture)}, null, true, 8, 2, 1.0f, 1.5f, 1.3f),
                new BoatStats("Speed Boat", new TimedTextureTemplate[] {new TimedTextureTemplate(Assets.speedBoatTexture)}, null, true, 3, 1, 2.2f, 0.6f, 0.8f),
                new BoatStats("Viking Boat", new TimedTextureTemplate[] {new TimedTextureTemplate(Assets.vikingBoatTexture)}, null, true, 4, 1, 1.5f, 1.0f, 1.05f),
                new BoatStats("Jet Ski", new TimedTextureTemplate[] {new TimedTextureTemplate(Assets.jetSkiTexture)}, null, true, 2, 1, 3.6f, 0.4f, 0.6f),
                new BoatStats("Sail Boat", new TimedTextureTemplate[] {new TimedTextureTemplate(Assets.normalBoatTexture)}, null, true, 5, 1, 1.3f, 1.0f, 1.0f)
        };

        public static int numOfBoats() {
                return BOATS_STATS.length;
        }

        public static BoatStats getBoatStats(int boatNumber) {
                if (boatNumber < BOATS_STATS.length) return BOATS_STATS[boatNumber];
                return null;
        }
}
