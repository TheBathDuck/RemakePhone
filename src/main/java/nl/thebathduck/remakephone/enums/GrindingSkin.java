package nl.thebathduck.remakephone.enums;

import lombok.Getter;

public enum GrindingSkin {
    WOOD_REFINED("Refinded Wooden Pickaxe", "grindingskin.wood_pickaxe_refined", "wood_pickaxe_refined"),
    STONE_REFINED("Refinded Stone Pickaxe", "grindingskin.stone_pickaxe_refined", "stone_pickaxe_refined"),
    CACTUS("Cactus Pickaxe", "grindingskin.cactus", "cactus_pickaxe"),
    CACTUS_REFINED("Refined Cactus Pickaxe", "grindingskin.cactus_refined", "cactus_pickaxe_refined"),
    COPPER("Copper Pickaxe", "grindingskin.copper", "copper_pickaxe"),
    COPPER_REFINED("Refined Copper Pickaxe", "grindingskin.copper_refined", "copper_pickaxe_refined"),
    DARKPRISMARINE("Dark Prismarine Pickaxe", "grindingskin.darkprismarine_pickaxe", "darkprismarine_pickaxe"),
    DARKPRISMARINE_REFINED("Refined Dark Prismarine Pickaxe", "grindingskin.darkprismarine_pickaxe_refined", "darkprismarine_pickaxe_refined"),
    DIAMOND_REFINED("Refined Diamond Pickaxe", "grindingskin.diamond_pickaxe_refined", "diamond_pickaxe_refined"),
    EMERALD("Emerald Pickaxe", "grindinskin.emerald_pickaxe", "emerald_pickaxe"),
    EMERALD_REFINED("Refined Emerald Pickaxe", "grindingskin.emerald_pickaxe_refined", "emerald_pickaxe_refined"),
    GOLD("Gold Pickaxe", "grindingskin.gold_pickaxe", "gold_pickaxe"),
    GOLD_REFINED("Refined Gold Pickaxe", "grindingskin.gold_pickaxe_refined", "gold_pickaxe_refined"),
    IRON_REFINED("Refined Iron Pickaxe", "grindingskin.iron_pickaxe_refined", "iron_pickaxe_refined"),
    LAVA("Lava Pickaxe", "grindingskin.lava_pickaxe", "lava_pickaxe"),
    LAVA_REFINED("Refined Lava Pickaxe", "grindingskin.lava_pickaxe_pickaxe", "lava_pickaxe_refined"),
    MAGMA("Magma Pickaxe", "grindingskin.magma_pickaxe", "magma_pickaxe"),
    MAGMA_REFINED("Refined Magma Pickaxe", "grindingskin.magma_pickaxe", "magma_pickaxe"),
    MONDRIAAN("Mondriaan Pickaxe", "grindingskin.mondriaan_pickaxe", "mondriaan_pickaxe"),
    MONDRIAAN_REFINED("Refined Mondriaan Pickaxe", "grindingskin.mondriaan_pickaxe_refined", "mondriaan_pickaxe_refined"),
    MORGANITE("Morganite Pickaxe", "grindingskin.morganite_pickaxe", "morganite_pickaxe"),
    MORGANITE_REFINED("Refined Morganite Pickaxe", "grindingskin.morganite_pickaxe_refined", "morganite_pickaxe"),
    NETHERPORTAL("Netherportal Pickaxe", "grindingskin.netherportal_pickaxe", "netherportal_pickaxe"),
    NETHERPORTAL_REFINED("Refined Nether Pickaxe", "grindingskin.netherportal_pickaxe_refined", "netherportal_pickaxe_refined"),
    OBSIDIAN("Obsidian Pickaxe", "grindingskin.obsidian_pickaxe", "obsidian_pickaxe"),
    OBSIDIAN_REFINED("Refined Obsidian Pickaxe", "grindingskin.obsidian_pickaxe_refined", "obsidian_pickaxe_refined"),
    REAL_DIAMOND("Real Diamond Pickaxe", "grindingskin.real_diamond_pickaxe", "real_diamond_pickaxe"),
    REAL_DIAMOND_REFINED("Refined Real Diamond Pickaxe", "grindingskin.real_diamond_pickaxe_refined", "real_diamond_pickaxe_refined"),
    RUBY("Ruby Pickaxe", "grindingskin.ruby_pickaxe", "ruby_pickaxe"),
    RUBY_REFINED("Refined Ruby Pickaxe", "grindingskin.ruby_pickaxe_refined", "ruby_pickaxe_refined"),
    SAPPHIRE("Sapphire Pickaxe", "grindingskin.sapphire_pickaxe", "sapphire_pickaxe"),
    SAPPHIRE_REFINED("Refined Sapphire Pickaxe", "grindingskin.sapphire_pickaxe_refined", "sapphire_pickaxe_refined"),
    SILVER("Silver Pickaxe", "grindingskin.silver_pickaxe", "silver_pickaxe"),
    SILVER_REFINED("Refined Silver Pickaxe", "grindingskin.silver_pickaxe_refined", "silver_pickaxe_refined"),
    WATER("Water Pickaxe", "grindingskin.water_pickaxe", "water_pickaxe"),
    WATER_REFINED("Refined Water Pickaxe", "grindingskin.water_pickaxe_refined", "water_pickaxe_refined");





    private @Getter String name;
    private @Getter String permission;
    private @Getter String nbt;
    GrindingSkin(String name, String permission, String nbt) {
        this.name = name;
        this.permission = permission;
        this.nbt = nbt;
    }

}
