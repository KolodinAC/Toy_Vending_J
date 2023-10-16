import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Raffle {

    private static ArrayList<Toy> toys = new ArrayList<>();
    private static PriorityQueue<Toy> prizes = new PriorityQueue<>();

    private static int idCounter = 0;

    public void addToy() {
        Scanner scan = new Scanner(System.in);
        String title;
        int frequency;
        while (true) {
            System.out.print("Введите название игрушки: ");
            title = scan.nextLine();
            if (title.isEmpty()) {
                System.out.println("Некорректный ввод! Попробуйте снова.");
                break;
            }
            System.out.print("Введите частоту выпадения игрушки в %: ");
            String value = scan.nextLine();
            if (isDigit(value)) {
                frequency = Integer.parseInt(value);
                if (frequency <= 0 || frequency > 100) {
                    System.out.println("Некорректный ввод! Попробуйте снова.");
                } else {
                    Toy toy = new Toy(idCounter, title, frequency);
                    if (!toys.contains(toy) || toys.size() == 0) {
                        idCounter++;
                        toys.add(toy);
                        System.out.println("Новая игрушка добавлена!");
                    } else {
                        System.out.println("Данная игрушка уже участвует в розыгрыше!");
                    }
                }
            } else {
                System.out.println("Некорректный ввод! Попробуйте снова.");
            }
            break;
        }
    }

    public void setFrequency() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите ID игрушки: ");
        String value = scan.nextLine();
        if (isDigit(value)) {
            int selectedId = Integer.parseInt(value);
            if (selectedId >= 0 && selectedId < toys.size()) {
                System.out.println("Игрушка " + toys.get(selectedId).getToyTitle() +
                        " имеет частоту выпадения " + toys.get(selectedId).getToyVictoryFrequency());
                System.out.print("Введите новую частоту выпадения: ");
                value = scan.nextLine();
                if (isDigit(value)) {
                    int newFrequency = Integer.parseInt(value);
                    toys.get(selectedId).setToyVictoryFrequency(newFrequency);
                    System.out.println("Частота выпадения была изменена");
                } else {
                    System.out.println("Некорректный ввод! Попробуйте снова.");
                }
            } else {
                System.out.println("Игрушки с таким ID в розыгрыше нет!");
            }
        } else {
            System.out.println("Некорректный ввод! Попробуйте снова.");
        }
    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Toy getPrize() {
        if (prizes.size() == 0) {
            Random rnd = new Random();
            for (Toy toy : toys) {
                for (int i = 0; i < toy.getToyVictoryFrequency(); i++) {
                    Toy temp = new Toy(toy.getToyId(), toy.getToyTitle(), rnd.nextInt(1, 10));
                    prizes.add(temp);
                }
            }
        }
        return prizes.poll();
    }

    public void raffle() {
        if (toys.size() >= 2) {
            Toy prize = getPrize();
            System.out.println("Поздравляем! Вы выиграли: " + prize.getToyTitle());
            saveResult(prize.getInfo());
        } else {
            System.out.println("Неоходимо добавить как минимум 2 игрушки в розыгрыш!");
        }
    }

    private void saveResult(String text) {
        File file = new File("Result.txt");
        try {
            file.createNewFile();
        } catch (Exception ignored) {
            throw new RuntimeException();
        }
        try (FileWriter fw = new FileWriter("Result.txt", true)) {
            fw.write(text + "\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}