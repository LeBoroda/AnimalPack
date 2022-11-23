package factories;

import animals.AbsAnimal;
import animals.birds.Duck;
import animals.pets.Cat;
import animals.pets.Dog;
import data.AnimalTypesData;

public class AnimalFactory {
    public AbsAnimal create(AnimalTypesData animalType){
        switch (animalType){
            case CAT: {
                return new Cat();
            }
            case DOG: {
                return new Dog();
            }
            case DUCK: {
                return new Duck();
            }
        }
        return null;
    }

}
