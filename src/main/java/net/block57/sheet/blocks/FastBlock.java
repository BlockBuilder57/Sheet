package net.block57.sheet.blocks;

import net.block57.sheet.entity.RisingBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.state.property.Properties;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class FastBlock extends FallingBlock {
	public FastBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory)
    {
        super.appendProperties(stateFactory);
        stateFactory.add(Properties.FALLING);
    }

	@Override
	public void onScheduledTick(BlockState blockState, World world, BlockPos blockPos, Random random)
	{
		if (blockState.get(Properties.FALLING))
		{
			if (!world.isClient) {
				this.tryStartFalling(world, blockPos);
			}
		}
	}

	public void tryStartFalling(World world, BlockPos blockPos) {
		if (canFallThrough(world.getBlockState(blockPos.down())) && blockPos.getY() >= 0) {
			if (!world.isClient) {
				RisingBlockEntity risingBlockEntity = new RisingBlockEntity(world, (double)blockPos.getX() + 0.5D, blockPos.getY(), (double)blockPos.getZ() + 0.5D, world.getBlockState(blockPos), true);
				//this.configureFallingBlockEntity(risingBlockEntity);
				world.spawnEntity(risingBlockEntity);
			}

		}
	}
}
