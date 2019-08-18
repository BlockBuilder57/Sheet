package net.block57.sheet.blocks;

import net.block57.sheet.entity.RisingBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class SlowBlock extends FallingBlock {

	public SlowBlock(Settings settings)
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
		if (blockState.get(Properties.FALLING) == true)
		{
			if (!world.isClient) {
				this.tryStartFalling(world, blockPos);
			}
		}
	}

	public void tryStartFalling(World world, BlockPos blockPos) {
		if (canFallThrough(world.getBlockState(blockPos.up())) && blockPos.getY() <= 255) {
			if (!world.isClient) {
				RisingBlockEntity risingBlockEntity = new RisingBlockEntity(world, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, world.getBlockState(blockPos), false);
				world.spawnEntity(risingBlockEntity);
			}

		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
		if (random.nextInt(16) == 0) {
			BlockPos blockPos_2 = blockPos.up();
			if (canFallThrough(world.getBlockState(blockPos_2))) {
				double double_1 = (double)((float)blockPos.getX() + random.nextFloat());
				double double_2 = (double)blockPos.getY() + 0.05D;
				double double_3 = (double)((float)blockPos.getZ() + random.nextFloat());
				world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, blockState), double_1, double_2, double_3, 0.0D, 0.0D, 0.0D);
			}
		}

	}
}
