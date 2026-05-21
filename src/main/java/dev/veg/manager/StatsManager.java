package dev.veg.manager;

public class StatsManager {

    private static int illegalCorrected;
    private static int shulkersScanned;
    private static int enderChestsScanned;

    public static void addIllegalCorrected() {
        illegalCorrected++;
    }

    public static void addShulkerScanned() {
        shulkersScanned++;
    }

    public static void addEnderChestScanned() {
        enderChestsScanned++;
    }

    public static int getIllegalCorrected() {
        return illegalCorrected;
    }

    public static int getShulkersScanned() {
        return shulkersScanned;
    }

    public static int getEnderChestsScanned() {
        return enderChestsScanned;
    }
}