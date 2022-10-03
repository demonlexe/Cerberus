package lzxus.cerberus.structs;

import lzxus.cerberus.configdata.ConfigData;
import lzxus.cerberus.petdata.Pet;

import java.util.ArrayList;

public class LevelChecker {
    private ArrayList<Integer> specialLevelRequirements;
    protected ConfigData cData = new ConfigData();

    public int getOpenSlot(Pet pet) { // pet should be obtained from Cerberus.obtainPetData(p);
        if (pet == null || !pet.getWolfStatus().equals(1)) { return -1;}
        Integer petLvl = pet.getWolfLvl();
        if (petLvl==null) {return -1;}

        if (pet.getSpecial1().equals("")) {
            if (petLvl >= specialLevelRequirements.get(0)) {
                return 1;
            }
        }
        else if (pet.getSpecial2().equals("")) {
            if (petLvl >= specialLevelRequirements.get(1)) {
                return 2;
            }
        }
        else if (pet.getSpecial3().equals("")) {
            if (petLvl >= specialLevelRequirements.get(2)) {
                return 3;
            }
        }
        return -1;
    }

    // Returns 1 through maxLevel if successful, otherwise -1
    public int nextSlotLvl(Pet pet) {
        if (pet == null || !pet.getWolfStatus().equals(1)) { return -1;}
        Integer petLvl = pet.getWolfLvl();
        if (petLvl==null) {return -1;}

        // If a slot is empty, and we don't meet the level requirements for that slot, return the level required.
        if (pet.getSpecial1().equals("")) {
            if (petLvl < specialLevelRequirements.get(0)) {
                return specialLevelRequirements.get(0);
            }
        }
        if (pet.getSpecial2().equals("")) {
            if (petLvl < specialLevelRequirements.get(1)) {
                return specialLevelRequirements.get(1);
            }
        }
        if (pet.getSpecial3().equals("")) {
            if (petLvl < specialLevelRequirements.get(2)) {
                return specialLevelRequirements.get(2);
            }
        }
        return -1;
    }
    public LevelChecker() {
        specialLevelRequirements = (ArrayList<Integer>) cData.getSpecialLevels();
    }
}
