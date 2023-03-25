package nl.thebathduck.remakephone.enums.grinding;

import lombok.Getter;
import org.bukkit.Material;

public enum GrindingPickaxeSkin {
    WOOD(Material.WOOD_PICKAXE, "Wooden Pickaxe", "grindingskin.wood_pickaxe", "wood_pickaxe"),
    WOOD_REFINED(Material.STONE_PICKAXE, "Refinded Wooden Pickaxe", "grindingskin.wood_pickaxe_refined", "wood_pickaxe_refined"),
    STONE_REFINED(Material.STONE_PICKAXE, "Refinded Stone Pickaxe", "grindingskin.stone_pickaxe_refined", "stone_pickaxe_refined"),
    CACTUS(Material.STONE_PICKAXE, "Cactus Pickaxe", "grindingskin.cactus", "cactus_pickaxe"),
    CACTUS_REFINED(Material.STONE_PICKAXE, "Refined Cactus Pickaxe", "grindingskin.cactus_refined", "cactus_pickaxe_refined"),
    COPPER(Material.STONE_PICKAXE, "Copper Pickaxe", "grindingskin.copper", "copper_pickaxe"),
    COPPER_REFINED(Material.STONE_PICKAXE, "Refined Copper Pickaxe", "grindingskin.copper_refined", "copper_pickaxe_refined"),
    DARKPRISMARINE(Material.STONE_PICKAXE, "Dark Prismarine Pickaxe", "grindingskin.darkprismarine_pickaxe", "darkprismarine_pickaxe"),
    DARKPRISMARINE_REFINED(Material.STONE_PICKAXE, "Refined Dark Prismarine Pickaxe", "grindingskin.darkprismarine_pickaxe_refined", "darkprismarine_pickaxe_refined"),
    DIAMOND_REFINED(Material.STONE_PICKAXE, "Refined Diamond Pickaxe", "grindingskin.diamond_pickaxe_refined", "diamond_pickaxe_refined"),
    EMERALD(Material.STONE_PICKAXE, "Emerald Pickaxe", "grindinskin.emerald_pickaxe", "emerald_pickaxe"),
    EMERALD_REFINED(Material.STONE_PICKAXE, "Refined Emerald Pickaxe", "grindingskin.emerald_pickaxe_refined", "emerald_pickaxe_refined"),
    GOLD(Material.STONE_PICKAXE, "Gold Pickaxe", "grindingskin.gold_pickaxe", "gold_pickaxe"),
    GOLD_REFINED(Material.STONE_PICKAXE, "Refined Gold Pickaxe", "grindingskin.gold_pickaxe_refined", "gold_pickaxe_refined"),
    IRON_REFINED(Material.STONE_PICKAXE, "Refined Iron Pickaxe", "grindingskin.iron_pickaxe_refined", "iron_pickaxe_refined"),
    LAVA(Material.STONE_PICKAXE, "Lava Pickaxe", "grindingskin.lava_pickaxe", "lava_pickaxe"),
    LAVA_REFINED(Material.STONE_PICKAXE, "Refined Lava Pickaxe", "grindingskin.lava_pickaxe_pickaxe", "lava_pickaxe_refined"),
    MAGMA(Material.STONE_PICKAXE, "Magma Pickaxe", "grindingskin.magma_pickaxe", "magma_pickaxe"),
    MAGMA_REFINED(Material.STONE_PICKAXE, "Refined Magma Pickaxe", "grindingskin.magma_pickaxe", "magma_pickaxe"),
    MONDRIAAN(Material.STONE_PICKAXE, "Mondriaan Pickaxe", "grindingskin.mondriaan_pickaxe", "mondriaan_pickaxe"),
    MONDRIAAN_REFINED(Material.STONE_PICKAXE, "Refined Mondriaan Pickaxe", "grindingskin.mondriaan_pickaxe_refined", "mondriaan_pickaxe_refined"),
    MORGANITE(Material.STONE_PICKAXE, "Morganite Pickaxe", "grindingskin.morganite_pickaxe", "morganite_pickaxe"),
    MORGANITE_REFINED(Material.STONE_PICKAXE, "Refined Morganite Pickaxe", "grindingskin.morganite_pickaxe_refined", "morganite_pickaxe"),
    NETHERPORTAL(Material.STONE_PICKAXE, "Netherportal Pickaxe", "grindingskin.netherportal_pickaxe", "netherportal_pickaxe"),
    NETHERPORTAL_REFINED(Material.STONE_PICKAXE, "Refined Nether Pickaxe", "grindingskin.netherportal_pickaxe_refined", "netherportal_pickaxe_refined"),
    OBSIDIAN(Material.STONE_PICKAXE, "Obsidian Pickaxe", "grindingskin.obsidian_pickaxe", "obsidian_pickaxe"),
    OBSIDIAN_REFINED(Material.STONE_PICKAXE, "Refined Obsidian Pickaxe", "grindingskin.obsidian_pickaxe_refined", "obsidian_pickaxe_refined"),
    REAL_DIAMOND(Material.STONE_PICKAXE, "Real Diamond Pickaxe", "grindingskin.real_diamond_pickaxe", "real_diamond_pickaxe"),
    REAL_DIAMOND_REFINED(Material.STONE_PICKAXE, "Refined Real Diamond Pickaxe", "grindingskin.real_diamond_pickaxe_refined", "real_diamond_pickaxe_refined"),
    RUBY(Material.STONE_PICKAXE, "Ruby Pickaxe", "grindingskin.ruby_pickaxe", "ruby_pickaxe"),
    RUBY_REFINED(Material.STONE_PICKAXE, "Refined Ruby Pickaxe", "grindingskin.ruby_pickaxe_refined", "ruby_pickaxe_refined"),
    SAPPHIRE(Material.STONE_PICKAXE, "Sapphire Pickaxe", "grindingskin.sapphire_pickaxe", "sapphire_pickaxe"),
    SAPPHIRE_REFINED(Material.STONE_PICKAXE, "Refined Sapphire Pickaxe", "grindingskin.sapphire_pickaxe_refined", "sapphire_pickaxe_refined"),
    SILVER(Material.STONE_PICKAXE, "Silver Pickaxe", "grindingskin.silver_pickaxe", "silver_pickaxe"),
    SILVER_REFINED(Material.STONE_PICKAXE, "Refined Silver Pickaxe", "grindingskin.silver_pickaxe_refined", "silver_pickaxe_refined"),
    WATER(Material.STONE_PICKAXE, "Water Pickaxe", "grindingskin.water_pickaxe", "water_pickaxe"),
    WATER_REFINED(Material.STONE_PICKAXE, "Refined Water Pickaxe", "grindingskin.water_pickaxe_refined", "water_pickaxe_refined");





    private @Getter Material material;
    private @Getter String name;
    private @Getter String permission;
    private @Getter String nbt;
    GrindingPickaxeSkin(Material material, String name, String permission, String nbt) {
        this.material = material;
        this.name = name;
        this.permission = permission;
        this.nbt = nbt;
    }

}
