package mc.mian.lifesteal.fabric;

import net.fabricmc.api.ClientModInitializer;
import mc.mian.lifesteal.common.blockentity.LSBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;

public class LifestealFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(LSBlockEntityTypes.EXPANDED_SKULL.get(), SkullBlockRenderer::new);
    }
}