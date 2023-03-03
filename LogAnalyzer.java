/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer {
    // Where to calculate the access counts.
    private int[] hourCounts;
    private int[] dailyCounts;
    private int[] monthlyCounts;
    private int[] yearlyCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses (default name).
     */
    public LogAnalyzer() { 
        // Create array objects to hold the access counts.
        hourCounts = new int[24];
        dailyCounts = new int[28];
        monthlyCounts = new int[12];
        yearlyCounts = new int[5];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
    }
    
    /**
    * Create an analyze hourly web accesses from a particular log file.
    * @param filename The file of log data.
    */
    public LogAnalyzer(String filename) { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dailyCounts = new int[28];
        monthlyCounts = new int[12];
        yearlyCounts = new int[5];
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the access data from the log file.
     */
    public void analyzeData() {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
            int day = entry.getDay();
            dailyCounts[(day - 1)]++;
            int month = entry.getMonth();
            monthlyCounts[(month - 1)]++;
            int year = entry.getYear();
            yearlyCounts[(year - 2015)]++;
        }
    }
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts() {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the monthly counts.
     */
    public void printMonthlyCounts() {
        System.out.println("Month: Count");
        for(int month = 0; month < monthlyCounts.length; month++) {
            System.out.println((month + 1) + ": " + monthlyCounts[month]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
    * Returns the number of accesses recorded in the log file.
    * @return Total accesses
    */
    public int numberOfAccesses() {
        int total = 0;
        // Add the value in each element of hourCounts to total.
        for (int index = 0; index < hourCounts.length; index++) {
            total += hourCounts[index];
        }
        
        return total;
    }
    
    /**
     * Returns the hour with the most accesses.
     * @return Hour with the most accesses
     */
    public int busiestHour() {
        int busiest = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > hourCounts[busiest]) {
                busiest = hour;
            }
        }
        
        return busiest;
    }
    
    /**
     * Returns the hour with the fewest accesses.
     * @return Hour with the fewest accesses.
     */
    public int quietestHour() {
        int quietest = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < hourCounts[quietest]) {
                quietest = hour;
            }
        }
        
        return quietest;
    }
    
    /**
     * Finds which two hour period has the most accesses, and
     * returns the first of those two hours.
     * @return The first hour of the two-hour period with the most accesses
     */
    public int busiestTwoHour() {
        int busiest = 0;
        for (int hour = 0; hour < hourCounts.length; hour++) {
            if (hour != 23) {
                if ((hourCounts[hour] + hourCounts[hour + 1]) >
                    (hourCounts[busiest] + hourCounts[busiest + 1])) {
                    busiest = hour;
                }
            }
            else if (hour == 23) {
                if ((hourCounts[23] + hourCounts[0]) >
                    (hourCounts[busiest] + hourCounts[busiest + 1])) {
                    busiest = 23;
                }
            }
        }
        
        return busiest;
    }
    
    /**
     * Returns the day (of the month) with the most accesses.
     * @return Day with the most accesses
     */
    public int busiestDay() {
        int busiest = 0;
        for (int day = 0; day < dailyCounts.length; day++) {
            if (dailyCounts[day] > dailyCounts[busiest]) {
                busiest = day;
            }
        }
        
        return (busiest + 1);
    }
    
    /**
     * Returns the day (of the month) with the fewest accesses.
     * @return Day with the most accesses
     */
    public int quietestDay() {
        int quietest = 0;
        for (int day = 0; day < dailyCounts.length; day++) {
            if (dailyCounts[day] < dailyCounts[quietest]) {
                quietest = day;
            }
        }
        
        return (quietest + 1);
    }
    
    /**
     * Returns the month with the most accesses.
     * @return Month with the most accesses
     */
    public int busiestMonth() {
        int busiest = 0;
        for (int month = 0; month < monthlyCounts.length; month++) {
            if (monthlyCounts[month] > monthlyCounts[busiest]) {
                busiest = month;
            }
        }
        
        return (busiest + 1);
    }
    
    /**
     * Returns the month with the fewest accesses.
     * @return Month with the fewest accesses
     */
    public int quietestMonth() {
        int quietest = 0;
        for (int month = 0; month < monthlyCounts.length; month++) {
            if (monthlyCounts[month] < monthlyCounts[quietest]) {
                quietest = month;
            }
        }
        
        return (quietest + 1);
    }
    
    /**
     * Returns the array of accesses per month.
     * @return Int array of accesses per month
     */
    public int[] totalAccessesPerMonth() {
        return monthlyCounts;
    }
    
    /**
     * Returns the average monthly accesses.
     * @return Mean of accesses per month
     */
    public double averageAccessesPerMonth() {
        double total = numberOfAccesses();
        double average = total / 12;
        return average;
    }
}
