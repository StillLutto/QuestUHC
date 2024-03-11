package me.lutto.questuhc.instance

import me.lutto.questuhc.enums.quests.QuestList
import java.util.*

class Quests {

    private val questsGiven: MutableSet<QuestList> = HashSet()
    private val playerQuests: MutableMap<UUID, QuestList> = mutableMapOf()

    fun getRandomQuest(): QuestList {

        var randomQuest: QuestList = QuestList.entries.random()

        while (!questsGiven.contains(randomQuest)) {
            randomQuest = QuestList.entries.random()
            questsGiven.add(randomQuest)
        }

        return randomQuest

    }

    fun setPlayerQuest(uuid: UUID, quest: QuestList) {
        playerQuests[uuid] = quest
    }

    fun getPlayerQuest(uuid: UUID): QuestList? = playerQuests[uuid]

}