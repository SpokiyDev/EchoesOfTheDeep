package com.spokiy.echoesofthedeep.client.model;

import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WardenHeadModel extends SkullModel {

    public WardenHeadModel(ModelPart part) {
        super(part);
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild(
                "head", CubeListBuilder.create().texOffs(0, 32).addBox(
                                -8.0F, -12.0F, -6.0F,
                                16.0F, 16.0F, 10.0F),
                PartPose.ZERO
        );
        head.addOrReplaceChild(
                "right_tendril",
                CubeListBuilder.create().texOffs(58, 38).addBox(
                        -10.0F, -10.0F, 0.001F,
                        10.0F, 10.0F, 0.0F),
                PartPose.offset(-8.0F, -5.0F, -1.0F)
        );
        head.addOrReplaceChild(
                "left_tendril",
                CubeListBuilder.create().texOffs(58, 6).addBox(
                        0.0F, -10.0F, 0.0F,
                        10.0F, 10.0F, 0.0F),
                PartPose.offset(8.0F, -5.0F, -1.0F)
        );

        return LayerDefinition.create(mesh, 128, 128);
    }
}
