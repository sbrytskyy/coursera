import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;
    private double[][] energy;

    public SeamCarver(Picture picture) { // create a seam carver object based on the given picture

        if (picture == null)
            throw new IllegalArgumentException();

        this.picture = new Picture(picture);
        this.energy = new double[picture.width()][picture.height()];
        buildEnergyMatrix();
    }

    private int[][] getColors() {
        int width = picture.width();
        int height = picture.height();

        int[][] colors = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colors[x][y] = picture.getRGB(x, y);
            }
        }
        return colors;
    }

    private void buildEnergyMatrix() {
        int width = picture.width();
        int height = picture.height();

        int[][] colors = getColors();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                recalculateEnergy(x, y, colors);
            }
        }
    }

    private void recalculateEnergy(int x, int y, int[][] colors) {
        int width = picture.width();
        int height = picture.height();

        if (y < 0 || y >= height || x < 0 || x >= width) {
            return;
        }

        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
            energy[x][y] = 1000.0;
            return;
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

    public Picture picture() { // current picture
        return picture;
    }

    public int width() { // width of current picture
        return picture.width();
    }

    public int height() { // height of current picture
        return picture.height();
    }

    public double energy(int x, int y) { // energy of pixel at column x and row y
        int width = picture.width();
        int height = picture.height();

        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new IllegalArgumentException();
        }
        return energy[x][y];
    }

    public int[] findHorizontalSeam() { // sequence of indices for horizontal seam
        int width = picture.width();
        int height = picture.height();

        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0) {
                    distTo[x][y] = energy[x][y];
                    continue;
                }
                distTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height; y++) {
                relaxH(x, y, distTo, edgeTo);
            }
        }

        double minDist = Double.POSITIVE_INFINITY;
        int minY = 0;

        for (int y = 0; y < height; y++) {
            if (Double.compare(distTo[width - 1][y], minDist) < 0) {
                minY = y;
                minDist = distTo[width - 1][y];
            }
        }

        int[] seam = new int[width];

        int y = minY;
        for (int x = width - 1; x >= 0; x--) {
            seam[x] = y;
            y = y - edgeTo[x][y];
        }
        return seam;
    }

    private void relaxH(int x, int y, double[][] distTo, int[][] edgeTo) {
        int height = picture.height();

        for (int index = y - 1; index <= y + 1; index++) {
            if (index < 0 || index >= height) {
                continue;
            }

            if (distTo[x + 1][index] > distTo[x][y] + energy[x + 1][index]) {
                distTo[x + 1][index] = distTo[x][y] + energy[x + 1][index];
                edgeTo[x + 1][index] = index - y;
            }
        }
    }

    public int[] findVerticalSeam() { // sequence of indices for vertical seam
        int width = picture.width();
        int height = picture.height();

        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0) {
                    distTo[x][y] = energy[x][y];
                    continue;
                }
                distTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                relaxV(x, y, distTo, edgeTo);
            }
        }

        double minDist = Double.POSITIVE_INFINITY;
        int minX = 0;

        for (int x = 0; x < width; x++) {
            if (Double.compare(distTo[x][height - 1], minDist) < 0) {
                minX = x;
                minDist = distTo[x][height - 1];
            }
        }

        int[] seam = new int[height];

        int x = minX;
        for (int y = height - 1; y >= 0; y--) {
            seam[y] = x;
            x = x - edgeTo[x][y];
        }
        return seam;
    }

    private void relaxV(int x, int y, double[][] distTo, int[][] edgeTo) {
        int width = picture.width();

        for (int index = x - 1; index <= x + 1; index++) {
            if (index < 0 || index >= width) {
                continue;
            }

            if (distTo[index][y + 1] > distTo[x][y] + energy[index][y + 1]) {
                distTo[index][y + 1] = distTo[x][y] + energy[index][y + 1];
                edgeTo[index][y + 1] = index - x;
            }
        }
    }

    public void removeHorizontalSeam(int[] seam) { // remove horizontal seam from current picture
        int width = picture.width();
        int height = picture.height();

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

        int[][] colors = getColors();

        for (int x = 0; x < width; x++) {
            int y = seam[x];

            for (int i = y; i < height - 1; i++) {
                colors[x][i] = colors[x][i + 1];
                energy[x][i] = energy[x][i + 1];
            }
        }

        for (int x = 0; x < width; x++) {
            int y = seam[x];
            recalculateEnergy(x, y - 1, colors);
            recalculateEnergy(x, y, colors);
            recalculateEnergy(x, y + 1, colors);
        }

        height--;

        this.picture = new Picture(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                picture.setRGB(x, y, colors[x][y]);
            }
        }
    }

    public void removeVerticalSeam(int[] seam) { // remove vertical seam from current picture
        int width = picture.width();
        int height = picture.height();

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

        int[][] colors = getColors();

        for (int y = 0; y < height; y++) {
            int x = seam[y];

            for (int i = x; i < width - 1; i++) {
                colors[i][y] = colors[i + 1][y];
                energy[i][y] = energy[i + 1][y];
            }
        }

        for (int y = 0; y < height; y++) {
            int x = seam[y];
            recalculateEnergy(x - 1, y, colors);
            recalculateEnergy(x, y, colors);
            recalculateEnergy(x + 1, y, colors);
        }

        width--;

        this.picture = new Picture(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                picture.setRGB(x, y, colors[x][y]);
            }
        }
    }
}
