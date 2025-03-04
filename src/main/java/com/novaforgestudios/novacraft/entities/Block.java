package com.novaforgestudios.novacraft.entities;

public class Block {

    public enum Type {
        STONE, DIRT, GRASS, AIR
    }

    private Type type;

    public Block(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}