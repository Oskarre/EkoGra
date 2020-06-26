package com.group.gra.trashes;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

import static com.group.gra.trashes.TrashConstants.*;

public class TrashGenerator {
    private static final int TRASH_TYPES_AMOUNT = 6;
    private static final int AMOUNT_IN_ONE_TRASH_TYPE = 5;
    public static final int PLASTIC_SUBTYPES_AMOUNT = 2;
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
        Random random = new Random();
        int randomNumber = random.nextInt(PLASTIC_SUBTYPES_AMOUNT);
        String name;

        if (randomNumber == 0) {
            name = getProductName(GREEN_CAN, RED_CAN, TIN, CLOSED_TIN, ORANGE_CAN);
        } else {
            name = getProductName(RED_BAG, GREEN_BAG, BLUE_BAG, CRASHED_PLASTIC_BOTTLE, PLASTIC_BOTTLE);
        }

        String imageName = parseToFileDirectory(name);
        return new Plastic(name, imageName);
    }

    private Glass generateGlassTrash() {
        String name = getProductName(CHAMPAGNE_BOTTLE, GREEN_BOTTLE, GLASS_OF_WINE, BROWN_CHAMPAGNE_BOTTLE, BROWN_BOTTLE);
        String imageName = parseToFileDirectory(name);
        return new Glass(name, imageName);
    }

    private Paper generatePaperTrash() {
        String name = getProductName(PACKAGE, CARTOON, CARD, NEWSPAPER, PILE_OF_NEWSPAPERS);
        String imageName = parseToFileDirectory(name);
        return new Paper(name, imageName);
    }

    private Bio generateBioTrash() {
        String name = getProductName(PEPPER, BANANA_SKIN, SPINACH, ORGANIC_4, APPLE_STAB);
        String imageName = parseToFileDirectory(name);
        return new Bio(name, imageName);
    }

    private HazardousTrash generateHazardousTrash() {
        String name = getProductName(BATTERY, MICROWAVE, CALCULATOR, BULB, LAPTOP);
        String imageName = parseToFileDirectory(name);
        return new HazardousTrash(name, imageName);
    }

    private MixedTrash generateMixedTrash() {
        String name = getProductName(BONE, EGG_SHELL, DOG_PILE, MEAT, T_SHIRT);
        String imageName = parseToFileDirectory(name);
        return new MixedTrash(name, imageName);
    }

    private String getProductName(String name1, String name2, String name3, String name4, String name5) {
        Random random = new Random();
        int randomNumber = random.nextInt(AMOUNT_IN_ONE_TRASH_TYPE);
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
