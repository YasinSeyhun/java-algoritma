import java.util.ArrayList;
import java.util.List;

class Job implements Comparable<Job> {
    String ad;
    int gelisZamani;
    int sonTeslimZamani;
    int calismaZamani;

    public Job(String ad, int gelisZamani, int sonTeslimZamani, int calismaZamani) {
        this.ad = ad;
        this.gelisZamani = gelisZamani;
        this.sonTeslimZamani = sonTeslimZamani;
        this.calismaZamani = calismaZamani;
    }

    @Override
    public int compareTo(Job diger) {
        return Integer.compare(this.sonTeslimZamani, diger.sonTeslimZamani);
    }
}

public class EDFScheduler {
    public static List<String> edfZamaniHesapla(List<Job> isler) {
        List<String> zamanPlanı = new ArrayList<>();
        int simdikiZaman = 0;

        while (!isler.isEmpty()) {
            List<Job> hazirIsler = new ArrayList<>();

            for (Job is : isler) {
                if (is.gelisZamani <= simdikiZaman) {
                    hazirIsler.add(is);
                }
            }

            if (hazirIsler.isEmpty()) {
                simdikiZaman++;
                continue;
            }

            hazirIsler.sort(Job::compareTo);
            Job secilenIs = hazirIsler.get(0);

            zamanPlanı.add("Zaman " + simdikiZaman + ": " + secilenIs.ad);

            simdikiZaman += secilenIs.calismaZamani;
            isler.remove(secilenIs);
        }

        return zamanPlanı;
    }

    public static void main(String[] args) {
        Job is1 = new Job("İş1", 0, 5, 3);
        Job is2 = new Job("İş2", 1, 4, 2);
        Job is3 = new Job("İş3", 2, 8, 4);

        List<Job> isler = new ArrayList<>();
        isler.add(is1);
        isler.add(is2);
        isler.add(is3);

        List<String> zamanPlanı = edfZamaniHesapla(isler);

        System.out.println("EDF Zaman Planı:");
        for (String kayit : zamanPlanı) {
            System.out.println(kayit);
        }
    }
}
