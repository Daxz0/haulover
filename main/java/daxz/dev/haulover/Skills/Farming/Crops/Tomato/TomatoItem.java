package daxz.dev.haulover.Skills.Farming.Crops.Tomato;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Utilities.Lib.LoreTool;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class TomatoItem {
    public static final String ID = "tomato_item";

    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    public static final NamespacedKey uuid = new NamespacedKey(Haulover.getInstance(), "uuid");

    //flags
    public static final TomatoItem INSTANCE = new TomatoItem();


    public ItemStack createItem() {

        Material material = Material.APPLE;
        ItemStack item = ItemStack.of(material);



        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Tomato", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        LoreTool.lore(item,
        "<dark_gray> Tomato!",
              "<dark_gray><st>                                          </st>",
              "<white>\uD83C\uDF31 <bold>Crop"
        );


        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
            pdc.set(uuid, PersistentDataType.STRING, UUID.randomUUID().toString());
        });
        return item;

    }


}
