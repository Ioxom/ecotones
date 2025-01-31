package supercoder79.ecotones.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;
import supercoder79.ecotones.api.DevOnly;
import supercoder79.ecotones.world.gen.EcotonesChunkGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@DevOnly
public class MapSoilQualityCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("mapsoilquality")
                    .requires(source -> source.hasPermissionLevel(2));

            builder.executes(context -> execute(context.getSource()));

            dispatcher.register(builder);

        });
    }

    private static int execute(ServerCommandSource source) {
        BufferedImage img = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_RGB);

        EcotonesChunkGenerator chunkGenerator = (EcotonesChunkGenerator) source.getWorld().getChunkManager().getChunkGenerator();

        for (int x = -2048; x < 2048; x++) {
            if (x % 512 == 0) {
                source.sendFeedback(new LiteralText(((x + 2048) / 4096.0) * 100 + "%"), false);
            }

            for (int z = -2048; z < 2048; z++) {
                int height = (int) ((MathHelper.clamp(chunkGenerator.getSoilQualityAt(x, z), -1.0, 1.0) * 127) + 128);

                img.setRGB(x + 2048, z + 2048, getIntFromColor(height, height, height));
            }
        }

        // save the biome map
        Path p = Paths.get("ecotones_soil_quality.png");
        try {
            ImageIO.write(img, "png", p.toAbsolutePath().toFile());
            source.sendFeedback(new LiteralText("Mapped soil quality!"), false);
        } catch (IOException e) {
            source.sendFeedback(new LiteralText("Something went wrong, check the log!"), true);
            e.printStackTrace();
        }



        return 0;
    }

    private static int getIntFromColor(int red, int green, int blue) {
        red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        green = (green << 8) & 0x0000FF00; //Shift green 8-bits and mask out other stuff
        blue = blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
}
