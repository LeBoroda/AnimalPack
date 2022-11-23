package animals.birds;

import animals.AbsAnimal;

public class Duck extends AbsAnimal implements IFly {

    @Override
    public void fly() {
        System.out.println("Я лечу");
    }

    @Override
    public void say(){
        System.out.println("Кря");
    }
}
