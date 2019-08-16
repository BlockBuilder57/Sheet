package net.block57.sheet;

import net.block57.sheet.blocks.FastBlock;
import net.block57.sheet.blocks.SlowBlock;
import net.block57.sheet.entity.RisingBlockEntity;
import net.block57.sheet.items.NigraCoin;
import net.block57.sheet.items.SheetToolMaterial;
import net.block57.sheet.items.WalterCoin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sheet implements ModInitializer {

	public static final String ModID = "sheet";

	public static final Identifier NIGRA_EVENT_ID = new Identifier(ModID, "nigra");
	public static SoundEvent NIGRA_EVENT = new SoundEvent(NIGRA_EVENT_ID);

	public static EntityType<RisingBlockEntity> RISING_BLOCK;
	public static ToolMaterial WII_MATERIAL = new SheetToolMaterial(4, 800, 10f, 12f, 15, () -> Ingredient.ofItems(new ItemConvertible[]{Blocks.OBSIDIAN}));

	public static final FastBlock FAST_BLOCK = new FastBlock(FabricBlockSettings.of(Material.PISTON).sounds(BlockSoundGroup.LANTERN).lightLevel(15).breakInstantly().slipperiness(4).build());
	public static final SlowBlock SLOW_BLOCK = new SlowBlock(FabricBlockSettings.of(Material.PISTON).sounds(BlockSoundGroup.LANTERN).lightLevel(-15).breakInstantly().slipperiness(0.1f).build());

	public static final ItemGroup SHEET_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(ModID, "general"), () -> new ItemStack(FAST_BLOCK));

	public static final NigraCoin NIGRACOIN = new NigraCoin(new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(7));
	public static final WalterCoin WALTERCOIN = new WalterCoin(new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(14));
	public static final Item CHINKEN_NUNGET = new Item(new Item.Settings().group(SHEET_ITEM_GROUP).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).meat().snack().build()));
	public static final SwordItem WII_REMOTE = new SwordItem(WII_MATERIAL, 0, 16f, new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(1).maxDamage(0));

	public static final ItemGroup USEFUL_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(ModID, "useful"))
			.icon(() -> new ItemStack(Blocks.COMMAND_BLOCK))
			.appendItems(stacks ->
			{
				stacks.add(new ItemStack(Blocks.COMMAND_BLOCK));
				stacks.add(new ItemStack(Blocks.CHAIN_COMMAND_BLOCK));
				stacks.add(new ItemStack(Blocks.REPEATING_COMMAND_BLOCK));
				stacks.add(new ItemStack(Items.DEBUG_STICK));
				stacks.add(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.EMPTY));
			}).build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("[Sheet] says hi");

		Registry.register(Registry.SOUND_EVENT, NIGRA_EVENT_ID, NIGRA_EVENT);

		RISING_BLOCK = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "rising_block"), FabricEntityTypeBuilder.<RisingBlockEntity>create(EntityCategory.MISC).build());

		Registry.register(Registry.ITEM, new Identifier(ModID, "nigracoin"), NIGRACOIN);
		Registry.register(Registry.ITEM, new Identifier(ModID, "waltercoin"), WALTERCOIN);
		Registry.register(Registry.ITEM, new Identifier(ModID, "chinken_nunget"), CHINKEN_NUNGET);
		Registry.register(Registry.ITEM, new Identifier(ModID, "wii_remote"), WII_REMOTE);

		Registry.register(Registry.BLOCK, new Identifier(ModID, "fast_block"), FAST_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ModID, "fast_block"), new BlockItem(FAST_BLOCK, new Item.Settings().group(SHEET_ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(ModID, "slow_block"), SLOW_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ModID, "slow_block"), new BlockItem(SLOW_BLOCK, new Item.Settings().group(SHEET_ITEM_GROUP)));
	}
}
