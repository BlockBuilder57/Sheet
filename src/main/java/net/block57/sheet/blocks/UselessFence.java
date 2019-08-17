package net.block57.sheet.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class UselessFence extends FenceBlock {
	public UselessFence(Settings settings)
	{
		super(settings);
	}

	public boolean canConnect(BlockState blockState, boolean connectIfNone, Direction direction)
	{
		return blockState.getBlock() instanceof UselessFence;
	}

	public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityContext entityContext)
	{
		return VoxelShapes.empty();
	}
}
