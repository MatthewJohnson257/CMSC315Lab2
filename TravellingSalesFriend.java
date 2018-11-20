import java.util.*;

public class TravellingSalesFriend
{
    // used to track the solution and retrace through when printing who visited which cities
    static int index1 = Integer.MAX_VALUE;
    static int index2 = Integer.MAX_VALUE;


    static boolean debug = false; // set to true if you want debugging print statements


    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int numberOfCities = sc.nextInt();


        // Handle edge cases involving a very small number of cities
        if(numberOfCities < 3)
        {
            handleEdgeCases(numberOfCities);
        }

        else
        {
            // adjacency matrix of the flight costs
            int[][] givenFlightCosts = new int[numberOfCities][numberOfCities];

            // place the input into a useable 2-d array called givenFlightCosts
            for(int i = 0; i < numberOfCities; i++)
            {
                for(int j = 0; j < numberOfCities; j++)
                {
                    givenFlightCosts[i][j] = sc.nextInt();
                }
            }

            // debug print statements
            if(debug)
            {
                System.out.println("Number of Cities: " + numberOfCities);
                System.out.println();
                System.out.println("Given Flights Costs:");
                printArray(givenFlightCosts);
                System.out.println();
            }

            // will store the solutions to TSF(i, j)
            int[][] solutions = new int[numberOfCities][numberOfCities];

            // used to track the i index of the solutions, which is used when
            // retracing and printing out who visited which city
            int[][] trackingIndex1 = new int[numberOfCities][numberOfCities];

            // used to track the j index of the solutions, which is used when
            // retracing and printing out who visited which city
            int[][] trackingIndex2 = new int[numberOfCities][numberOfCities];

            // used in the algorithm of TSF(i, j) to optimize values
            // this is just a dummy value of sorts, which is why its value is MAX_VALUE
            int tempMin = Integer.MAX_VALUE;


            // base cases
            solutions[0][0] = 0;
            solutions[0][1] = 0;

            // first row of the solutions array
            // this is a special case described in the explanantion in the PDF
            for(int k = 2; k < numberOfCities; k++)
            {
                solutions[0][k] = solutions[0][k-1] + givenFlightCosts[k-1][k];
                trackingIndex1[0][k] = 0;
                trackingIndex2[0][k] = 0;
            }

            // first column of the solutions array
            // this is a special case described in the explanation in the PDF
            for(int k = 1; k < numberOfCities; k++)
            {
                solutions[k][0] = solutions[k-1][0] + givenFlightCosts[k-1][k];
                trackingIndex1[k][0] = 0;
                trackingIndex2[k][0] = 0;
            }


            // calculate TSF(i, j) used the algorithm and recursive relationship
            // described in the explanation in the PDF
            for(int i = 1; i < numberOfCities; i++)
            {
                for(int j = 1; j < numberOfCities; j++)
                {
                    tempMin = Integer.MAX_VALUE;

                    // special case described in the explanation in PDF
                    if(i == j)
                    {
                        solutions[i][j] = -1;
                    }

                    // if my friend reaches a city farther along than me
                    else if(i < j)
                    {
                        // handles error that would occur when I only reached city 1
                        if(i == 1)
                        {
                            solutions[i][j] = givenFlightCosts[0][1] + solutions[j][0] - solutions[2][0];
                            trackingIndex1[i][j] = 0;
                            trackingIndex2[i][j] = 2;
                        }


                        else
                        {

                            for(int k = 1; k < i; k++)
                            {
                                if(tempMin > solutions[i][k] + givenFlightCosts[k][i+1] + (solutions[0][j] - solutions[0][i+1]))
                                {
                                    tempMin = solutions[i][k] + givenFlightCosts[k][i+1] + (solutions[0][j] - solutions[0][i+1]);
                                    trackingIndex1[i][j] = i;
                                    trackingIndex2[i][j] = k;
                                }
                                solutions[i][j] = tempMin;
                            }

                            if((j == i + 1) && (tempMin > solutions[i][0]))
                            {
                                tempMin = solutions[i][0];
                                solutions[i][j] = tempMin;
                                trackingIndex1[i][j] = j - 1;
                                trackingIndex2[i][j] = 0;
                            }
                        }
                    }

                    // if I reach a city farther along than my friend
                    else if(i > j)
                    {
                        for(int k = 0; k < j; k++)
                        {
                            if(tempMin > solutions[k][j] + givenFlightCosts[k][j+1] + (solutions[i][0] - solutions[j+1][0]))
                            {
                                tempMin = solutions[k][j] + givenFlightCosts[k][j+1] + (solutions[i][0] - solutions[j+1][0]);
                                trackingIndex1[i][j] = k;
                                trackingIndex2[i][j] = j;
                            }
                            solutions[i][j] = tempMin;
                        }

                        if(tempMin > solutions[i][0] - givenFlightCosts[j-1][j] - givenFlightCosts[j][j+1] + givenFlightCosts[j-1][j+1])
                        {
                            tempMin = solutions[i][0] - givenFlightCosts[j-1][j] - givenFlightCosts[j][j+1] + givenFlightCosts[j-1][j+1];
                            solutions[i][j] = tempMin;
                            trackingIndex1[i][j] = i;
                            trackingIndex2[i][j] = j;
                        }
                    }

                }
            }





            int finalMinimumCost = findCostOfSolution(solutions);

            // print the final minimum cost to visit all cities, optimized
            System.out.println(finalMinimumCost);


            if(debug)
            {
                System.out.println();
                System.out.println("Solutions Array:");
                printArray(solutions);
                System.out.println();
                System.out.println("Tracking Index 1 Array:");
                printArray(trackingIndex1);
                System.out.println();
                System.out.println("Tracking Index 2 Array:");
                printArray(trackingIndex2);
                System.out.println();
            }


            ArrayList<Integer> myCitiesList = new ArrayList<Integer>();
            ArrayList<Integer> friendCitiesList = new ArrayList<Integer>();


            myCitiesList.add(index1);
            friendCitiesList.add(index2);

            if(index1 < index2)
            {

                for(int l = index1 + 1; l < index2; l++)
                {
                    friendCitiesList.add(l);
                }

            }


            else if(index1 > index2)
            {
                for(int l = index2 + 1; l < index1; l++)
                {
                    myCitiesList.add(l);
                }
            }



            while(index1 != 0)
            {
                int tempIndex1 = trackingIndex1[index1][index2];
                int tempIndex2 = trackingIndex2[index1][index2];


                if(tempIndex1 < tempIndex2)
                {
                    myCitiesList.add(tempIndex1);
                    for(int l = tempIndex1 + 1; l < tempIndex2; l++)
                    {
                        friendCitiesList.add(l);
                    }

                }


                else if(tempIndex1 > tempIndex2)
                {
                    friendCitiesList.add(tempIndex2);
                    for(int l = tempIndex2 + 1; l < tempIndex1; l++)
                    {
                        myCitiesList.add(l);
                    }
                }

                index1 = tempIndex1;
                index2 = tempIndex2;
            }


            Integer zero = new Integer(0);
            Integer one = new Integer(1);
            if(!myCitiesList.contains(zero))
            {
                myCitiesList.add(0);
            }
            if(friendCitiesList.contains(zero))
            {
                friendCitiesList.remove(zero);
            }

            Collections.sort(myCitiesList);
            Collections.sort(friendCitiesList);


            if(myCitiesList.size() > 1 && myCitiesList.get(1) == 1)
            {
                friendCitiesList.remove(one);
            }


            // print all the cities that I must visit personally
            for(int k = 0; k < myCitiesList.size(); k++)
            {
                System.out.print(myCitiesList.get(k) + " ");
            }
            System.out.println();

            // print all the cities that my friend must visit
            for(int k = 0; k < friendCitiesList.size(); k++)
            {
                System.out.print(friendCitiesList.get(k) + " ");
            }


        }
    }




    // accepts the number of cities as a parameter
    // handles output if there are a very low number of cities ( < 3 )
    public static void handleEdgeCases(int numberOfCities)
    {
        if(numberOfCities <= 0)
        {
            System.out.println(0);
            System.out.println();
            System.out.println();
        }
        else if(numberOfCities == 1)
        {
            System.out.println(0);
            System.out.println(0);
            System.out.println();
        }
        else if(numberOfCities == 2)
        {
            System.out.println(0);
            System.out.println(0);
            System.out.println(1);
        }
    }


    // accepts the solution array as a parameter
    // returns the value of the optimal cost to visit all cities
    // considers all TSF(i,j) where either i or j equals the last city
    public static int findCostOfSolution(int[][] array)
    {
        int minTotalCost = Integer.MAX_VALUE;
        for(int k = 0; k < array.length; k++)
        {
            // check all solutions where my friend reaches the last city
            if(minTotalCost > array[k][array.length-1] && array[k][array.length-1] != -1)
            {
                minTotalCost = array[k][array.length-1];
                index1 = k;
                index2 = array.length - 1;
            }

            // check all solutions where I reach the last city
            if(minTotalCost > array[array.length-1][k] && array[array.length-1][k] != -1)
            {
                minTotalCost = array[array.length-1][k];
                index1 = array.length - 1;
                index2 = k;
            }
        }
        return minTotalCost;
    }


    // method purely for debugging; will print out the contents of a 2-d array
    public static void printArray(int[][] array)
    {
        for(int i = 0; i < array.length; i++)
        {
            for(int j = 0; j < array[0].length; j++)
            {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

}
