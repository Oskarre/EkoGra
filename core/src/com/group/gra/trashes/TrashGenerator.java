package com.group.gra.trashes;

import com.badlogic.gdx.utils.Array;
import java.util.Random;

import static com.group.gra.trashes.TrashConstans.*;

public class TrashGenerator {
    private static final int TRASH_TYPES_AMOUNT = 6;
    private static final int AMOUNT_IN_ONE_TRASH_TYPE = 5;
    private static final String PNG_TYPE = ".png";
    private static final String TRASHES_DIRECTORY = "trashes/";

    public Array<Trash> generateTrashArray(int trashAmount) {
        Array<Trash> trashes = new Array<>();

        for (int i = 0; i < trashAmount; i++) {
            Trash trash = generateTrash();
            trashes.add(trash);
        }
        return trashes;
    }

    private Trash generateTrash() {
        Random random = new Random();
        int randomNumber = random.nextInt(TRASH_TYPES_AMOUNT);
        switch (randomNumber) {
            case 0:
                return generatePlasticTrash();
            case 1:
                return generateGlassTrash();
            case 2:
                return generatePaperTrash();
            case 3:
                return generateBioTrash();
            case 4:
                return generateMixedTrash();
            default:
                return generateHazardousTrash();
        }
    }

    private Plastic generatePlasticTrash() {
        String name = getProductName(PLASTIC_1, PLASTIC_2, PLASTIC_3, PLASTIC_4, PLASTIC_5);
        String imageName = parseToFileDirectory(name);
        return new Plastic(name, imageName);
    }

    private Glass generateGlassTrash() {
        String name = getProductName(GLASS_1, GLASS_2, GLASS_3, GLASS_4, GLASS_5);
        String imageName = parseToFileDirectory(name);
        return new Glass(name, imageName);
    }

    private Paper generatePaperTrash() {
        String name = getProductName(PAPER_1, PAPER_2, PAPER_3, PAPER_4, PAPER_5);
        String imageName = parseToFileDirectory(name);
        return new Paper(name, imageName);
    }

    private Bio generateBioTrash() {
        String name = getProductName(ORGANIC_1, ORGANIC_2, ORGANIC_3, ORGANIC_4, ORGANIC_5);
        String imageName = parseToFileDirectory(name);
        return new Bio(name, imageName);
    }

    private HazardousTrash generateHazardousTrash() {
        String name = getProductName(EWASTE_1, EWASTE_2, EWASTE_3, EWASTE_4, EWASTE_5);
        String imageName = parseToFileDirectory(name);
        return new HazardousTrash(name, imageName);
    }

    private MixedTrash generateMixedTrash() {
        String name = getProductName(METAL_1, METAL_2, METAL_3, METAL_4, METAL_5);
        String imageName = parseToFileDirectory(name);
        return new MixedTrash(name, imageName);
    }

    private String getProductName(String name1, String name2, String name3, String name4, String name5) {
        Random random = new Random();
        int randomNumber = random.nextInt(AMOUNT_IN_ONE_TRASH_TYPE) + 1;
        switch (randomNumber) {
            case 0:
                return name1;
            case 1:
                return name2;
            case 2:
                return name3;
            case 3:
                return name4;
            default:
                return name5;
        }
    }

    private String parseToFileDirectory(String name) {
        return TRASHES_DIRECTORY + name + PNG_TYPE;
    }
}
