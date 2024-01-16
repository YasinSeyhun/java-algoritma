import java.util.Arrays;
import java.util.Scanner;

class Islem implements Comparable<Islem> {
    char ad;
    int varisZamani;
    int patlamaZamani;
    int beklemeSuresi;
    float yanitOrani;

    public Islem(char ad, int varisZamani, int patlamaZamani) {
        this.ad = ad;
        this.varisZamani = varisZamani;
        this.patlamaZamani = patlamaZamani;
        this.beklemeSuresi = 0;
        this.yanitOrani = 0;
    }

    @Override
    public int compareTo(Islem digerIslem) {
        return Float.compare(digerIslem.yanitOrani, this.yanitOrani);
    }
}

public class HRRFZamanlama {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Islem sayisini giriniz: ");
        int islemSayisi = scanner.nextInt();

        Islem[] islemler = new Islem[islemSayisi];

        for (char ad = 'A'; ad < 'A' + islemSayisi; ad++) {
            System.out.print("Varis zamani ve patlama zamani giriniz for Islem " + ad + " (ornek: 0 5): ");
            int varisZamani = scanner.nextInt();
            int patlamaZamani = scanner.nextInt();
            islemler[ad - 'A'] = new Islem(ad, varisZamani, patlamaZamani);
        }

        int simdikiZaman = 0;
        float toplamBeklemeSuresi = 0;

        System.out.println("\nHRRF Algoritmasi Cikti:");
        System.out.println("------------------------------");
        System.out.println("Islem\tVaris Zamani\tPatlama Zamani\tBekleme Suresi");
        System.out.println("------------------------------");

        while (true) {
            for (Islem islem : islemler) {
                if (islem.varisZamani <= simdikiZaman && islem.patlamaZamani > 0) {
                    islem.yanitOrani = (float) (simdikiZaman - islem.varisZamani + islem.patlamaZamani) / islem.patlamaZamani;
                } else {
                    islem.yanitOrani = 0;
                }
            }

            Arrays.sort(islemler);

            boolean tumIslemlerTamamlandi = true;

            for (Islem islem : islemler) {
                if (islem.patlamaZamani > 0) {
                    tumIslemlerTamamlandi = false;

                    islem.beklemeSuresi = simdikiZaman - islem.varisZamani;
                    toplamBeklemeSuresi += islem.beklemeSuresi;

                    System.out.println(islem.ad + "\t\t" + islem.varisZamani + "\t\t\t" + islem.patlamaZamani + "\t\t\t" + islem.beklemeSuresi);

                    simdikiZaman += islem.patlamaZamani;
                    islem.patlamaZamani = 0;
                    break;
                }
            }

            if (tumIslemlerTamamlandi) {
                break;
            }
        }

        float ortalamaBeklemeSuresi = toplamBeklemeSuresi / islemSayisi;
        System.out.println("\nOrtalama Bekleme Suresi: " + ortalamaBeklemeSuresi);

        scanner.close();
    }
}
