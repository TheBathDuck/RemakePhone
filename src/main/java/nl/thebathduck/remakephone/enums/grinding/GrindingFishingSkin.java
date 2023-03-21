package nl.thebathduck.remakephone.enums.grinding;

import lombok.Getter;

public enum GrindingFishingSkin {
    BLUE("Fishing Rod Blue", "grindingskin.fishing_rod_blue", "fishing_rod_blue"),
    CACTUS("Fishing Rod Cactus", "grindingskin.fishing_rod_cactus", "fishing_rod_cactus"),
    LIME("Fishing Rod Lime", "grindingskin.fishing_rod_lime", "fishing_rod_lime"),
    MAGMA("Fishing Rod Magma", "grindingskin.fishing_rod_magma", "fishing_rod_magma"),
    MONDRIAAN("Fishing Rod Mondriaan", "grindingskin.fishing_rod_mondriaan", "fishing_rod_mondriaan"),
    NETHER("Fishing Rod Nether", "grindingskin.fishing_rod_nether", "fishing_rod_nether");


    private @Getter String name;
    private @Getter String permission;
    private @Getter String nbt;
    GrindingFishingSkin(String name, String permission, String nbt) {
        this.name = name;
        this.permission = permission;
        this.nbt = nbt;
    }


}
