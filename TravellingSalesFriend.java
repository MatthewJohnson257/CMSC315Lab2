import java.util.*;

public class TravellingSalesFriend
{
    static int index1 = 999;
    static int index2 = 999;
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
            int[][] trackingIndex1 = new int[numberOfCities][numberOfCities];
            int[][] trackingIndex2 = new int[numberOfCities][numberOfCities];

            // // first column
            // for(int k = 0; k < numberOfCities; k++)
            // {
            //     solutions[k][0] = -1;
            // }
            //
            // // first row
            // solutions[0][1] = 0;
            // for(int k = 2; k < numberOfCities; k++)
            // {
            //     solutions[0][k] = solutions[0][k-1] + givenFlightCosts[k-1][k];
            // }
            //
            //
            //
            // for(int k = 2; k < numberOfCities - 1; k++)
            // {
            //     solutions[k][k+1] = solutions[0][1] + solutions[0][k] - givenFlightCosts[k-1][k] + givenFlightCosts[0][1];
            // }
            //
            // // second column
            // solutions[1][1] = -1;
            // solutions[2][1] = givenFlightCosts[0][2];
            // solutions[1][2] = givenFlightCosts[0][1];
            // for(int k = 3; k < numberOfCities; k++)
            // {
            //     solutions[k][1] = solutions[k-1][1] + givenFlightCosts[k-1][k];
            // }
            //
            // // for(int k = 2; k < numberOfCities; k++)
            // // {
            // //     solutions[k-1][k] = givenFlightCosts[0][1] + solutions[0][k] - givenFlightCosts[k-1][k];
            // // }
            //
            // // for(int k = 1; k < numberOfCities; k++)
            // // {
            // //     solutions[k-1][k] =
            // // }
            // int tempMin = maximumCostFromOneCityToAnother * maximumCostFromOneCityToAnother;
            //
            // for(int i = 1; i < numberOfCities; i++)
            // {
            //     for(int j = 1; j < numberOfCities; j++)
            //     {
            //         tempMin = maximumCostFromOneCityToAnother * maximumCostFromOneCityToAnother;
            //         if(i == j)
            //         {
            //             solutions[i][j] = -1;
            //         }
            //
            //         else if (i < j && solutions[i][j] < 1)
            //         {
            //             if(i == 1)
            //             {
            //                 for(int k = 0; k < i; k++)
            //                 {
            //                     if((tempMin > solutions[1][k] + givenFlightCosts[i+1][k]) && i != k  && !(i == numberOfCities))
            //                     {
            //                         tempMin = solutions[i][k] + givenFlightCosts[k][i+1];
            //                         trackingIndex1[i][j] = i;
            //                         trackingIndex2[i][j] = k;
            //                     }
            //                     solutions[i][j] = tempMin + solutions[0][j] - solutions[0][i+1];
            //
            //
            //                 }
            //             }
            //
            //             else
            //             {
            //                 for(int k = 1; k < i; k++)
            //                 {
            //                     if(tempMin > solutions[i][k] + givenFlightCosts[k][i+1])
            //                     {
            //                         tempMin = solutions[i][k] + givenFlightCosts[k][i+1];
            //                         trackingIndex1[i][j] = i;
            //                         trackingIndex2[i][j] = k;
            //                     }
            //                     solutions[i][j] = tempMin + solutions[0][j] - solutions[0][i+1];
            //                 }
            //             }
            //         }
            //
            //         else if(i > j && solutions[i][j] < 1)
            //         {
            //             if(j == 1)
            //             {
            //                 for(int k = 0; k < j; k++)
            //                 {
            //                     if((tempMin > solutions[k][j] + givenFlightCosts[k][j+1]))
            //                     {
            //                         tempMin = solutions[k][j] + givenFlightCosts[k][j+1];
            //                         trackingIndex1[i][j] = k;
            //                         trackingIndex2[i][j] = j;
            //                     }
            //                     solutions[i][j] = tempMin + solutions[i][1] - solutions[j+1][1];
            //
            //
            //                 }
            //             }
            //
            //             else
            //             {
            //
            //             for(int k = 0; k < j; k++)
            //             {
            //                 if(tempMin > solutions[k][j] + givenFlightCosts[k][j+1])
            //                 {
            //                     tempMin = solutions[k][j] + givenFlightCosts[k][j+1];
            //                     trackingIndex1[i][j] = k;
            //                     trackingIndex2[i][j] = j;
            //                 }
            //                 solutions[i][j] = tempMin + solutions[i][1] - solutions[j+1][1];
            //             }
            //             }
            //         }
            //         //System.out.println("tempMin: " + tempMin);
            //     }
            // }

