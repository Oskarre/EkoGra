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
        String name = getProductName(JUICE_CARTON, CAN, YOGURT_CONTAINER, PLASTIC_BOTTLE, PLASTIC_BAG);
        String imageName = parseToFileDirectory(name);
        return new Plastic(name, imageName);
    }

    private Glass generateGlassTrash() {
        String name = getProductName(BEER_BOTTLE, COSMETIC_BOTTLE, JAR, GLASS, OTHER_BOTTLE);
        String imageName = parseToFileDirectory(name);
        return new Glass(name, imageName);
    }

    private Paper generatePaperTrash() {
        String name = getProductName(BOOK, NOTE_BOOK, NEWSPAPER, CARTOON, CARD);
        String imageName = parseToFileDirectory(name);
        return new Paper(name, imageName);
    }

    private Bio generateBioTrash() {
        String name = getProductName(BANANA_SKIN, APPLE_STAB, LEAVES, BRANCH, LEES);
        String imageName = parseToFileDirectory(name);
        return new Bio(name, imageName);
    }

    private HazardousTrash generateHazardousTrash() {
        String name = getProductName(BATTERY, FRIDGE, MOBILE_PHONE, HARDCORE, FLUORESCENT_LAMP);
        String imageName = parseToFileDirectory(name);
        return new HazardousTrash(name, imageName);
    }

    private MixedTrash generateMixedTrash() {
        String name = getProductName(BONE, EGG_SHELL, TISSUE, DOG_PILE, CLOTHES);
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
