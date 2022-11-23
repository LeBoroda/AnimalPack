import animals.AbsAnimal;
import data.AnimalTypesData;
import data.CommandsData;
import factories.AnimalFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Runner {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        System.out.println("Добро пожаловать в зоопарк");
        ArrayList<AbsAnimal> animalPack = new ArrayList<>();

        while (true) {
            menuRunner();

            String commandInput = reader.readLine().toUpperCase().trim();
            CommandsData command = CommandsData.valueOf(checkCommand(commandInput));

            switch (command) {
                case ADD: {
                    animalPack.add(animalCreation());
                    animalPack.get(animalPack.size() - 1).say();
                    System.out.print("\n");
                    break;
                }
                case LIST: {
                    if (animalPack.size() == 0) {
                        System.out.println("Пока у вас нет животных" + "\n");
                    } else {
                        for (AbsAnimal animal : animalPack) {
                            System.out.println(animal.toString());
                        }
                        System.out.print("\n");
                    }
                    break;
                }
                case EXIT: {
                    System.exit(0);
                }
                default: {
                    System.out.println("Такой командый нет, попробуйте ещё" + "\n");
                }

            }
        }
    }

    private static AbsAnimal animalCreation() throws IOException {
        System.out.println("Введите тип животного");
        //подсказка пользователю какие животные есть в списке
        for (AnimalTypesData type : AnimalTypesData.values()) {
            System.out.print(type + " ");
        }
        System.out.print("\n");

        String animalTypeToCheck = reader.readLine().toUpperCase();
        AnimalTypesData animalType = AnimalTypesData.valueOf(checkAnimalType(animalTypeToCheck));

        return fillAnimalData(animalType);
    }

    private static AbsAnimal fillAnimalData(AnimalTypesData animalTypesData) throws IOException {
        AnimalFactory animalFactory = new AnimalFactory();
        AbsAnimal animal = animalFactory.create(animalTypesData);

        System.out.println("Как зовут животное?");
        animal.setName(reader.readLine().trim());

        System.out.println("Какой цвет у животного?");
        animal.setColor(reader.readLine().trim());

        System.out.println("Какой возраст у животного?");
        int ageInput = Integer.parseInt(checkNumberInput(reader.readLine().trim()));  //проверка на корректность ввода
        int age = checkAge(ageInput);                                                 // проверка на корректность введенного возраста
        animal.setAge(age);

        System.out.println("Какой вес у животного?");
        int weightInput = Integer.parseInt(checkNumberInput(reader.readLine().trim()));  //проверка на корректность ввода
        int weight = checkWeight(weightInput);                                           //проверка на корректность веса
        animal.setWeight(weight);

        return animal;
    }


    private static void menuRunner() {
        System.out.println("Введите команду");
        //подсказка пользователю со списком всех доступных команд
        for (CommandsData command : CommandsData.values()) {
            System.out.print(" / " + command + " / ");
        }
        System.out.print("\n");
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ignoring) {
            return false;
        }
    }

    private static String checkNumberInput(String numberInput) throws IOException {
        String userInput = numberInput;
        while (!isNumber(userInput)) {
            System.out.println("Тут должно быть целое число");
            System.out.println("Повторите ввод");
            userInput = reader.readLine().trim();
        }
        return userInput;
    }

    private static int checkAge(int ageInput) throws IOException {

        boolean ageAccepted = false;
        int ageToCheck = ageInput;

        while (!ageAccepted) {
            if (ageToCheck > 99) {
                System.out.println("Вам тут таки что, дом драконов? Зверушки столько не живут");
                System.out.println("Попробуйте ещё раз");
                ageToCheck = Integer.parseInt(checkNumberInput(reader.readLine().trim()));
            } else if (ageToCheck <= 0) {
                System.out.println("Серьёзно? Дракон вылупится через неделю?");
                System.out.println("Попробуйте ещё раз");
                ageToCheck = Integer.parseInt(checkNumberInput(reader.readLine().trim()));
            } else {
                ageAccepted = true;
            }
        }
        return ageToCheck;
    }

    private static int checkWeight(int weightInput) throws IOException {

        boolean weightAccepted = false;
        int weightToCheck = weightInput;

        while (!weightAccepted) {
            if (weightToCheck <= 0) {
                System.out.println("Вам нужен шарик с гелием или настоящее животное?");
                System.out.println("Попробуйте ещё раз");
                weightToCheck = Integer.parseInt(checkNumberInput(reader.readLine().trim()));
            } else {
                weightAccepted = true;
            }
        }
        return weightToCheck;
    }

    private static boolean isCommand(String userInputCommand) {
        try {
            CommandsData.valueOf(userInputCommand);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static String checkCommand(String userInput) throws IOException {
        String command = userInput;
        boolean commandCorrect = false;
        while (!commandCorrect) {
            if (isCommand(command)) {
                commandCorrect = true;
            } else {
                System.out.println("Команда введена неверно");
                menuRunner();
                command = reader.readLine().toUpperCase().trim();
            }
        }
        return command;
    }

    private static boolean isAnimalType(String userInputType) {
        try {
            AnimalTypesData.valueOf(userInputType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static String checkAnimalType(String userInput) throws IOException {
        String animalType = userInput;
        boolean isCorrectType = false;

        while (!isCorrectType) {
            if (isAnimalType(animalType)) {
                isCorrectType = true;
            } else {
                System.out.println("В нашем зоопарке нет такого животного");
                System.out.println("Введите тип животного");
                animalType = reader.readLine().toUpperCase().trim();
            }
        }
        return animalType;
    }
}