package daxz.dev.haulover.Commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class HauloverCommandHandler {

    public static LiteralCommandNode<CommandSourceStack> haulover() {

        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("haulover");


        return root.build();
    }

}
