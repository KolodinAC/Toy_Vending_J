import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Raffle r = new Raffle();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("""
                    Выберите действие:
                    1 - Добавить новую игрушку в розыгрыш
                    2 - Изменить частоту выпадения игрушки
                    3 - Провести розыгрыш и сохранить результат
                    0 - Выход
                    >\s""");
            var selection = sc.next();
            switch (selection) {
                case "1" -> r.addToy();
                case "2" -> r.setFrequency();
                case "3" -> r.raffle();
                case "0" -> {
                    System.out.println("До новых встреч!");
                    System.exit(0);
                }
                default -> System.out.println("Выбран некорректный вариант. Попробуйте снова!");
            }
        }
    }
}
