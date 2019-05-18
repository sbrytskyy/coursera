import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private final Picture picture;
    private final double[][] energy;
    private int width;
    private int height;

    public SeamCarver(Picture picture) { // create a seam carver object based on the given picture

        if (picture == null)
            throw new IllegalArgumentException();

        this.picture = new Picture(picture);
        width = picture.width();
        height = picture.height();
        int[][] colors = new int[width][height];
        this.energy = new double[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colors[x][y] = picture.getRGB(x, y);
            }
        }

        buildEnergyMatrix(colors);
    }

    private void buildEnergyMatrix(int[][] colors) {
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
        int[] seam = new int[width];
        return seam;
    }

    public int[] findVerticalSeam() { // sequence of indices for vertical seam
        int[] seam = new int[height];
        return seam;
    }

    public void removeHorizontalSeam(int[] seam) { // remove horizontal seam from current picture
        if (seam == null || seam.length != width || height <= 1) {
            throw new IllegalArgumentException();
        }

        if (seam[0] < 0 || seam[0] >= height) {
            throw new IllegalArgumentException();
        }
        for (int x = 1; x < width; x++) {
            if (seam[x] < 0 || seam[x] >= height || Math.abs(seam[x] - seam[x - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        
        height--;
    }

    public void removeVerticalSeam(int[] seam) { // remove vertical seam from current picture
        if (seam == null || seam.length != height || width <= 1) {
            throw new IllegalArgumentException();
        }
        if (seam[0] < 0 || seam[0] >= width) {
            throw new IllegalArgumentException();
        }
        for (int y = 1; y < height; y++) {
            if (seam[y] < 0 || seam[y] >= width || Math.abs(seam[y] - seam[y - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        
        width--;
    }
}
