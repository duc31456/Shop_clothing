package com.example.ck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.ck.Utils.PDFAdapter;
import com.example.ck.Utils.PDFUtils;
import com.example.ck.item_class.class_receipt;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeliveryActivity extends AppCompatActivity {
    public static final String TEN_FDF = "Hóa đơn mua hàng.pdf";
    Button buttonpdf;
    ArrayList<class_receipt> array = new ArrayList<class_receipt>();
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        dialog = new AlertDialog.Builder(this).setCancelable(false).setMessage("Xin đợi").create();
        array.add(new class_receipt("Ao the thao size M", "150000", "https://www.linkpicture.com/q/ao-nữ-6.jpg", "Beautiful for women"));
        array.add(new class_receipt("Ao the thao size L", "200000", "https://www.linkpicture.com/q/ao-đen.jpg", "Nice Nice Nice!"));


      buttonpdf = findViewById(R.id.btnpdf);
      Dexter.withActivity(this)
              .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
              .withListener(new PermissionListener() {
                  @Override
                  public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                      buttonpdf.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              try {
                                  createPDF(new StringBuilder(getAppPath()).append(TEN_FDF).toString());
                              } catch (IOException e) {
                                  e.printStackTrace();
                              }
                          }
                      });

                  }
                  @Override
                  public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                  }

                  @Override
                  public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                  }
              }).check();
    }

    private void createPDF(String path) throws IOException {
        if(new File(path).exists())
            new File(path).delete();

        try {
            Document document = new Document();
            //save
            PdfWriter.getInstance(document, new FileOutputStream(path));
            //open
            document.open();

            //tùy chỉnh
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("HELLO BRO");
            document.addCreator("SHOPSPORTER");

            //chỉnh font
            BaseColor color = new BaseColor(0, 153, 204, 255);
            float fontsize = 24.5f;
            BaseFont fontname = BaseFont.createFont("assets/fonts/time_new_roman.ttf", "UTF-8", BaseFont.EMBEDDED);

            //create title
            Font titlefont = new Font(fontname, 40.0f, Font.NORMAL, BaseColor.BLACK);
            PDFUtils.addNewItem(document, "RECEIPT", Element.ALIGN_CENTER, titlefont);

            // thêm thông tin
            Font textfont = new Font(fontname, fontsize, Font.NORMAL, color);
            PDFUtils.addNewItem(document, "Print By", Element.ALIGN_LEFT, titlefont);
            PDFUtils.addNewItem(document, "Dung Ngo", Element.ALIGN_LEFT, textfont);

            PDFUtils.addLineSeperator(document);

            //thêm detail
            PDFUtils.addLineSpace(document);
            PDFUtils.addNewItem(document, "DETAIL", Element.ALIGN_CENTER, titlefont);
            PDFUtils.addLineSeperator(document);

            //use RxJava , fetch image from url to PDF
            Observable.fromIterable(array).flatMap(model-> getBitmapfromURL(this,model,document))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(model-> {
                        // với mỗi item, thêm chi tết
                        PDFUtils.addNewItemWithLeftorRight(document, model.getTen(),"", titlefont, textfont);

                        PDFUtils.addLineSeperator(document);

                        PDFUtils.addNewItem(document, model.getGia(), Element.ALIGN_LEFT, textfont);

                        PDFUtils.addLineSeperator(document);
                        PDFUtils.addNewItem(document, model.getMota(), Element.ALIGN_LEFT, textfont);

                        PDFUtils.addLineSeperator(document);
                    }, throwable -> {

                    }, () ->{
                        //complete
                        document.close();
                        dialog.dismiss();
                        printPDF();
                    });
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            dialog.dismiss();
        }
    }

    private void printPDF() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PDFAdapter(this, new StringBuilder(getAppPath()).append(TEN_FDF).toString(), TEN_FDF);
            printManager.print("Document",printDocumentAdapter, new PrintAttributes.Builder().build());
        }catch (Exception e)
        {

        }
    }
    private ObservableSource<class_receipt> getBitmapfromURL(DeliveryActivity deliveryActivity, class_receipt array, Document document) {

        return Observable.fromCallable(() -> {
            Bitmap bitmap = Glide.with(DeliveryActivity.this)
                    .asBitmap().load(array.getImage()).submit().get();

            Image image = Image.getInstance(bitmaptobytearray(bitmap));
            image.scaleAbsolute(180, 200);
            document.add(image);

            return array;
        });
    }

    private byte[] bitmaptobytearray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return  stream.toByteArray();
    }

    private String getAppPath() {
        File file = new File(android.os.Environment.getExternalStorageDirectory() + File.separator
        + getResources().getString(R.string.app_name)
        +File.separator);
        if(!file.exists())
        {
            file.mkdir();
        }
        return file.getPath() +File.separator;
    }
}