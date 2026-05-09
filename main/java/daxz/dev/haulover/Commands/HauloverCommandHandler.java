package daxz.dev.haulover.Commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import daxz.dev.haulover.Registry.ItemRegistry;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class HauloverCommandHandler {

    public static LiteralCommandNode<CommandSourceStack> haulover() {

        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("haulover");

        root.then(
                Commands.literal("give").then(
                Commands.argument("item", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            ItemRegistry.getRegisteredItems().keySet().forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(ctx -> {
                            if (ctx.getSource().getSender() instanceof Player player) {
                                ItemRegistry.giveItem(player, ctx.getArgument("item", String.class));
                            }
                            return 1;
                        })
                )
        );

        return root.build();
    }




}
