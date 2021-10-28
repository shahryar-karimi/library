package ir.shahryar.library.packs;

import ir.shahryar.library.Exception.TimeAlreadySetException;

import java.util.Date;

public enum Pack {
    Gold(120),
    Silver(90),
    Bronze(60);

    public int length;
    public int bookCount;
    private final double price;
    private long date;

    Pack(int length) {
        this(length, calculateBookCount(length) , calculatePrice(length));
    }

    Pack(int length, int bookCount, double price) {
        this.length = length;
        this.bookCount = bookCount;
        this.price = price;
    }

    public static int calculateBookCount(double length) {
        return (int) ((length * length / 120) - (0.75 * length) + 30);
    }

    public static double calculatePrice(double length) {
        return (length * length / 15) - (7 * length) + 240;
    }

    public double getPrice() {
        return price;
    }

    public void setDate() throws TimeAlreadySetException {
        if (date == 0) {
            date = System.currentTimeMillis();
        } else {
            throw new TimeAlreadySetException();
        }
    }

    public String getDate() {
        return new Date(date).toString();
    }

    public String getExpireDate() {
        long expireTimeMillis = getExpireTimeMillis();
        return new Date(expireTimeMillis).toString();
    }

    public long getExpireTimeMillis() {
        return date + ((long) length) * 24 * 60 * 60 * 1000;
    }

    public int getRemainDays() {
        long now  = System.currentTimeMillis();
        long remain = getExpireTimeMillis() - now;
        return (int) (remain / (1000 * 60 * 60 * 24));
    }
}