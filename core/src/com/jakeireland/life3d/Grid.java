package com.jakeireland.life3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.Random;

/**
 * Created by Jake on 18/08/2016.
 */
public class Grid {
    private int xLength;
    private int yLength;
    private int zLength;
    private Cell[][][] coords;
    ModelBuilder modelBuilder;


    public Grid(int xLength, int yLength, int zLength) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.zLength = zLength;
        coords = new Cell[xLength][yLength][zLength];
        modelBuilder = new ModelBuilder();
        randomizeAndFillGrid();

    }

    public void randomizeAndFillGrid() {
        Random rand = new Random();
        for (int z = 0; z < zLength; z++) {
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    coords[x][y][z] = new Cell(rand.nextInt(5) == 0, x, y, z, this); // for random seed use rand.nextInt(10) == 0
                }
            }
        }
    }

    public Cell[][][] modulate() {
        Cell[][][] coords2 = new Cell[xLength][yLength][zLength];
        int currentCellNumber = 0;
        int numberOfAliveAround;
        for (int z = 0; z < zLength; z++) {
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    coords2[x][y][z] = new Cell(false, x, y, z, this);
                    if (coords[x][y][z].isAlive()) {
                        currentCellNumber++;
                    }
                }
            }
        }

        for (int z = 0; z < zLength; z++) {
            for (int y = 0; y < yLength; y++) {
                for (int x = 0; x < xLength; x++) {
                    numberOfAliveAround = coords[x][y][z].numberOfAliveCellsAround();
                    if (coords[x][y][z].isAlive()) {
                        if (numberOfAliveAround < 4) {
                            coords2[x][y][z].setAlive(false);
                        } else if (numberOfAliveAround == 4 || numberOfAliveAround == 5) {
                            coords2[x][y][z].setAlive(true);
                        } else if (numberOfAliveAround > 5) {
                            coords2[x][y][z].setAlive(false);
                        }
                    } else {
                        if (numberOfAliveAround == 5) {
                            coords2[x][y][z].setAlive(true);
                        }
                    }
                }
            }
        }
        if (currentCellNumber == 0) {
            Random rand = new Random();
            for (int z = 0; z < zLength; z++) {
                for (int y = 0; y < yLength; y++) {
                    for (int x = 0; x < xLength; x++) {
                        coords2[x][y][z] = new Cell(rand.nextInt(5) == 0, x, y, z, this); // for random seed use rand.nextInt(10) == 0
                    }
                }
            }
        }
        return coords2;
    }

    public Cell getCellInGrid(int x, int y, int z) {
        if (x < 0 || y < 0 || x > xLength -1 || y > yLength -1 || z < 0 || z > zLength -1)
            return null;

        return coords[x][y][z];
    }

    public Cell[][][] getCoords() {
        return coords;
    }

    public void setCoords(Cell[][][] value) {
        coords = value;
    }
}
