package com.ryohandoko.restaurantuas.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ryohandoko.restaurantuas.Adapter.PesananAdapter;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentPesananBinding;
import com.ryohandoko.restaurantuas.model.Pesanan;
import com.ryohandoko.restaurantuas.viewmodel.PesananFragmentViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PesananFragment extends Fragment {

    private FragmentPesananBinding binding;
    private PesananFragmentViewModel viewModel;

    private SwipeRefreshLayout swipeRefreshLayout;
    private PesananAdapter adapter;
    private RecyclerView recyclerView;

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private File pdfFile;
    private PdfWriter writer;
    private AlertDialog.Builder builder;
    private List<Pesanan> listPesanan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(PesananFragmentViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pesanan, container, false);

        binding.setView(this);
        binding.setVM(viewModel);
        binding.setLifecycleOwner(this);

        swipeRefreshLayout = binding.getRoot().findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

        swipeRefreshLayout.setOnRefreshListener( () -> {
            getData();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                swipeRefreshLayout.setRefreshing(false);

                switch (s) {
                    case "Retrieve Pesanan Success":
                        viewModel.setIsLoaded(true);
                        listPesanan = viewModel.getListPesanan().getValue();
                        adapter  = new PesananAdapter(listPesanan, getContext());
                        recyclerView.setAdapter(adapter);
                        break;
                    case "Data tidak ditemukan":
                        Toast.makeText(getContext(), "Pesanan Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.executePendingBindings();

        return  binding.getRoot();
    }

    private void getData() {
        SharedPreferences sp = getActivity().getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        String id = sp.getString("id", "");

        viewModel.getAllPesananUser(id);
    }

    public void back(View view) {
        getActivity().onBackPressed();
    }

    public void cetak(View view) {
        try {
            Toast.makeText(getContext(), "Clicked PDF", Toast.LENGTH_SHORT).show();
            createPdfWrapper();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

//        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Download/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
        }

        String pdfname = "Struk" + ".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, output);
        document.open();

        Paragraph judul = new Paragraph("Beli Item: \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        PdfPTable tables = new PdfPTable(new float[]{16, 8});
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph kepada = new Paragraph(
                "Kepada Yth: \n" + "Aditya Ryo Handoko" + "\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
        cellSupplier.addElement(kepada);
        tables.addCell(cellSupplier);

        PdfPCell cellPO = new PdfPCell();

        Paragraph NomorTanggal = new Paragraph(
                "No : " + "180709840" + "\n\n" +
                        "Tanggal : " + "12 December 2020" + "\n",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

        NomorTanggal.setPaddingTop(5);
        tables.addCell(NomorTanggal);
        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan daftar makanan yang dibeli: \n\n", f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5});
        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);

        PdfPCell h1 = new PdfPCell(new Phrase("ID Makanan"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        PdfPCell h2 = new PdfPCell(new Phrase("Nama Makanan"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Harga Makanan"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Beli"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);

        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);

        PdfPCell[] cells = tableHeader.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5,5});
        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        int arrLength = listPesanan.size();

        for (int x = 0; x < arrLength; x++) {
            for (int i = 0; i < cells.length; i++) {
                if (i == 0) {
                    tableData.addCell(listPesanan.get(x).getId_pesanan());
                } else if (i == 1) {
                    tableData.addCell(listPesanan.get(x).getNama_product());
                } else if (i == 2) {
                    tableData.addCell("Rp. " + listPesanan.get(x).getHarga_product());
                } else {
                    tableData.addCell(listPesanan.get(x).getJumlah_pesan());
                }
            }
        }

        document.add(tableData);

        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String tglDicetak = sdf.format(currentTime);
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = 0;
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("Izinkan aplikasi untuk akses penyimpanan?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new
                                                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }

    }

    private void previewPdf() {

        //isikan code previewPdf()
        PackageManager packageManager = getContext().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName() + ".provider",
                        pdfFile);
            } else {
                uri = Uri.fromFile(pdfFile);
            }
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            getContext().grantUriPermission("com.ryohandoko.restaurantuas.view.fragment", uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        } else {
//            FancyToast.makeText(getContext(), "Unduh pembuka PDF untuk menampilkan file ini",
//                    FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
        }

    }
}