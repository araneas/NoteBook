import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class NoteBook {

    private static final String FILE_PATH = "NoteBook.txt";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
            __________________________________________________________________
            Введите одну из трех команд для продолжения работы с ежедневником: 
            "#read" - посмотреть существующие записи
            "#write" - добавить новую запись
            "#exit" - выйти из ежедневника       
            """);

            String userInput = scanner.nextLine().trim();
            if ("#read".equalsIgnoreCase(userInput)) {
                read();
            } else if ("#write".equalsIgnoreCase(userInput)) {
                System.out.println("Добавьте новую запись:");
                String Text = scanner.nextLine().trim();
                write(Text);
            } else if ("#exit".equalsIgnoreCase(userInput)) {
                System.out.println("Выполнение программы остановлено.");
                scanner.close();
                break;
            } else {
                System.out.println("Команда введена не верно.");
            }
        }
    }


    private static void write(String entry) {
        String timeStamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String record = timeStamp + " - " + entry;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(record);
            writer.newLine();
            System.out.println("Запись добавлена.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }


    private static void read() {
        Path NoteBookPath = Paths.get(FILE_PATH);
        try {
            if (Files.notExists(NoteBookPath) || Files.size(NoteBookPath) == 0) {
                System.out.println("Записи отсутствуют");
            } else {
                List<String> entries = Files.readAllLines(NoteBookPath);
                entries.forEach(System.out::println);
            }
        } catch (IOException ioEx) {
            System.err.println("Произошла ошибка при чтении файла: " + ioEx.getMessage());
        }
    }
}