            int tempMin = Integer.MAX_VALUE;

            solutions[0][0] = 0;
            solutions[0][1] = 0;

            // first row
            for(int k = 2; k < numberOfCities; k++)
            {
                solutions[0][k] = solutions[0][k-1] + givenFlightCosts[k-1][k];
                trackingIndex1[0][k] = 0;
                trackingIndex2[0][k] = 0;
            }

            // first column
            for(int k = 1; k < numberOfCities; k++)
            {
                solutions[k][0] = solutions[k-1][0] + givenFlightCosts[k-1][k];
                trackingIndex1[k][0] = 0;
                trackingIndex2[k][0] = 0;
            }

            for(int i = 1; i < numberOfCities; i++)
            {
                for(int j = 1; j < numberOfCities; j++)
                {
                    tempMin = Integer.MAX_VALUE;

                    if(i == j)
                    {
                        solutions[i][j] = -1;
                    }

                    else if(i < j)
                    {
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






            printArray(solutions);




            int finalMinimumCost = findCostOfSolution(solutions);
            System.out.println("finalMinimumCost: " + finalMinimumCost);

            System.out.println();
            //printArray(solutionTracking);
            printArray(trackingIndex1);
            System.out.println();
            printArray(trackingIndex2);



            ArrayList<Integer> myCitiesList = new ArrayList<Integer>();
            ArrayList<Integer> friendCitiesList = new ArrayList<Integer>();


            //myCitiesList.add(0);
            myCitiesList.add(index1);
            friendCitiesList.add(index2);

            if(index1 < index2)
            {

                for(int l = index1 + 1; l < index2; l++)
                {
                    System.out.println("First: " + l);
                    friendCitiesList.add(l);
                }

            }


            else if(index1 > index2)
            {
                for(int l = index2 + 1; l < index1; l++)
                {
                    System.out.println("Second: " + l);
                    myCitiesList.add(l);
                }
            }



            while(index1 != 0)
            {
                int tempIndex1 = trackingIndex1[index1][index2];
                int tempIndex2 = trackingIndex2[index1][index2];
                // myCitiesList.add(index1);
                // friendCitiesList.add(index2);

                if(tempIndex1 < tempIndex2)
                {
                    myCitiesList.add(tempIndex1);
                    for(int l = tempIndex1 + 1; l < tempIndex2; l++)
                    {
                        //System.out.println("First: " + l);
                        friendCitiesList.add(l);
                    }

                }


                else if(tempIndex1 > tempIndex2)
                {
                    friendCitiesList.add(tempIndex2);
                    for(int l = tempIndex2 + 1; l < tempIndex1; l++)
                    {
                        //System.out.println("Second: " + l);
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


            if(myCitiesList.size() > 2 && myCitiesList.get(1) == 1)
            {
                friendCitiesList.remove(one);
            }


            System.out.println("MyCities: " + myCitiesList.toString());
            System.out.println("FriendsCities: " + friendCitiesList.toString());




        }
    }





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


    public static int findCostOfSolution(int[][] array)
    {
        int minTotalCost = 9999;
        for(int k = 0; k < array.length; k++)
        {
            if(minTotalCost > array[k][array.length-1] && array[k][array.length-1] != -1)
            {
                minTotalCost = array[k][array.length-1];
                index1 = k;
                index2 = array.length - 1;
            }
            if(minTotalCost > array[array.length-1][k] && array[array.length-1][k] != -1)
            {
                minTotalCost = array[array.length-1][k];
                index1 = array.length - 1;
                index2 = k;
            }
        }
        System.out.println("index1: " + index1);
        System.out.println("index2: " + index2);
        return minTotalCost;
    }


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
