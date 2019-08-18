package net.block57.sheet.blocks;

import com.sun.istack.internal.Nullable;
import net.block57.sheet.Sheet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class FamousTorch extends TorchBlock {
	public FamousTorch(Settings settings)
	{
		super(settings);
	}

	public void onPlaced(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity_1, ItemStack itemStack)
	{
		Box area = new Box(blockPos).expand(16);

		for (double x = area.minX; x < area.maxX; x++)
		{
			for (double y = area.minY; y < area.maxY; y++)
			{
				for (double z = area.minZ; z < area.maxZ; z++)
				{
					BlockState state = world.getBlockState(new BlockPos(x,y,z));
					if (state.getBlock() == Blocks.TORCH || state.getBlock() == Blocks.WALL_TORCH || state.getBlock() == Sheet.FAMOUS_TORCH || state.getBlock() == Sheet.FAMOUS_WALL_TORCH)
						if (state.getBlock() != this)
							world.setBlockState(new BlockPos(x,y,z), Blocks.AIR.getDefaultState());
				}
			}
		}
	}
}
