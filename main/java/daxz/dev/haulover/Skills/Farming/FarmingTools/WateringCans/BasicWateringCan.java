package daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans;

import daxz.dev.haulover.Haulover;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.*;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

import static io.papermc.paper.registry.keys.DataComponentTypeKeys.POTION_CONTENTS;

public class BasicWateringCan {

    public static final String ID = "basic_watering_can";
    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    public static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");

    //flags
    public static final NamespacedKey stopInteraction = new NamespacedKey(Haulover.getInstance(), "flag-no_interactions");


    public ItemStack createItem() {

        Material material = Material.CYAN_DYE;
        ItemStack item = ItemStack.of(material);

        float wateringCanSpeed = 3.0F;


        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Basic Watering Can", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().animation(ItemUseAnimation.BOW).consumeSeconds(wateringCanSpeed));


        item.setData(DataComponentTypes.LORE, ItemLore.lore()
                .addLine(Component.text("A basic rusty watering can.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                        .addLine(Component.text(" "))
                        .addLine(Component.text("\uD83C\uDF31 ", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("Farming Tool")))
                .build())
        ;
        item.setData(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().customColor(Color.GRAY));

        var registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DATA_COMPONENT_TYPE);
        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay().hiddenComponents(Set.of(registry.get(POTION_CONTENTS))));




        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
//            pdc.set(stopInteraction, PersistentDataType.BOOLEAN, true);
        });
        return item;

    }


}
