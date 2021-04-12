import java.util.Arrays;

public class Q3Solver {
    public static void main(String[] args) {

        char[][] input = new char[10][10];
        int fLenght = 10;
        
        for (int i = 0; i < input.length;i++){
            Arrays.fill(input[i],'#');
        }
        input[0][(int)(Math.random() * input.length + 1)] = 'S';


















        for(int i = 0; i < input.length;i++){
            System.out.println(Arrays.toString(input[i]));
        }

    }





















    private static void solver() {
        char[][] input = {
                {'#', 'S', '#'},
                {'#', 'O', 'O'},
                {'O', '#', 'O'},
                {'O', '#', 'O'},
                {'#', '#', 'O'},
                {'#', 'F', 'O'},
        };

        int moves = 0;
        int[] yPosition = findPosition(input, 'S');


        while (true) {
            try {
                if (input[yPosition[0] + 1][yPosition[1]] == 'O' || input[yPosition[0] + 1][yPosition[1]] == 'F') {
                    input[yPosition[0]][yPosition[1]] = '#';
                    yPosition[0]++;
                    moves++;

                } else if (input[yPosition[0]][yPosition[1] - 1] == 'O' || input[yPosition[0]][yPosition[1] - 1] == 'F') {
                    input[yPosition[0]][yPosition[1]] = '#';
                    yPosition[1]--;
                    moves++;
                } else if (input[yPosition[0]][yPosition[1] + 1] == 'O' || input[yPosition[0]][yPosition[1] + 1] == 'F') {
                    input[yPosition[0]][yPosition[1]] = '#';
                    yPosition[1]++;
                    moves++;
                }
            } catch (Exception e) {
                System.out.println(moves);
                break;
            }


            System.out.println(Arrays.toString(yPosition));


        }

    }

    private static int[] findPosition(char[][] input, char letter) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == letter) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
