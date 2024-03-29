package net.block57.sheet;

import net.block57.sheet.blocks.*;
import net.block57.sheet.entity.RisingBlockEntity;
import net.block57.sheet.items.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.packet.UpdateSelectedSlotC2SPacket;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.minecraft.block.Blocks.OAK_PLANKS;

public class Sheet implements ModInitializer {

	public static final String ModID = "sheet";

	public static final Identifier NIGRA_EVENT_ID = new Identifier(ModID, "nigra");
	public static SoundEvent NIGRA_EVENT = new SoundEvent(NIGRA_EVENT_ID);
	public static final Identifier NOKIA_ARABIC_EVENT_ID = new Identifier(ModID, "nokia_arabic");
	public static SoundEvent NOKIA_ARABIC_EVENT = new SoundEvent(NOKIA_ARABIC_EVENT_ID);

	public static EntityType<RisingBlockEntity> RISING_BLOCK;
	public static ToolMaterial WII_MATERIAL = new SheetToolMaterial(4, 800, 10f, 12f, 15, () -> Ingredient.ofItems(Blocks.OBSIDIAN));
	public static ToolMaterial BAN_MATERIAL = new SheetToolMaterial(1337, 1337, 1337f, 1336f, 1337, () -> Ingredient.ofItems(Blocks.BEDROCK));

	public static final FastBlock FAST_BLOCK = new FastBlock(FabricBlockSettings.of(Material.PISTON).sounds(BlockSoundGroup.LANTERN).lightLevel(15).breakInstantly().slipperiness(4).build());
	public static final SlowBlock SLOW_BLOCK = new SlowBlock(FabricBlockSettings.of(Material.PISTON).sounds(BlockSoundGroup.LANTERN).lightLevel(-15).breakInstantly().slipperiness(0.1f).build());
	public static final Block YELLOW_SNOW_BLOCK = new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).strength(0.2F, 1f).sounds(BlockSoundGroup.SNOW).build());
	public static final Block NUT_SNOW_BLOCK = new Block(FabricBlockSettings.of(Material.SNOW_BLOCK).strength(0.2F, 1f).sounds(BlockSoundGroup.SLIME).build());
	public static final UselessFence USELESS_FENCE = new UselessFence(FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).noCollision().build());
	public static final FamousTorch FAMOUS_TORCH = new FamousTorch(FabricBlockSettings.of(Material.PART).noCollision().breakInstantly().lightLevel(14).sounds(BlockSoundGroup.WOOD).build());
	public static final FamousWallTorch FAMOUS_WALL_TORCH = new FamousWallTorch(FabricBlockSettings.of(Material.PART).noCollision().breakInstantly().lightLevel(14).sounds(BlockSoundGroup.WOOD).dropsLike(FAMOUS_TORCH).build());

	public static final ItemGroup SHEET_ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(ModID, "general"), () -> new ItemStack(FAST_BLOCK));

	public static final NigraCoin NIGRACOIN = new NigraCoin(new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(7));
	public static final WalterCoin WALTERCOIN = new WalterCoin(new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(14));
	public static final Item CHINKEN_NUNGET = new Item(new Item.Settings().group(SHEET_ITEM_GROUP).food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).meat().snack().build()));
	public static final Item NEGABREAD = new Item(new Item.Settings().group(SHEET_ITEM_GROUP).food(new FoodComponent.Builder().hunger(-5).saturationModifier(-0.6F).alwaysEdible().build()));
	public static final SwordItem WII_REMOTE = new SwordItem(WII_MATERIAL, 0, 16f, new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(1).maxDamage(0));
	public static final BanHammer BANHAMMER = new BanHammer(BAN_MATERIAL, 0, 16f, new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(1));
	public static final SnowballItem YELLOW_SNOWBALL = new SnowballItem(new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(16));
	public static final NokiaPhone NOKIA_PHONE = new NokiaPhone(new Item.Settings().group(SHEET_ITEM_GROUP).maxCount(1));

	/*public static final ItemGroup USEFUL_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(ModID, "useful"))
			.icon(() -> new ItemStack(Blocks.COMMAND_BLOCK))
			.appendItems(stacks ->
			{
				stacks.add(new ItemStack(Blocks.COMMAND_BLOCK));
				stacks.add(new ItemStack(Blocks.CHAIN_COMMAND_BLOCK));
				stacks.add(new ItemStack(Blocks.REPEATING_COMMAND_BLOCK));
				stacks.add(new ItemStack(Items.DEBUG_STICK));
				stacks.add(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.EMPTY));
			}).build();*/

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("[Sheet] says hi");

		Registry.register(Registry.SOUND_EVENT, NIGRA_EVENT_ID, NIGRA_EVENT);
		Registry.register(Registry.SOUND_EVENT, NOKIA_ARABIC_EVENT_ID, NOKIA_ARABIC_EVENT);

		RISING_BLOCK = Registry.register(Registry.ENTITY_TYPE, new Identifier(ModID, "rising_block"), FabricEntityTypeBuilder.<RisingBlockEntity>create(EntityCategory.MISC).build());

		Registry.register(Registry.ITEM, new Identifier(ModID, "nigracoin"), NIGRACOIN);
		Registry.register(Registry.ITEM, new Identifier(ModID, "waltercoin"), WALTERCOIN);
		Registry.register(Registry.ITEM, new Identifier(ModID, "chinken_nunget"), CHINKEN_NUNGET);
		Registry.register(Registry.ITEM, new Identifier(ModID, "wii_remote"), WII_REMOTE);
		Registry.register(Registry.ITEM, new Identifier(ModID, "banhammer"), BANHAMMER);
		Registry.register(Registry.ITEM, new Identifier(ModID, "yellow_snowball"), YELLOW_SNOWBALL);
		Registry.register(Registry.ITEM, new Identifier(ModID, "negabread"), NEGABREAD);
		Registry.register(Registry.ITEM, new Identifier(ModID, "nokia_phone"), NOKIA_PHONE);

		Registry.register(Registry.BLOCK, new Identifier(ModID, "fast_block"), FAST_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ModID, "fast_block"), new BlockItem(FAST_BLOCK, new Item.Settings().group(SHEET_ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(ModID, "slow_block"), SLOW_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ModID, "slow_block"), new BlockItem(SLOW_BLOCK, new Item.Settings().group(SHEET_ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(ModID, "yellow_snow_block"), YELLOW_SNOW_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ModID, "yellow_snow_block"), new BlockItem(YELLOW_SNOW_BLOCK, new Item.Settings().group(SHEET_ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(ModID, "nut_snow_block"), NUT_SNOW_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ModID, "nut_snow_block"), new BlockItem(NUT_SNOW_BLOCK, new Item.Settings().group(SHEET_ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(ModID, "useless_fence"), USELESS_FENCE);
		Registry.register(Registry.ITEM, new Identifier(ModID, "useless_fence"), new BlockItem(USELESS_FENCE, new Item.Settings().group(SHEET_ITEM_GROUP)));
		Registry.register(Registry.BLOCK, new Identifier(ModID, "famous_torch"), FAMOUS_TORCH);
		Registry.register(Registry.BLOCK, new Identifier(ModID, "famous_wall_torch"), FAMOUS_WALL_TORCH);
		Registry.register(Registry.ITEM, new Identifier(ModID, "famous_torch"), new WallStandingBlockItem(FAMOUS_TORCH, FAMOUS_WALL_TORCH, new Item.Settings().group(SHEET_ITEM_GROUP)));
	}
}
