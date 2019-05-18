import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private final Picture picture;
    private final int[][] colors;
    private final double[][] energy;
    private final int width;
    private final int height;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture); // create a seam carver object based on the given picture
        width = picture.width();
        height = picture.height();
        this.colors = new int[width][height];
        this.energy = new double[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colors[x][y] = picture.getRGB(x, y);
            }
        }

        buildEnergyMatrix();
    }

    private void buildEnergyMatrix() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    energy[x][y] = 1000.0;
                    continue;
                }

                int rx = ((colors[x - 1][y] >> 16) & 0xFF) - ((colors[x + 1][y] >> 16) & 0xFF);
                int gx = ((colors[x - 1][y] >> 8) & 0xFF) - ((colors[x + 1][y] >> 8) & 0xFF);
                int bx = ((colors[x - 1][y] >> 0) & 0xFF) - ((colors[x + 1][y] >> 0) & 0xFF);

                long deltaX = rx * rx + gx * gx + bx * bx;

                int ry = ((colors[x][y - 1] >> 16) & 0xFF) - ((colors[x][y + 1] >> 16) & 0xFF);
                int gy = ((colors[x][y - 1] >> 8) & 0xFF) - ((colors[x][y + 1] >> 8) & 0xFF);
                int by = ((colors[x][y - 1] >> 0) & 0xFF) - ((colors[x][y + 1] >> 0) & 0xFF);

                long deltaY = ry * ry + gy * gy + by * by;

                energy[x][y] = Math.sqrt((double) deltaX + deltaY);
            }
        }
    }

    public Picture picture() { // current picture
        return this.picture;
    }

    public int width() { // width of current picture
        return width;
    }

    public int height() { // height of current picture
        return height;
    }

    public double energy(int x, int y) { // energy of pixel at column x and row y
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new IllegalArgumentException();
        }
        return energy[x][y];
    }

    public int[] findHorizontalSeam() { // sequence of indices for horizontal seam
        return null;
    }

    public int[] findVerticalSeam() { // sequence of indices for vertical seam
        return null;
    }

    public void removeHorizontalSeam(int[] seam) { // remove horizontal seam from current picture
    }

    public void removeVerticalSeam(int[] seam) { // remove vertical seam from current picture
    }
}
