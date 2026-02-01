package com.spokiy.echoesofthedeep.datagen.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;

import javax.swing.text.html.parser.Entity;
import java.util.HashSet;
import java.util.Set;

public class EDEntityLootProvider extends EntityLootSubProvider {
    private final Set<Entity> generatedLootTables = new HashSet<>();

    protected EDEntityLootProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {

    }
}
