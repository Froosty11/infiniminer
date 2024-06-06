package org.gobbos.infiniminer;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.text.Text;
import org.gobbos.infiniminer.gpt.BridgeClass;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Infiniminer implements ModInitializer {
    @Override
    public void onInitialize() {
        BridgeClass bridger = new BridgeClass();
        //handshake process
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(literal("gpt")
                        .then(argument("value1", StringArgumentType.string())
                                .then(argument("value2", StringArgumentType.string())
                                        .executes(context -> {
                                            final String value1 = StringArgumentType.getString(context, "value1");
                                            final String value2 = StringArgumentType.getString(context, "value2");

                                            //context.getSource().sendFeedback(() -> Text.literal("Called /gpt with arguments " +  value1 + " and "+ value2), false);
                                            context.getSource().sendFeedback(() -> Text.literal(bridger.makePostRequest(value1,value2)), false);
                                            return 1;
                                        })
                                )
                        )
                )
        );
    }
}
