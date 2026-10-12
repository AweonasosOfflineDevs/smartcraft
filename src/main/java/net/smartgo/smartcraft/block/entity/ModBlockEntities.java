package net.smartgo.smartcraft.block.entity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import net.smartgo.smartcraft.SmartCraft;
import net.smartgo.smartcraft.block.ModBlocks;
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SmartCraft.MOD_ID);
    public static final RegistryObject<BlockEntityType<SmartizadorBlockEntity>> SMARTIZADOR =
            TYPES.register("smartizador", () -> BlockEntityType.Builder.of(SmartizadorBlockEntity::new, ModBlocks.SMARTIZADOR.get()).build(null));
    public static void register(IEventBus bus) { TYPES.register(bus); }
}