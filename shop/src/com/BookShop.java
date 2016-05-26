package com;

import com.entities.Book;
import com.entities.Client;
import com.entities.Order;
import com.exceptions.OrderCreationException;
import com.exceptions.WrongInformationException;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class BookShop {

    private Database database;

    public BookShop() {
        database = new Database();
        init();
    }

    public Database getDatabase() {
        return database;
    }

    public void registerUser(String firstName, String lastName, String number, String address, String email,
                             String login, String password) throws WrongInformationException {
        database.addClient(new Client(firstName, lastName, number, address, email, login, password));
    }

    public Client loginUser(String login, String password) throws WrongInformationException {
        for (Client client : database.getClients().values()) {
            if (client.getLogin().equals(login) && client.getPassword().equals(password)) {
                return client;
            }
        }
        throw new WrongInformationException("Wrong username or password.");
    }

    public boolean changePass(Integer id, String newPass) {
        Client c = database.getClient(id);
        if (c.getPassword().equals(newPass)) {
            return false;
        }
        else {
            c.setPassword(newPass);
            return true;
        }
    }

    public boolean changeAddress(Integer id, String newAddress) {
        Client c = database.getClient(id);
        c.setAddress(newAddress);
        return true;
    }

    public Order createOrder(Integer clientId, LinkedList<Book.ISBN> bookISBNs, String destination, Date deliveryDate)
            throws OrderCreationException {
        Client c = database.getClient(clientId);
        LinkedList<Book> books = new LinkedList<Book>();
        Double price = 0.0;
        for (Book.ISBN bookISBN : bookISBNs) {
            Book b;
            books.add(b = database.getBook(bookISBN));
            price += b.getPrice();
        }
        Order o;
        database.addOrder((o = new Order(c, books, destination, price, new Date(), deliveryDate)));
        c.getOrders().add(o.getId());
        return o;
    }

    public void cancelOrder(Integer id) {
        Order o;
        if ((o = database.getOrder(id)).getStatus() == Order.EStatus.PENDING) {
            o.setStatus(Order.EStatus.CANCELED);
            return;
        }
        if (o.getStatus() == Order.EStatus.CANCELED || o.getStatus() == Order.EStatus.COMPLETED){
            return;
        }
        for (Book book : o.getBooks()) {
            database.addBook(book);
        }
        o.setStatus(Order.EStatus.CANCELED);
    }

    public void completeOrder(Integer id) {
        database.getOrder(id).setStatus(Order.EStatus.COMPLETED);
    }

    public boolean confirmOrder(Integer id) throws OrderCreationException {
        Order o;
        (o = database.getOrder(id)).setStatus(Order.EStatus.PERFORMING);
        for (Book book : o.getBooks()) {
            if (database.removeBook(book.getIsbn()) == null) {
                return false;
            }
        }
        return true;
    }

    private void init() {
        try {

            database.addClient(new Client("test", "test", "test", "test", "test@test.ru", "test",
                    "test"));
            database.addClient(new Client("test1", "test1", "test1", "test1", "test1@test.ru", "test1",
                    "test1"));
            database.addClient(new Client("Ivan", "Ivanov", "+7(777)777-77-77", "Moscow", "ivanov777@ya.ru", "ivan",
                    "ivan"));

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            for (int i = 0; i < 1000; i++) {
                database.addBook(new Book("Капитанская дочка", "А.С. Пушкин", "В своих руках Вы держите, дорогой " +
                        "читатель, " +
                        "лучшее в истории книгопечатания коллекционное издание произведений А.С.Пушкина. Несмотря на " +
                        "роскошное оформление, книга выглядит очень живо и естестевенно, как и само творчество любимого " +
                        "поэта России. Выразительнось и легкость изданию придают и иллюстрации, выполненные известными " +
                        "художниками.", 67615.0, 584, 1500, new Book.ISBN(978, 5, 7827, 87, 4), "Интрейд Корпорейшн",
                        formatter.parse("23-11-2011"), new File("com/bookcovers/gjew17tv.bmp")));
                database.addBook(new Book("Бесогон. Россия между прошлым и будущим", "Никита Михалков", "Книга написана " +
                        "по мотивам авторской программы Никиты Михалкова \"Бесогон-ТВ\", каждый выпуск которой на " +
                        "федеральном канале Россия 24 собирает много миллионную зрительскую аудиторию. В этой книге " +
                        "автор возвращается к историческим аспектам развития России, дает личную оценку социальных и " +
                        "политических процессов, происходящих как внутри нашей страны, так и в мире.",
                        655.0, 446, 10000, new Book.ISBN(978, 5, 699, 88589, 3), "Э",
                        formatter.parse("25-12-2014"), new File("com/bookcovers/4e3eesvg.bmp")));
                database.addBook(new Book("Алгоритмизация комбинаторно-оптимизационных задач при проектировании ЭВМ и " +
                        "систем", "Владимир Овчинников", "Рассмотрены вопросы алгоритмизации комбинаторно-" +
                        "оптимизационных задач структурного синтеза на графах. Большое внимание уделено формализации " +
                        "таких задач и методам их решения, основанныи на идее отсечения, ветвей и границ, поиска в " +
                        "глубину, в ширину, двоичной свертки.",
                        1055.0, 287, 10000, new Book.ISBN(978, 5, 7038, 1872, 5), "МГТУ им.Баумана",
                        formatter.parse("12-12-2007"), new File("com/bookcovers/m4iz7isa.bmp")));
                database.addBook(new Book("Преступление и наказание", "Федор Достоевский", "Социально-философский роман," +
                        " оказавший громадное влияние на мировую литературу, остается одной из самых актуальных книг " +
                        "современности, ибо ставит проблемы нравственного выбора. Может ли человек не жить по общим " +
                        "законам? Имеет ли право совершать преступления ради благой цели? Где грань между любовью и " +
                        "настоящим самопожертвованием?", 2610.0
                        , 687, 1500, new Book.ISBN("978-5-9680-24-8"), "Пан пресс",
                        formatter.parse("10-03-2008"), new File("com/bookcovers/7cih2rto.bmp")));
                database.addBook(new Book("Полное собрание сочинений. В 13 т. Т.4. Так говорил Заратустра. " +
                        "Книга для всех и ни для кого", "Фридрих Ницше", "Четвертый том содержит центральное " +
                        "произведение Ф.Ницше" +
                        " \"Так говорил Заратустра. Книга для всех и ни для кого\" (части I-IV), созданное в период " +
                        "1883-1885 гг.", 480.0, 432, 2500, new Book.ISBN("978-5-250-6018-9"), "Культурная Революция",
                        formatter.parse("01-03-2008"), new File("com/bookcovers/lh9bj7hh.bmp")));
                database.addBook(new Book("Хаджи-Мурат", "Лев Толстой", "Произведения Л.Н.Толстого по праву " +
                        "считаются шедеврами не только русской, но и мировой литературы. В настоящее издание " +
                        "вошли \"Севастопольские рассказы\", повесть \"Хаджи-Мурат\" и другие выдающиеся " +
                        "произведения. ", 410.0, 703, 5000, new Book.ISBN("978-5-7793-1867-9"), "Культурная Революция",
                        formatter.parse("23-12-2012"), new File("com/bookcovers/j3i77a1u.bmp")));
                database.addBook(new Book("Стихотворения : в 3 т.", "Сергей Есенин", "В трехтомное миниатюрное " +
                        "издание вошли самые значительные стихотворные произведения великого русского поэта Сергея " +
                        "Есенина.", 760.0, 201, 5000, new Book.ISBN("978-5-7034-143-9"), "Культурная Революция",
                        formatter.parse("23-12-2012"), new File("com/bookcovers/0v8tq4n3.bmp")));
                database.addBook(new Book("Теория управления", "Геннадий Леонов", "Книга представляет собой курс лекций" +
                        " по теории управления. Она предназначена для первоначального изучения теории управления. " +
                        "В ней излагаются основные понятия и методы классической теории. В книге показано, как " +
                        "проблемы анализа и синтеза конкретных систем управления явились мотивацией к созданию " +
                        "математического аппарата, с помощью которого решались эти проблемы.", 885.0, 233, 2500,
                        new Book.ISBN("978-5-288-3885-3"), "Издательский дом Санкт-Петербургского университета",
                        formatter.parse("23-12-2007"), new File("com/bookcovers/54v91cdh.bmp")));
                database.addBook(new Book("Почему не работают команды? : Что идет не так, и как это исправить",
                        "Х. Роббинс, М. Финли", "Командная работа - одна из \"священных коров\" современного " +
                        "менеджмента. \"Мы - одна команда!\" - эту мантру заучивают и твердят рядовые сотрудники, " +
                        "она ласкает слух менеджеров и приводит в восторг руководителей компаний. Консультанты и " +
                        "тренеры зарабатывают целые состояния, помогая топ-менеджерам \"строить команды\" и " +
                        "укреплять \"командный дух\" сотрудников на тренингах в экстремальных условиях.", 200.0, 304,
                        1500,
                        new Book.ISBN("978-5-98124-44-7"), "Добрая книга",
                        formatter.parse("21-02-2008"), new File("com/bookcovers/36rgvwfm.bmp")));
                database.addBook(new Book("Интеллектуальные системы",
                        "Е. В. Абрамов, П. В. Афонин, А. В. Барлит и др.", "Настоящее издание представляет собой " +
                        "коллективную монографию, включающую результаты научных исследований, которые были " +
                        "рекомендованы к печати на Международных научно-технических конференциях \"Интеллектуальные " +
                        "системы\" (IEEE AIS) и \"Интеллектуальные САПР\" (CAD).", 890.0, 287,
                        200,
                        new Book.ISBN("978-5-9221-510-1"), "Физматлит",
                        formatter.parse("30-01-2010"), new File("com/bookcovers/eytmy3q7.bmp")));
            }

            LinkedList<Book.ISBN> list = new LinkedList<Book.ISBN>();
            for (int i = 0; i < 356; i++) {
                list.add(new Book.ISBN(978, 5, 7827, 87, 4));
                list.add(new Book.ISBN("978-5-98124-44-7"));
                list.add(new Book.ISBN(978, 5, 699, 88589, 3));
                list.add(new Book.ISBN("978-5-7793-1867-9"));
                list.add(new Book.ISBN(978, 5, 250, 6018, 9));
            }
            for (int i = 0; i < 123; i++) {
                list.add(new Book.ISBN("978-5-7034-143-9"));
                list.add(new Book.ISBN("978-5-288-3885-3"));
            }
            for (int i = 0; i < 253; i++) {
                list.add(new Book.ISBN("978-5-98124-44-7"));
                list.add(new Book.ISBN("978-5-9221-510-1"));
                list.add(new Book.ISBN("978-5-7793-1867-9"));
            }
            for (int i = 0; i < 125; i++) {
                list.add(new Book.ISBN("978-5-9221-510-1"));
                list.add(new Book.ISBN(978, 5, 699, 88589, 3));
                list.add(new Book.ISBN(978, 5, 9680, 24, 8));
            }
            for (int i = 0; i < 179; i++) {
                list.add(new Book.ISBN(978, 5, 7038, 1872, 5));
                list.add(new Book.ISBN(978, 5, 7827, 87, 4));
                list.add(new Book.ISBN("978-5-288-3885-3"));
            }

            Order o = createOrder(2, list, "MSC", new Date());
            confirmOrder(o.getId());

            for (Book book : database.getBooks().values()) {
                System.out.println(book);
            }
        } catch(WrongInformationException | ParseException | OrderCreationException e) {
            e.printStackTrace();
        }
    }

}
