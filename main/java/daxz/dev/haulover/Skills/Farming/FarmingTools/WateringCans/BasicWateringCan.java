package daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Utilities.LoreTool;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.*;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
@SuppressWarnings("UnstableApiUsage")
public class BasicWateringCan {

    public static final String ID = "basic_watering_can";
    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    public static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");
    public static final NamespacedKey wateringCanCapacity = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can-capacity");

    //flags
    public static final NamespacedKey stopConsume = new NamespacedKey(Haulover.getInstance(), "flag-no_consume");

    public ItemStack createItem() {

        Material material = Material.LIGHT_GRAY_DYE;
        ItemStack item = ItemStack.of(material);

        float wateringCanSpeed = 3.0F;
        int wateringCanMaxCapacity = 100;


        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Basic Watering Can", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().animation(ItemUseAnimation.BOW).consumeSeconds(wateringCanSpeed).sound(Key.key("")).hasConsumeParticles(false));
        item.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(1f).build());

        LoreTool.lore(item,
        "<dark_gray> A basic rusty watering can.",
              "<dark_gray><st>                                          </st>",
              "<gray>  Speed: <green>" + wateringCanSpeed + "s",
              "<gray>  Capacity: <aqua>0<dark_aqua>/<dark_aqua>" + wateringCanMaxCapacity,
              "<dark_gray><st>                                          </st>",
              "<white>\uD83C\uDF31 <bold>Farming Tool"
        );


        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
            pdc.set(stopConsume, PersistentDataType.BOOLEAN, true);
            pdc.set(wateringCanID, PersistentDataType.BOOLEAN, true);
            pdc.set(wateringCanCapacity, PersistentDataType.FLOAT, 0f);
        });
        return item;

    }


}
