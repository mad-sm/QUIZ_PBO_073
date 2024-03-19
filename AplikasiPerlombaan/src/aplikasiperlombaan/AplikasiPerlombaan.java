/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasiperlombaan;

/**
 *
 * @author Lab Informatika
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AplikasiPerlombaan extends JFrame{

    /**
     * @param args the command line arguments
     */
    private JComboBox<String> comboBoxKategori;
    private JTextField textFieldNama, textFieldSekolah;
    private JSpinner[] spinnerNilai;
    private JButton tombolHitung, tombolUbah, tombolKeluar;
    private JFrame frameHasil;

    private static final double NILAI_MINIMAL = 85.0;
    
    
    public AplikasiPerlombaan() {
        setTitle("Program Perlombaan UPN V Yogyakarta");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        String[] kategori = {"Animasi", "Menulis Surat"};
        comboBoxKategori = new JComboBox<>(kategori);
        
        JLabel labelNama = new JLabel("Nama:");
        JLabel labelSekolah = new JLabel("Asal Sekolah:");
        JLabel[] labelNilai = {
            new JLabel("Alur Cerita:"),
            new JLabel("Konten:"),
            new JLabel("Kreativitas:"),
            new JLabel("Sinematografi:")
        };
        textFieldNama = new JTextField(15);
        textFieldSekolah = new JTextField(15);
        spinnerNilai = new JSpinner[4];
        for (int i = 0; i < spinnerNilai.length; i++) {
            spinnerNilai[i] = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        }
        
    tombolHitung = new JButton("Hitung Nilai");
        tombolHitung.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitungNilai();
            }
        });

        tombolUbah = new JButton("Ubah Nilai");
        tombolUbah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ubahNilai();
            }
        });

        tombolKeluar = new JButton("Keluar");
        tombolKeluar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panelUtama = new JPanel(new GridLayout(0, 2, 10, 10));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelUtama.add(new JLabel("Kategori:"));
        panelUtama.add(comboBoxKategori);
        panelUtama.add(labelNama);
        panelUtama.add(textFieldNama);
        panelUtama.add(labelSekolah);
        panelUtama.add(textFieldSekolah);
        for (int i = 0; i < labelNilai.length; i++) {
            panelUtama.add(labelNilai[i]);
            panelUtama.add(spinnerNilai[i]);
        }
        panelUtama.add(tombolHitung);
        panelUtama.add(tombolUbah);
        panelUtama.add(tombolKeluar);

        add(panelUtama);
    }

    private void tampilkanHasil(String nama, String sekolah, int[] nilai) {
    frameHasil = new JFrame("Hasil Perlombaan");
    frameHasil.setSize(400, 200);
    frameHasil.setLocationRelativeTo(null);
    frameHasil.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JPanel panelHasil = new JPanel(new GridLayout(0, 2, 10, 10));
    panelHasil.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    panelHasil.add(new JLabel("Nama:"));
    panelHasil.add(new JLabel(nama));
    panelHasil.add(new JLabel("Asal Sekolah:"));
    panelHasil.add(new JLabel(sekolah));
    for (int i = 0; i < nilai.length; i++) {
        panelHasil.add(new JLabel("Nilai " + (i + 1) + ":"));
        panelHasil.add(new JLabel(String.valueOf(nilai[i])));
    }

    frameHasil.add(panelHasil);
    frameHasil.setVisible(true);
}
    
    private void hitungNilai() {
    String nama = textFieldNama.getText();
    String sekolah = textFieldSekolah.getText();
    int[] nilai = new int[spinnerNilai.length];
    for (int i = 0; i < spinnerNilai.length; i++) {
        nilai[i] = (int) spinnerNilai[i].getValue();
    }

    String kategori = (String) comboBoxKategori.getSelectedItem();
    double[] bobot;
    if (kategori.equals("Animasi")) {
        bobot = new double[]{0.15, 0.35, 0.35, 0.15};
    } else {
        bobot = new double[]{0.10, 0.40, 0.30, 0.20};
    }

    double nilaiTotal = 0;
    for (int i = 0; i < spinnerNilai.length; i++) {
        nilaiTotal += (int) spinnerNilai[i].getValue() * bobot[i];
    }

    if (nilaiTotal >= NILAI_MINIMAL) {
        JOptionPane.showMessageDialog(this, "Peserta lolos seleksi dengan nilai akhir: " + nilaiTotal, "Hasil Seleksi", JOptionPane.INFORMATION_MESSAGE);
        tampilkanHasil(nama, sekolah, nilai);
    } else {
        JOptionPane.showMessageDialog(this, "Maaf, peserta tidak lolos seleksi dengan nilai akhir: " + nilaiTotal, "Hasil Seleksi", JOptionPane.ERROR_MESSAGE);
    }
    }

    
    private void ubahNilai() {
       String nilaiBaruStr = JOptionPane.showInputDialog( "Masukkan nilai baru:");
        try {
            int nilaiBaru = Integer.parseInt(nilaiBaruStr);
            if (nilaiBaru >= 0 && nilaiBaru <= 100) {
                JSpinner spinner = (JSpinner) 
                        JOptionPane.getFrameForComponent(this).getFocusOwner();
                spinner.setValue(nilaiBaru);
            } else {
                JOptionPane.showMessageDialog(this, "Masukkan nilai antara 0 sampai 100", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukan harus berupa angka", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AplikasiPerlombaan().setVisible(true);
            }
        });
    }



