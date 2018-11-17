import java.util.*;

public class TravellingSalesFriend
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int numberOfCities = sc.nextInt();
        System.out.println("numberOfCities: " + numberOfCities);

        // Handle edge cases involving a very small number of cities
        if(numberOfCities < 3)
        {
            handleEdgeCases(numberOfCities);
        }

        else
        {
            int[][] givenFlightCosts = new int[numberOfCities][numberOfCities];
            for(int i = 0; i < numberOfCities; i++)
            {
                for(int j = 0; j < numberOfCities; j++)
                {
                    givenFlightCosts[i][j] = sc.nextInt();
                }
            }

            printArray(givenFlightCosts);
            System.out.println();


            int[][] solutions = new int[numberOfCities][numberOfCities];

            // first column
            for(int k = 0; k < numberOfCities; k++)
            {
                solutions[k][0] = -1;
            }

            // first row
            solutions[0][1] = 0;
            for(int k = 2; k < numberOfCities; k++)
            {
                solutions[0][k] = solutions[0][k-1] + givenFlightCosts[k-1][k];
            }

            // second column
            solutions[1][1] = -1;
            solutions[2][1] = givenFlightCosts[0][2];
            for(int k = 3; k < numberOfCities; k++)
            {
                solutions[k][1] = solutions[k-1][1] + givenFlightCosts[k-1][k];
            }


            for(int i = 1; i < numberOfCities; i++)
            {
                for(int j = 2; j < numberOfCities; j++)
                {
                    int tempMin = 99999;
                    if(i == j)
                    {
                        solutions[i][j] = -1;
                    }

                    else if (i < j)
                    {
                        for(int k = 1; k < i; k++)
                        {
                            if(tempMin > solutions[i][k] + givenFlightCosts[k][i+1])
                            {
                                tempMin = solutions[i][k] + givenFlightCosts[k][i+1];
                            }
                            solutions[i][j] = tempMin + solutions[0][j] - solutions[0][i+1];
                        }
                    }


                }
            }


            printArray(solutions);
        }
    }





    public static void handleEdgeCases(int numberOfCities)
    {
        if(numberOfCities <= 0)
        {
            System.out.println(0);
            System.out.println("There are no cities for me to visit.");
            System.out.println("There are no cities for my friend to visit.");
        }
        else if(numberOfCities == 1)
        {
            System.out.println(0);
            System.out.println(0);
            System.out.println("There are no cities for my friend to visit.");
        }
        else if(numberOfCities == 2)
        {
            System.out.println(0);
            System.out.println(0);
            System.out.println(1);
        }
    }




    public static void printArray(int[][] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            for(int j = 0; j < array[0].length; j++)
            {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

}
