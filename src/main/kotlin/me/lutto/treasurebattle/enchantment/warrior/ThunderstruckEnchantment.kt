package me.lutto.treasurebattle.enchantment.warrior

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent
import me.lutto.treasurebattle.TreasureBattle
import me.lutto.treasurebattle.enchantment.CustomEnchantmentItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import kotlin.random.Random

class ThunderstruckEnchantment(private val treasureBattle: TreasureBattle) : CustomEnchantmentItem(
    "thunderstruck",
    "Thunderstruck",
    EnchantmentTarget.TRIDENT) {

    init {
        treasureBattle.itemManager.registerEnchantmentItem(this)
    }

    @EventHandler
    fun onPlayerLaunchProjectile(event: PlayerLaunchProjectileEvent) {
        if (event.projectile.type != EntityType.TRIDENT) return

        val item: ItemStack = event.itemStack
        if (!treasureBattle.itemManager.isItem(item, this.id)) return

        val randomInt: Int = Random.nextInt(1, 100)
        if (randomInt < 80) return

        event.projectile.setMetadata("shouldStrikeLightning", FixedMetadataValue(treasureBattle, true))
    }

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        if (event.entity.type != EntityType.TRIDENT) return
        if (event.hitEntity !is LivingEntity) return
        val entity: LivingEntity = event.hitEntity as LivingEntity
        val projectile: Projectile = event.entity

        if (projectile.hasMetadata("shouldStrikeLightning")) return
        if (projectile.getMetadata("shouldStrikeLightning") == FixedMetadataValue(treasureBattle, false)) return

        projectile.setMetadata("shouldStrikeLightning", FixedMetadataValue(treasureBattle, false))
        entity.world.strikeLightningEffect(entity.location)
        if (entity.health + 2.0 < 0) {
            entity.health = 0.0
            return
        }
        entity.health -= 2.0
    }

    override fun getMaterial(): Material = Material.TRIDENT
    override fun getPairedEnchantment(): Enchantment = Enchantment.LOYALTY
    override fun getItemName(): Component = MiniMessage.miniMessage().deserialize("<gradient:#1d4ed8:#0284c7>ᴛʜᴜɴᴅᴇʀѕᴛʀᴜᴄᴋ ᴛʀɪᴅᴇɴᴛ").decoration(TextDecoration.ITALIC, false)
    override fun getItemLore(): Component = MiniMessage.miniMessage().deserialize("Zap attack!").color(NamedTextColor.DARK_GRAY)

}