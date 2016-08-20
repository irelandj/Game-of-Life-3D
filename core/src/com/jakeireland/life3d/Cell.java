package com.jakeireland.life3d;

/**
 * Created by Jake on 18/08/2016.
 */
public class Cell {
    private boolean alive;
    private int posX;
    private int posY;
    private int posZ;

    private Grid grid;

    public Cell(boolean alive, int posX, int posY, int posZ, Grid grid) {

        this.alive = alive;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.grid = grid;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean value) {
        alive = value;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() { return posZ; }

    public int numberOfAliveCellsAround() {
        int numberOfAliveAround = 0;
        for (int z = -1; z <= 1; z++) {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    Cell neighbour = grid.getCellInGrid(posX + x, posY + y, posZ + z);
                    if (neighbour != null && !(x == 0 && y == 0) && neighbour.alive) {
                        numberOfAliveAround++;
                    }
                }
            }
        }
        return numberOfAliveAround;
    }
}
