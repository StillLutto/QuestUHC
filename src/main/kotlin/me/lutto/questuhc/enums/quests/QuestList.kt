package me.lutto.questuhc.enums.quests

import org.bukkit.Material
import org.bukkit.entity.EntityType

enum class QuestList(val questName: String, val amount: Int, val type: QuestType, val typeToCheck: Any) {

    COW("Cows", 4, QuestType.KILL, EntityType.COW),
    SHEEP("Sheep", 3, QuestType.KILL, EntityType.SHEEP),
    BASALT("Basalt", 34, QuestType.MINE, Material.BASALT),
    BLUE_BED("Blue Bed", 1, QuestType.MINE, Material.BLUE_BED),
    CARROT("Carrots", 3, QuestType.EAT, Material.CARROT),
    DRIED_KELP("Dried Kelp", 4, QuestType.EAT, Material.DRIED_KELP),
    DAYLIGHT_DETECTOR("Daylight Detectors", 2, QuestType.CRAFT, Material.DAYLIGHT_DETECTOR),
    PUMPKIN_PIE("Pumpkin Pie", 1, QuestType.CRAFT, Material.PUMPKIN_PIE)

}
