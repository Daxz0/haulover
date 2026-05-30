package daxz.dev.haulover.Skills.Farming.Crops.Tomato;


import daxz.dev.haulover.Skills.Farming.Crops.CropPlant;
import org.bukkit.Location;

public class TomatoPlant extends CropPlant {

    public TomatoPlant() {
        cropID = "tomato_plant";
        seedID = "tomato_seed";
    }

    @Override
    protected void updateGrowModel() {
        switch (growState){

            case 1:


        }
    }
}
