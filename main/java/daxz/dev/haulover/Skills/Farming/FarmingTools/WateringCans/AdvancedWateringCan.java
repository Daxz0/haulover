package daxz.dev.haulover.Skills.Farming.FarmingTools.WateringCans;

import daxz.dev.haulover.Haulover;
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
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class AdvancedWateringCan implements WateringCan{
    public static final String ID = "advanced_watering_can";
    @Override public String getID() { return "advanced_watering_can"; }
    @Override public int getMaxCapacity() { return 200; }
    @Override public float getSpeed() { return 2.5F; }
    @Override public float getCooldownSpeed() {return 0.8f; }


    public static final NamespacedKey hauloverItemID = new NamespacedKey(Haulover.getInstance(), "haulover_item");
    public static final NamespacedKey uuid = new NamespacedKey(Haulover.getInstance(), "uuid");
    public static final NamespacedKey wateringCanID = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can");
    public static final NamespacedKey wateringCanCapacity = new NamespacedKey(Haulover.getInstance(), "haulover-watering_can-capacity");

    //flags
    public static final NamespacedKey stopConsume = new NamespacedKey(Haulover.getInstance(), "flag-no_consume");
    public static final AdvancedWateringCan INSTANCE = new AdvancedWateringCan();

    @Override
    public ShapedRecipe getRecipe() {
        NamespacedKey key = new NamespacedKey(Haulover.getInstance(), ID);
        ItemStack item = createItem();
        ShapedRecipe recipe = new ShapedRecipe(key, item));

        recipe.shape(
                "GGG",
                "GBG",
                "GGG"
        );

        item.editPersistentDataContainer(pdc -> {

            pdc.remove(uuid);
            pdc.remove(wateringCanID);

        });

        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('B', new RecipeChoice.ExactChoice(item));

        return recipe;

    }


    public ItemStack createItem() {

        Material material = Material.LIGHT_GRAY_DYE;
        ItemStack item = ItemStack.of(material);



        item.setData(DataComponentTypes.CUSTOM_NAME, Component.text("Advanced Watering Can", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable().animation(ItemUseAnimation.BOW).consumeSeconds(getSpeed()).sound(Key.key("")).hasConsumeParticles(false));
        item.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(getCooldownSpeed()).build());

        LoreTool.lore(item,
        "<dark_gray> A nice shiny watering can.",
              "<dark_gray><st>                                          </st>",
              "<gray>  Speed: <green>" + getSpeed() + "s",
              "<gray>  Capacity: <aqua>0<dark_aqua>/<dark_aqua>" + getMaxCapacity(),
              "<dark_gray><st>                                          </st>",
              "<white>\uD83C\uDF31 <bold>Farming Tool"
        );


        item.editPersistentDataContainer(pdc -> {
            pdc.set(hauloverItemID, PersistentDataType.STRING, ID);
            pdc.set(stopConsume, PersistentDataType.BOOLEAN, true);
            pdc.set(uuid, PersistentDataType.STRING, UUID.randomUUID().toString());
            pdc.set(wateringCanID, PersistentDataType.BOOLEAN, true);
            pdc.set(wateringCanCapacity, PersistentDataType.FLOAT, 0f);
        });
        return item;

    }


}
