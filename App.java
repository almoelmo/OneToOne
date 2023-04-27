package org.example;

import jakarta.persistence.Query;
import org.example.model.*;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        //Инициализация данных в виде POJO-классов
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setName("Dough");
        p2.setName("Sauce");
        p1.setRemain(2);
        p2.setRemain(10);
        Order order1 = new Order();
        Waiter waiter1 = new Waiter();
        Table table1 = new Table();
        Table table2 = new Table();
        Food food1 = new Food();
        Food food2 = new Food();
        List<Food> foods = new LinkedList<>();
        food1.setName("Pizza");
        food2.setName("Pasta");
        food1.setPrice(123.4);
        food2.setPrice(444.4);
        foods.add(food1);
        foods.add(food2);
        order1.setFoods(foods);
        table1.setSeats(6);
        table1.setService("Economy");
        table2.setSeats(4);
        table2.setService("VIP");
        waiter1.setName("Ivan");
        waiter1.setGender("male");
        order1.setWaiter(waiter1);
        order1.setTable(table1);
        table1.setOrder(order1);
        Order order2 = new Order();
        Waiter waiter2 = new Waiter();
        waiter2.setName("Fedor");
        waiter2.setGender("male");
        order2.setWaiter(waiter2);
        order2.setTable(table2);
        order2.setFoods(foods.subList(0, 1));
        table2.setOrder(order2);
        food1.setOrder(order1);
        food2.setOrder(order2);
        Set<Product> productsForFood1 = new HashSet<>();
        Set<Product> productsForFood2 = new HashSet<>();
        productsForFood1.add(p1);
        productsForFood1.add(p2);
        productsForFood2.add(p1);
        productsForFood2.add(p2);
        food1.setProducts(productsForFood1);
        food2.setProducts(productsForFood2);
        Transaction transaction = null;

        //Вставка данных в таблицы
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // начало транзакции
            transaction = session.beginTransaction();
            // сохраняем объекты
            session.persist(waiter1);
            session.persist(waiter2);
            session.persist(table1);
            session.persist(table2);
            session.persist(order1);
            session.persist(order2);
            session.persist(food1);
            session.persist(food2);
            session.persist(p1);
            session.persist(p2);
            // завершаем транзакцию
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        //проверка односторонней связи "один к одному" (заказ знает своего официанта)
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Order> orders = session.createQuery("from orders", Order.class).list();
            List<Waiter> waiters = session.createQuery("from waiters", Waiter.class).list();
            List<Table> tables = session.createQuery("from tables", Table.class).list();
            List<Food> foodes = session.createQuery("from foods", Food.class).list();
            System.out.println("Заказы");
            orders.forEach(o -> {
                System.out.println(o);
                System.out.println("Имя официанта для заказа: " + o.getWaiter().getName());
            });
            System.out.println("Официанты");
            waiters.forEach(System.out::println);
            System.out.println("Столики");
            tables.forEach(System.out::println);
            System.out.println("Еда");
            foodes.forEach(System.out::println);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
