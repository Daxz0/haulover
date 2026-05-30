package daxz.dev.haulover.Skills.Farming.Crops;

import daxz.dev.haulover.Registry.ItemRegistry;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public abstract class CropPlant {
    protected int ticksPerStage = 100;
    protected int growTicks = 0;
    protected int growState = 0;
    protected int maxGrowStates = 3;

    protected Location loc;

    protected String seedID;
    protected String cropID;


    public void createPlant(Location loc, String seedID, String cropID) {
        this.loc = loc;
    }


    public ItemStack getCropResult(){
        return ItemRegistry.getItem(cropID);
    }

    public ItemStack getSeedResult(){
        return ItemRegistry.getItem(seedID);
    }

    protected abstract void updateGrowModel();

    public void updateGrowState(){

        if (growState >= maxGrowStates){
            return;
        }
        growState++;
        updateGrowModel();
    }

    // main class to tick with when updating
    public void updateGrowTicks(int amount){
        growTicks+=amount;

        if (growTicks >= ticksPerStage){
            growTicks = 0;
            updateGrowState();
        }


    }


}

