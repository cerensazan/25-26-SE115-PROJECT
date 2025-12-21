// Main.java — Students version
import java.io.*;
import java.util.*;
//github:cerensazan
public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};

    static int[][][] profits = new int[MONTHS][DAYS][COMMS];
    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        Scanner sc = null;
        try {
            for (int m = 0; m < MONTHS; m++) {
                File file = new File("Data_Files/" + months[m] + ".txt");
                if (!file.exists()) continue;

                sc = new Scanner(file);

                if (sc.hasNextLine()) {
                    sc.nextLine();
                }

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split(",");
                    if (parts.length < 3) continue;

                    int day = Integer.parseInt(parts[0].trim()) - 1;
                    String comm = parts[1].trim();
                    int value = Integer.parseInt(parts[2].trim());

                    for (int c = 0; c < COMMS; c++) {
                        if (commodities[c].equals(comm)) {
                            profits[m][day][c] = value;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }finally {
            if (sc != null) {
                sc.close();
            }
            
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int maxProfit = Integer.MIN_VALUE;
        String best = "";

        for (int c = 0; c < COMMS; c++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profits[month][d][c];
            }
            if (sum > maxProfit) {
                maxProfit = sum;
                best = commodities[c];
            }
        }
        return best + " " + maxProfit;
    }


    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) return -99999;

        int sum = 0;
        for (int c = 0; c < COMMS; c++) {
            sum += profits[month][day - 1][c];
        }
        return sum;
    }



    public static int commodityProfitInRange(String commodity, int from, int to) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(commodity)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1 || from < 1 || to > DAYS || from > to) return -99999;

        int sum = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d <= to - 1; d++) {
                sum += profits[m][d][cIndex];
            }
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return -1;

        int bestDay = 1;
        int max = Integer.MIN_VALUE;

        for (int d = 0; d < DAYS; d++) {
            int sum = 0;
            for (int c = 0; c < COMMS; c++) {
                sum += profits[month][d][c];
            }
            if (sum > max) {
                max = sum;
                bestDay = d + 1;
            }
        }
        return bestDay;
    }

    public static String bestMonthForCommodity(String comm) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return "INVALID_COMMODITY";

        int bestMonth = 0;
        int max = Integer.MIN_VALUE;

        for (int m = 0; m < MONTHS; m++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profits[m][d][cIndex];
            }
            if (sum > max) {
                max = sum;
                bestMonth = m;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return -1;

        int maxStreak = 0;
        int current = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][cIndex] < 0) {
                    current++;
                    if (current > maxStreak) maxStreak = current;
                } else {
                    current = 0;
                }
            }
        }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int cIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                cIndex = c;
                break;
            }
        }
        if (cIndex == -1) return -1;

        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][cIndex] > threshold) count++;
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;

        int maxSwing = 0;

        for (int d = 0; d < DAYS - 1; d++) {
            int today = 0;
            int next = 0;
            for (int c = 0; c < COMMS; c++) {
                today += profits[month][d][c];
                next += profits[month][d + 1][c];
            }
            int diff = Math.abs(today - next);
            if (diff > maxSwing) maxSwing = diff;
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int i1 = -1, i2 = -1;

        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(c1)) i1 = c;
            if (commodities[c].equals(c2)) i2 = c;
        }
        if (i1 == -1 || i2 == -1) return "INVALID_COMMODITY";

        int sum1 = 0, sum2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                sum1 += profits[m][d][i1];
                sum2 += profits[m][d][i2];
            }
        }
        if (sum1 == sum2) return "Equal";
        if (sum1 > sum2) return c1 + " is better by " + (sum1 - sum2);
        return c2 + " is better by " + (sum2 - sum1);
    }

    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int bestWeek = 1;
        int max = Integer.MIN_VALUE;

        for (int w = 0; w < 4; w++) {
            int sum = 0;
            for (int d = w * 7; d < w * 7 + 7; d++) {
                for (int c = 0; c < COMMS; c++) {
                    sum += profits[month][d][c];
                }
            }
            if (sum > max) {
                max = sum;
                bestWeek = w + 1;
            }
        }
        return "Week " + bestWeek;

    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
