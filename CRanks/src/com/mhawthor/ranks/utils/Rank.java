package com.mhawthor.ranks.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Rank {

    private String groupName;
    private long level;
    private short id;
    private float exp;
    private long money;
    private ArrayList<ItemStack> items = new ArrayList<ItemStack>();
    private String name;
    private int playerKill;
    private int mobKill;

    public Rank(String name, String groupName, long level, long money, float exp, ArrayList<ItemStack> items,int playerKill,int mobKill) {
        this.level = level;
        this.groupName = groupName;
        this.money = money;
        this.exp = exp;
        this.items = items;
        this.name = name;
        this.playerKill = playerKill;
        this.mobKill = mobKill;
    }

    public int getPlayerKill() {
        return playerKill;
    }

    public void setPlayerKill(int playerKill) {
        this.playerKill = playerKill;
    }

    public int getMobKill() {
        return mobKill;
    }

    public void setMobKill(int mobKill) {
        this.mobKill = mobKill;
    }

    public Rank() {
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setId(short id) {
        this.id = id;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(ItemStack item) {
        this.items.add(item);
    }

    @Deprecated
    public void addItems(ItemStack... items) {
        for (ItemStack item : items) {
            this.items.add(item);
        }
    }

    public boolean levelIsNull() {
        return !(this.level >= 0);
    }

    public boolean groupNameIsNull() {
        return this.groupName == null;
    }

    public boolean idIsNull() {
        return (this.id == 0);
    }

    public boolean moneyIsNull() {
        return (this.money == 0);
    }

    public boolean expIsNull() {
        return (this.exp == 0.0f);
    }

    @Deprecated
    public boolean isOkay() {

        return !(this.level >= 0) && !(this.groupName == null) && !(this.id >= 0);
    }


    public long getLevel() {
        return this.level;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getName() {
        return this.name;
    }


    public short getId() {
        return this.id;
    }

    public float getExp() {
        return this.exp;
    }

    public long getMoney() {
        return this.money;
    }

    public ArrayList<ItemStack> getItems() {
        return this.items;
    }
}
