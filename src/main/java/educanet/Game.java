package educanet;

import educanet.models.Square;
import educanet.utils.FileUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Game {

    private static Square square;
    private static Square square2;

    public static String textMazeArr = "101111111000111011101010000001\n" +
            "100000101101101100111011111111\n" +
            "110011100111000110100010000100\n" +
            "010110000000010010101110110111\n" +
            "011100111101110110111010100001\n" +
            "100011100111001100100000111011\n" +
            "100000100010011000101111101010\n" +
            "110011101110010111111000000010\n" +
            "010110100001110000001010111010\n" +
            "111100101111001111011010101011\n" +
            "001000001000001010010110101001\n" +
            "111011101011101011110100101101\n" +
            "101010101010111000001110100111\n" +
            "100010111010000111001010100001\n" +
            "111110000011001101011010101101\n" +
            "100100111101011001110001101001\n" +
            "101100100101000011011011001001\n" +
            "011001100101111010010010001011\n" +
            "110111001110001110110110011010\n" +
            "100100111011100010101100110010\n" +
            "100100000000111010101011101011\n" +
            "101111011110001011001010001001\n" +
            "100001010011001001101011001101\n" +
            "110011011101101100101001111001\n" +
            "011010000100100110101011001001\n" +
            "001011001101100010001000011001\n" +
            "111001111001011010111011110011\n" +
            "010000100001010011100010001110\n" +
            "011110001111010000000111001000\n" +
            "110011111001011111111101111111";
    public static int size = textMazeArr.length() - textMazeArr.replace("\n", "").length()+1;
    public static String mazeText = textMazeArr.replaceAll("\n", "");
    public static float squareSize = 1.0f/((float)size/2);
    public static Square[][] mazeArr = new Square[size][size];
    public static void init(long window) {

        System.out.println(squareSize);
        System.out.println(mazeText);
        float[] white = {
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
        };

        float[] black = {
                0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f,
                0.0f, 0.0f, 0.0f,
        };
        float xCord = -1.0f;
        float yCord = 1.0f-squareSize;

        int codePos = 0;
        for(int y = 0; y<size; y++) {
            for(int x = 0; x<size; x++) {
                Square square;
                if(mazeText.charAt(codePos) == '1') square = createSquare(xCord, yCord, white);
                else square = createSquare(xCord, yCord, black);
                codePos++;

                mazeArr[y][x] = square;

                xCord += squareSize;
            }
            xCord = -1.0f;
            yCord -= squareSize;
        }

        Shaders.initShaders();
    }

    private static Square createSquare(float x, float y, float[] color) {
        int[] indices = {
                0, 1, 3, // First triangle
                1, 2, 3 // Second triangle
        };

        float[] vertices = {
                x + squareSize, y + squareSize, 0.0f, // 0 -> Top right
                x + squareSize, y, 0.0f, // 1 -> Bottom right
                x, y, 0.0f, // 2 -> Bottom left
                x, y + squareSize, 0.0f, // 3 -> Top left
        };
        return new Square(vertices, indices, color);
    }

    public static void render(long window) {
        GL33.glUseProgram(Shaders.shaderProgramId);

        // Draw using the glDrawElements function
        for(int y = 0; y<size; y++) {
            for(int x = 0; x<size; x++) {
                GL33.glBindVertexArray(mazeArr[x][y].vaoId);
                GL33.glDrawElements(GL33.GL_TRIANGLES, mazeArr[x][y].indices.length, GL33.GL_UNSIGNED_INT, 0);
            }
        }

    }

    public static void update(long window) {

    }

}
