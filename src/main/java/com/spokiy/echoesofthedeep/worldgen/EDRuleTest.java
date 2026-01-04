package com.spokiy.echoesofthedeep.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class EDRuleTest extends RuleTest {
    public static final Codec<EDRuleTest> CODEC =
            Codec.FLOAT.fieldOf("chance")
                    .xmap(EDRuleTest::new, test -> test.chance)
                    .codec();

    private final float chance;

    public EDRuleTest(float chance) {
        this.chance = chance;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        return state.is(Blocks.STONE) && random.nextFloat() < chance;
    }

    @Override
    protected RuleTestType<?> getType() {
        return null;
    }
}
