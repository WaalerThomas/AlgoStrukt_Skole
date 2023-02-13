package AlgoStrukt_Skole.thomaswaaler.oblig1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

// Maybe not the best naming for the main class, but i guess it will do. Thankfully I don't have to type it alot.
class Flyplassen
{
    int maxQueueSize = 10;    // Max amount of planes in each queue allowed
    int maxTimesteps;         // Max timesteps the simulations will run
    int loggingDepth;         // Used for spacing correctly while logging
    float P_planeArrivals;    // Probability that a plane will arrive
    float P_planeDepartures;  // Probability that a plane will arrive

    public Flyplassen()
    {
        // Read in data from the user for simulation parameters
        Scanner s = new Scanner(System.in);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("   Welcome to Airport Simulation!   ");
        System.out.println("           By Thomas Waaler          ");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        System.out.print("Amount of timesteps to perform? ");
        maxTimesteps = s.nextInt();

        // In case of maxTimesteps=100, we want to get the digit count of 99 since we will only show 0-99 during logging
        loggingDepth = String.valueOf(maxTimesteps - 1).length();

        System.out.print("Expected arrivals per timestep (Between 0.0 and 1.0)? ");
        P_planeArrivals = s.nextFloat();

        System.out.print("Expected departures per timestep (Between 0.0 and 1.0)? ");
        P_planeDepartures = s.nextFloat();

        // Add an empty line before logging
        System.out.println("");

        // Check that the probability isn't greater than 1
        float collectedP = P_planeArrivals + P_planeDepartures;
        if (collectedP > 1.0) {
            System.out.println("ERROR: The total probability is larger than 1, " + collectedP);
            System.exit(1);
        }

        // Cleanup
        s.close();
    }

    // Class to keep track of how long the plane waited in a queue
    private class Plane
    {
        public Plane(int name, int arrival) {
            this.name = name;       // The plane number to know which one it is during logging
            this.arrival = arrival;
        }
        public int getWaitTime(int time) { return time - arrival; }

        private int arrival;
        private int name;
    }

    public void simulate()
    {
        // Variables used for tracking what happened during the simulation
        int planeNumber        = 0; // Keep track of what the next plane number will be
        int processedCount     = 0; // How many planes we have processed, including those declined
        int planeLandCount     = 0;
        int planeTakeoffCount  = 0;
        int planeDeclinedCount = 0;
        int landingWaitTime    = 0; // The total time planes have had to wait for landing
        int runwayWaitTime     = 0; // The total time planes have had to wait for taking off
        int emptyAirportTime   = 0; // How many timesteps the airport have been empty

        // Creating the two plane queues
        Queue<Plane> runwayQ = new LinkedList<Plane>();
        Queue<Plane> landingQ = new LinkedList<Plane>();

        // Used for making a list of lines to print at the end of each timestep
        Queue<String> loggingQueue = new LinkedList<String>();

        // Simulate each timestep
        int newPlanesCount;
        for (int time = 0; time < maxTimesteps; time++)
        {
            // How many planes will want to land during this timestep?
            newPlanesCount = getPoissonRandom(P_planeArrivals);
            processedCount += newPlanesCount;
            for (int i = 0; i < newPlanesCount; i++) {
                if (landingQ.size() == maxQueueSize)
                    planeDeclinedCount++;
                else
                {
                    loggingQueue.add("Plane " + planeNumber + " ready for landing.");
                    landingQ.add(new Plane(planeNumber, time));
                    planeNumber++;
                }
            }

            // How many planes will want to take off during this timestep?
            newPlanesCount = getPoissonRandom(P_planeDepartures);
            processedCount += newPlanesCount;
            for (int i = 0; i < newPlanesCount; i++) {
                if (runwayQ.size() == maxQueueSize)
                    planeDeclinedCount++;
                else
                {
                    loggingQueue.add("Plane " + planeNumber + " ready for departure.");
                    runwayQ.add(new Plane(planeNumber, time));
                    planeNumber++;
                }
            }

            // Check if there are any planes in the queue, and prioritize planes in the landing queue.
            if (!landingQ.isEmpty())
            {
                Plane p = landingQ.remove();
                loggingQueue.add("Plane " + p.name + " landed, wait time " + p.getWaitTime(time) + " units.");

                // Increment data for this timestep
                planeLandCount++;
                landingWaitTime += p.getWaitTime(time);
            }
            else if (!runwayQ.isEmpty())
            {
                Plane p = runwayQ.remove();
                loggingQueue.add("Plane " + p.name + " departed, wait time " + p.getWaitTime(time) + " units.");

                // Increment data for this timestep
                planeTakeoffCount++;
                runwayWaitTime += p.getWaitTime(time);
            }
            else {
                // Both queues are emtpy
                emptyAirportTime++;
                loggingQueue.add("Airport is empty.");
            }

            // Print some lovely informasjon about this timestep to the console
            boolean isFirstLine = true;
            while (!loggingQueue.isEmpty())
            {
                String line = loggingQueue.remove();

                if (isFirstLine) {
                    System.out.format("%" + loggingDepth + "d: ", time);
                    isFirstLine = false;
                }
                else
                    System.out.format("%" + (loggingDepth + 2) + "s", ""); // Writes x spaces to line up with the first line

                System.out.println(line);
            }
        }

        // Print out the finishing data
        System.out.println("");
        System.out.println("Simulation finished after   : " + maxTimesteps + " timesteps");
        System.out.println("Total planes processed      : " + processedCount);
        System.out.println("Planes landed               : " + planeLandCount);
        System.out.println("Planes taken off            : " + planeTakeoffCount);
        System.out.println("Planes declined             : " + planeDeclinedCount);
        System.out.println("Planes ready for landing    : " + landingQ.size());
        System.out.println("Planes ready to take off    : " + runwayQ.size());
        System.out.println("Percent available time      : " + (int)((float)emptyAirportTime / (float)maxTimesteps * 100));
        // Protect from division by 0
        if (planeLandCount > 0) {
            System.out.println(
                    "Average wait time landing   : " + (float) landingWaitTime / (float) planeLandCount + " timesteps");
        }
        // Protect from division by 0
        if (planeTakeoffCount > 0) {
            System.out.println("Average wait time on runway : " + (float) runwayWaitTime / (float) planeTakeoffCount
                    + " timesteps");
        }
    }

    // Random drawing with poisson distribution, implementation from task text
    private int getPoissonRandom(double mean)
    {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }

    public static void main(String[] args) {
        Flyplassen airport = new Flyplassen();
        airport.simulate();
    }
}
