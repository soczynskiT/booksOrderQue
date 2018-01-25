import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

class Customer {
    private String fullName;

    public Customer(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}

class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "\"" + title + "\" written by: " + author;
    }
}

class Order {
    private Book book;
    private Customer customer;
    private LocalDate dateOfOrder;
    private int year;
    private int month;
    private int day;

    public Order(Customer customer, Book book, int year, int month, int day) {
        this.customer = customer;
        this.book = book;
        this.dateOfOrder = LocalDate.of(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getOrderIndex() {
        return this.year * 10000 + this.month * 100 + this.day;
    }


    @Override
    public String toString() {
        return book + " odered by " + customer + " at: " + dateOfOrder;
    }
}

class OrdersQueSorter {
    public Deque<Order> sortOrdersQue(Deque<Order> ordersQue) {
        final Deque<Order> queSorted = new ArrayDeque<>();
        Order temp = ordersQue.pollFirst();
        queSorted.offer(temp);
        while (!ordersQue.isEmpty()) {
            if (ordersQue.peekFirst().getOrderIndex() == queSorted.peekLast().getOrderIndex()) {
                temp = ordersQue.pollFirst();
                queSorted.offer(temp);
            } else {
                if (ordersQue.peekFirst().getOrderIndex() > queSorted.peekLast().getOrderIndex()) {
                    temp = ordersQue.pollFirst();
                    queSorted.offer(temp);
                } else {
                    temp = ordersQue.pollFirst();
                    while (!queSorted.isEmpty()) {
                        ordersQue.offerFirst(queSorted.pollLast());
                    }
                    queSorted.offer(temp);
                }
            }
        }
        return queSorted;
    }
}

class BooksApp {
    public static void main(String[] args) {
        final OrdersQueSorter sorter = new OrdersQueSorter();

        final Customer customer1 = new Customer("Jan Kowalski");
        final Customer customer2 = new Customer("Anna Nowak");
        final Customer customer3 = new Customer("Jerzy Ziemba");

        final Book book1 = new Book("Some title no 1", "Some author no 1");
        final Book book2 = new Book("Some title no 2", "Some author no 2");
        final Book book3 = new Book("Some title no 3", "Some author no 3");

        final Order order1 = new Order(customer1, book2, 2018, 10, 11);
        final Order order2 = new Order(customer1, book3, 2018, 10, 11);
        final Order order3 = new Order(customer2, book1, 2018, 10, 30);
        final Order order4 = new Order(customer2, book2, 2017, 12, 15);
        final Order order5 = new Order(customer2, book3, 2017, 12, 18);
        final Order order6 = new Order(customer3, book3, 2017, 12, 30);

        final Deque<Order> ordersQue = new ArrayDeque<>();
        ordersQue.offer(order1);
        ordersQue.offer(order2);
        ordersQue.offer(order3);
        ordersQue.offer(order4);
        ordersQue.offer(order5);
        ordersQue.offer(order6);
        for (Order anOrder : ordersQue) {
            System.out.println(anOrder);
        }
        System.out.println("\nNo of orders waiting in que: " + ordersQue.size());
        System.out.println("\n\nSorting orders que from the oldest one...");
        final Deque<Order> sortedOrdersQue = sorter.sortOrdersQue(ordersQue);
        for (Order anInt : sortedOrdersQue) {
            System.out.print("\n" + anInt);
        }

        System.out.println("\n\nWe have to take care about this orders first:");
        final String order = sortedOrdersQue.peekFirst().toString();
        System.out.println(order);

        /*Exercise request:*/
        Order tempOrder;
        tempOrder = sortedOrdersQue.poll();
        for (Order anOrder : sortedOrdersQue) {
            tempOrder = sortedOrdersQue.poll();
        }
        System.out.println("\n\nLast taken order should be:\n" + tempOrder);
    }
}
