package utils;

import main.Game;

public class Constants {

    public static final float GRAVITY = 0.1f * Game.SCALE;
    public static final int ANI_SPEED = 7;

    public static class ObjectConstants {
        
        public static final int RED_POTION = 0 ;
        public static final int BLUE_POTION = 1 ;
        public static final int BARREL = 2 ;
        public static final int BOX = 3 ;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;

        public static final int RED_POTION_VALUE = 15 ;
        public static final int BLUE_POTION_VALUE = 10 ;

        public static final int CONTAINER_WIDTH_D = 40 ;
        public static final int CONTAINER_HEIGHT_D = 30 ;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_D);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_D);

        public static final int POTION_WIDTH_D = 12 ;
        public static final int POTION_HEIGHT_D = 16 ;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_D);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_D);
        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * Game.SCALE);
        public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * Game.SCALE);

        public static int getSprite(int object_type) {
            switch (object_type) {
                case RED_POTION, BLUE_POTION:
                    return 7;
                case BARREL, BOX:
                    return 8;
                case CANNON_LEFT, CANNON_RIGHT:
                    return 7;
            }
            return 1;
        }
    }
    public static class EnemyConstants {
        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_D = 72;
        public static final int CRABBY_HEIGHT_D = 32;

        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_D * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_D * Game.SCALE);

        public static final int CRABBY_OFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_OFFSET_Y = (int) (9 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case CRABBY:
                    switch (enemy_state) {
                        case IDLE:
                            return 9;
                        case RUNNING:
                            return 6;
                        case HIT:
                            return 4;
                        case ATTACK:
                            return 7;
                        case DEAD:
                            return 5;
                        default:
                            throw new IllegalArgumentException("animation number " + enemy_state + " not found");
                    }
            }
            return 0;
        }

        public static int getMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int getEnemyDmg(int enemy_type) {
            switch (enemy_type) {
                case CRABBY:
                    return 15;
                default:
                    return 1;
            }
        }
    }

    public static class Environment {
        public static final int BIG_CLOUD_DEF_WIDTH = 448;
        public static final int BIG_CLOUD_DEF_HEIGHT = 101;
        public static final int SMALL_CLOUD_DEF_WIDTH = 74;
        public static final int SMALL_CLOUD_DEF_HEIGHT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_DEF_WIDTH * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_DEF_HEIGHT * Game.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_DEF_WIDTH * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_DEF_HEIGHT * Game.SCALE);
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);

        }

        public static class URMButtons {
            public static final int URM_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int DEFAULT_WIDTH = 28;
            public static final int DEFAULT_HEIGHT = 44;
            public static final int DEFAULT_SLIDER_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (DEFAULT_SLIDER_WIDTH * Game.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    /**
     * class that contains all the animation coordinates
     */
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 4;
        public static final int DEAD = 6;

        /**
         * given an action it returns the corrisponding number of sprites
         */
        public static int GetSpriteAmount(int PlayerAction) {
            switch (PlayerAction) {
                case IDLE:
                    return 5;
                case RUNNING:
                    return 6;
                case FALLING:
                    return 1;
                case HIT:
                    return 4;
                case JUMPING:
                case ATTACK_1:
                    return 3;
                case DEAD:
                    return 8;
                default:
                    throw new IllegalArgumentException("animation number " + PlayerAction + " not found");
            }
        }
    }
}
