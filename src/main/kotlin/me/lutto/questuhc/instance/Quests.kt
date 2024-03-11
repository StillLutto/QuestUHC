package me.lutto.questuhc.instance

import org.bukkit.entity.EntityType
import java.util.*
import kotlin.random.Random

class Quests {

    private val questsGiven: MutableSet<Pair<EntityType, Int>> = HashSet()
    private val playerQuests: MutableMap<UUID, Pair<EntityType, Int>> = mutableMapOf()

    fun getRandomQuest(uuid: UUID): Pair<EntityType, Int> {

        val killList: MutableList<Pair<EntityType, Int>> = mutableListOf()
        killList.add(Pair(EntityType.SHEEP, 5))
        killList.add(Pair(EntityType.COW, 3))

        val totalList: List<Pair<EntityType, Int>> = killList
        var randomQuest: Pair<EntityType, Int> = totalList[Random.nextInt(0, totalList.size)]

        while (!questsGiven.contains(randomQuest)) {
            randomQuest = totalList[Random.nextInt(0, totalList.size)]
            questsGiven.add(randomQuest)
        }

        return randomQuest

    }

    fun setPlayerQuest(uuid: UUID, quest: Pair<EntityType, Int>) {
        playerQuests[uuid] = quest
    }

    fun getPlayerQuests(): MutableMap<UUID, Pair<EntityType, Int>>  = playerQuests

}