package daxz.dev.haulover.Skills.Farming.FarmingTools;

import daxz.dev.haulover.Haulover;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class WateringCan {

    public static final String ID = "basic_watering_can";
    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");


    public ItemStack createItem() {

        Material material = Material.PLAYER_HEAD;


        ItemStack item = ItemStack.of(material);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Basic Watering Can", NamedTextColor.WHITE));
        item.setData(DataComponentTypes.LORE, ItemLore.lore()
                .addLine(Component.text("A basic rusty watering can.", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                        .addLine(Component.text(" "))
                        .addLine(Component.text("\uD83C\uDF31 ", NamedTextColor.DARK_GRAY).decoration(TextDecoration.ITALIC, false).append(Component.text("Farming Tool")))
                .build())
        ;


        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
        });
        return item;

    }


}
