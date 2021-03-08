package Main;

import Classes.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainAplikasiKasir {

    public DaftarMenu daftarMenu;
    //Tambahkan
    public static double PAJAK_PPN = 0.10;
    public static double BIAYA_SERVICE = 0.05;
    //End of Tambahkan

    public static void main(String[] args) {
        //inisialisasi kelas Scanner untuk mengambil
        //input dari keyboard
        Scanner input = new Scanner(System.in);
        //Tambahkan
        String no_transaksi, nama_pemesanan, tanggal, no_meja = "";
        String transaksi_lagi = "", pesan_lagi = "", keterangan = "", makan_ditempat;
        int jumlah_pesanan, no_menu;
        //End of Tambahkan

        MainAplikasiKasir app = new MainAplikasiKasir();
        //tampilan daftar menu
        app.generateDaftarMenu();
        // mulai transaksi
        System.out.println("========== TRANSAKSI ==========");

        //ambil data transaksi
        System.out.print("No Transaksi : ");
        no_transaksi = input.next();
        System.out.print("Pemesan : ");
        nama_pemesanan = input.next();
        System.out.print("Tanggal : [dd-mm-yyyy] ");
        tanggal = input.next();
        System.out.print("Makan ditempat? [Y?N] ");
        makan_ditempat = input.next();

        if (makan_ditempat.equalsIgnoreCase("Y")) {
            System.out.print("Nomor Meja : ");
            no_meja = input.next();
        }
        //buat transaksi baru
        Transaksi trans = new Transaksi(no_transaksi, nama_pemesanan, tanggal, no_meja);
        System.out.println("======== PESANAN ========");
        int no_kuah;
        do {
            //ambil menu berdasarkan nomor urut yang dipilih
            Menu menu_yang_dipilih = app.daftarMenu.pilihMenu();

            jumlah_pesanan = (int) app.cekInputNumber("Jumlah : ");

            //buat pesanan
            Pesanan pesanan = new Pesanan(menu_yang_dipilih, jumlah_pesanan);
            trans.tambahPesanan(pesanan);
            //khusus untuk pesanan ramen, kuah nya langsung diinput juga
            if (menu_yang_dipilih.getKategori().equals("Ramen")){
                //looping sesuai jumlah pesanan ramen
                int jumlah_ramen = jumlah_pesanan;
                do {
                    //ambil objek menu berdasarkan nomer yang dipilih
                    Menu kuah_yang_dipilih = app.daftarMenu.pilihKuah();

                    System.out.print("Level [0-5] : ");
                    String level = input.next();

                    //validasi jumlah kuah tidak boleh melebihi dari jumlah ramen
                    int jumlah_kuah = 0;
                    do {
                        jumlah_kuah =  (int) app.cekInputNumber("Jumlah : ");

                        if (jumlah_kuah > jumlah_ramen) {
                            System.out.println("[Err] Jumlah kuah melibihi jumlah ramen yang sudah dipesan");
                        } else {
                            break;
                        }
                    } while (jumlah_kuah > jumlah_ramen);

                    //set pesanan kuah
                    Pesanan pesanan_kuah = new Pesanan(kuah_yang_dipilih, jumlah_kuah);
                    pesanan_kuah.setKeterangan("Level" +level);

                    //tambahkan pesanan kuah ke transaksi
                    trans.tambahPesanan(pesanan_kuah);

                    //hitung jumlah ramen yang belum dipesan kuah nya
                    jumlah_ramen -= jumlah_kuah;
                } while (jumlah_ramen > 0);

            } else {
                //jika keterangan tidak diisi tulis =
                System.out.print("Keterangan [- jika kosong]: ");
                keterangan = input.next();
            }
            //cek jika keterangan diisi selain "-" set ke pesanan
            if (!keterangan.equals("-")){
                pesanan.setKeterangan(keterangan);
            }

            //konfirmasi, mau tambah pesanan atau tidak
            System.out.print("Tambah pesanan lagi? [Y/N] : ");
            pesan_lagi = input.next();
        } while (pesan_lagi.equalsIgnoreCase("Y"));
    }

    public void generateDaftarMenu() {
        daftarMenu = new DaftarMenu();
        daftarMenu.tambahMenu(new Ramen("Ramen Seafood", 25000));
        daftarMenu.tambahMenu(new Ramen("Ramen Original", 18000));
        daftarMenu.tambahMenu(new Ramen("Ramen Vegetarian", 22000));
        daftarMenu.tambahMenu(new Ramen("Ramen Karnivor", 28000));
        daftarMenu.tambahMenu(new Kuah("Kuah Original "));
        daftarMenu.tambahMenu(new Kuah("Kuah Internasional"));
        daftarMenu.tambahMenu(new Kuah("Kuah Spicy Lada"));
        daftarMenu.tambahMenu(new Kuah("Kuah Soto Padang"));
        daftarMenu.tambahMenu(new Toping("Crab Stick Bakar", 6000));
        daftarMenu.tambahMenu(new Toping("Chicken Katsu", 8000));
        daftarMenu.tambahMenu(new Toping("Gyoza Goreng", 4000));
        daftarMenu.tambahMenu(new Toping("Bakso Goreng", 7000));
        daftarMenu.tambahMenu(new Toping("Enoki Goreng", 5000));
        daftarMenu.tambahMenu(new Minuman("Jus Alpukat SPC", 10000));
        daftarMenu.tambahMenu(new Minuman("Jus Stroberi", 11000));
        daftarMenu.tambahMenu(new Minuman("Capucino Coffee", 15000));
        daftarMenu.tambahMenu(new Minuman("Vietnam Dripp", 14000));

        daftarMenu.tampilDaftarMenu();
    }

    public double cekInputNumber(String label) {
        try {
            Scanner get_input = new Scanner(System.in);
            System.out.print(label);
            double nilai = get_input.nextDouble();

            return nilai;
        } catch (InputMismatchException ex) {
            System.out.println("[Err] Harap masukan angka");
            return cekInputNumber(label);
        }
    }
}
