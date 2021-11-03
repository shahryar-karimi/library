package ir.shahryar.library.user.customer;

import ir.shahryar.library.packs.Pack;
import ir.shahryar.library.user.User;
import ir.shahryar.library.util.JSONable;
import ir.shahryar.library.wallet.Wallet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer extends User implements JSONable {
    @Id
    private String id;
    private String nationalId;
    private String phoneNumber;
    private String address;
    private Pack pack;
    private Wallet wallet;

    public void init(long initialAmount) {
        wallet = new Wallet(initialAmount);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "password='" + password + '\'' +
                ", id='" + id + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public String toJson() {
        return gson.toJson(this);
    }

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
