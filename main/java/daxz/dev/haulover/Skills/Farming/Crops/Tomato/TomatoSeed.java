package daxz.dev.haulover.Skills.Farming.Crops.Tomato;

import daxz.dev.haulover.Haulover;
import daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans.WateringCan;
import daxz.dev.haulover.Utilities.Lib.LoreTool;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.UseCooldown;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class TomatoSeed{
    public static final String ID = "tomato_seed";

    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    public static final NamespacedKey uuid = new NamespacedKey(Haulover.getInstance(), "uuid");

    //flags
    public static final TomatoSeed INSTANCE = new TomatoSeed();


    public ItemStack createItem() {

        Material material = Material.BEETROOT_SEEDS;
        ItemStack item = ItemStack.of(material);



        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Tomato Seed", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));

        LoreTool.lore(item,
        "<dark_gray> Could become a tomato..",
              "<dark_gray><st>                                          </st>",
              "<white>\uD83C\uDF31 <bold>Seed"
        );


        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
            pdc.set(uuid, PersistentDataType.STRING, UUID.randomUUID().toString());
        });
        return item;

    }


}
