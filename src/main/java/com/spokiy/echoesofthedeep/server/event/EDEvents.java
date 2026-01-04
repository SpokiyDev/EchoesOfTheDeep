package com.spokiy.echoesofthedeep.server.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.config.EDConfigs;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EDEvents {


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();

        if (EDConfigs.WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER_ENABLED.get()) {
            if (source.getMsgId().equals("sonic_boom") && source.getDirectEntity() instanceof Warden) {
                float baseDamage = event.getAmount();

                AttributeInstance armorAttribute = entity.getAttribute(Attributes.ARMOR);
                if (armorAttribute != null) {
                    double multiplier = 1 - EDConfigs.WARDEN_SONIC_ARMOR_PENETRATION_MULTIPLIER.get();

                    float modifiedArmor = (float) (armorAttribute.getValue() * multiplier);
                    float damageAfterArmor = baseDamage * (1 - modifiedArmor / 25f);

                    event.setAmount(damageAfterArmor);
                }
            }
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        EDVillagerTrades.addCustomTrades(event);
    }

}