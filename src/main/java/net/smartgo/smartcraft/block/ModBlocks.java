package net.smartgo.smartcraft.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smartgo.smartcraft.SmartCraft;
import net.smartgo.smartcraft.block.custom.RotateBlock;
import net.smartgo.smartcraft.block.custom.SmartizadorBlock;
import net.smartgo.smartcraft.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SmartCraft.MOD_ID);

    public static final RegistryObject<Block> DARKGO = registerBlock("darkgo", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GLINK_SMART = registerBlock("glink_smart", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MR_SMART = registerBlock("mr_smart", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART = registerBlock("smart", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTDEL_CALAMAR = registerBlock("smartdel_calamar", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTGHOST = registerBlock("smartghost", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTGO = registerBlock("smartgo", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTLINKED = registerBlock("smartlinked", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTROX = registerBlock("smartrox", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTSMILE = registerBlock("smartsmile", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTTLER = registerBlock("smarttler", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMARTY = registerBlock("smarty", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_18WN = registerBlock("smart_18wn", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_2807 = registerBlock("smart_2807", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_CASTILLO = registerBlock("smart_castillo", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_CD = registerBlock("smart_cd", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_CPSITO = registerBlock("smart_cpsito", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_DEL_OXXO = registerBlock("smart_del_oxxo", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_ELPEPE = registerBlock("smart_elpepe", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_FALOPA = registerBlock("smart_falopa", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_FIXION = registerBlock("smart_fixion", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_FLOYD = registerBlock("smart_floyd", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_KEK = registerBlock("smart_kek", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_MASTER_7PRO = registerBlock("smart_master_7pro", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_MIZUNO = registerBlock("smart_mizuno", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_MOMARDOPAPU = registerBlock("smart_momardopapu", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_NAVIDAD = registerBlock("smart_navidad", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_NONSITO = registerBlock("smart_nonsito", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_SANS = registerBlock("smart_sans", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_SUPER_IDOL = registerBlock("smart_super_idol", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_ZAD = registerBlock("smart_zad", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMART_ZAMN = registerBlock("smart_zamn", () -> new RotateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> SMARTIZADOR = registerBlock("smartizador",
            () -> new SmartizadorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));
        return registeredBlock;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}