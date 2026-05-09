package daxz.dev.haulover.Skills.Farming.FarmingTools;

import daxz.dev.haulover.Haulover;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.PotionContents;
import io.papermc.paper.datacomponent.item.SwingAnimation;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.DataComponentTypeKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

import static io.papermc.paper.registry.keys.DataComponentTypeKeys.POTION_CONTENTS;

public class WateringCan {

    public static final String ID = "basic_watering_can";
    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    public static final NamespacedKey stopInteraction = new NamespacedKey(Haulover.getInstance(), "flag-no_interactions");


    public ItemStack createItem() {

        Material material = Material.LINGERING_POTION;
        ItemStack item = ItemStack.of(material);


//        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Basic Watering Can", NamedTextColor.WHITE));
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Basic Watering Can", NamedTextColor.WHITE));


        item.setData(DataComponentTypes.LORE, ItemLore.lore()
                .addLine(Component.text("A basic rusty watering can.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                        .addLine(Component.text(" "))
                        .addLine(Component.text("\uD83C\uDF31 ", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("Farming Tool")))
                .build())
        ;
//        item.setData(DataComponentTypes.ITEM_MODEL, Key.key("minecraft:lingering_potion"));
        item.setData(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().customColor(Color.GRAY));

        var registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.DATA_COMPONENT_TYPE);
        item.setData(DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay.tooltipDisplay().hiddenComponents(Set.of(registry.get(POTION_CONTENTS))));


//        item.setData(DataComponentTypes.SWING_ANIMATION, SwingAnimation.swingAnimation().type(SwingAnimation.Animation.STAB).duration(1));


        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
            pdc.set(stopInteraction, PersistentDataType.BOOLEAN, true);
        });
        return item;

    }


}
