package com.example.ck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ck.Utils.PDFAdapter;
import com.example.ck.Utils.PDFUtils;
import com.example.ck.item_class.cart_item;
import com.example.ck.request_api.CallApiUser;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity {
    public static final String TEN_FDF = "Hóa đơn mua hàng.pdf";
    Button buttonpdf, btnthanhtoandonhang;
    ImageView btntrangchu;
    ArrayList<cart_item> array = new ArrayList<cart_item>();
    private AlertDialog dialog;
    EditText recipient_name, recipient_phone, recipient_mail, recipient_adress;
    TextView sanphamgiaohang, total_price, phuongthuc;
    Integer dem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delivery);

        anhxa();
        addtoPDF();

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

      btnthanhtoandonhang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              thanhtoandonhang();
              buttonpdf.setVisibility(View.VISIBLE);
          }
      });
      btntrangchu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(DeliveryActivity.this, MainActivity.class);
              startActivity(intent);
          }
      });
    }

    public void anhxa()
    {
         recipient_name = findViewById(R.id.recipient_name);
         recipient_phone = findViewById(R.id.recipient_phone);
         recipient_mail = findViewById(R.id.recipient_email);
         sanphamgiaohang = findViewById(R.id.sanphamgiaohang);
        total_price = findViewById(R.id.total_price);
        phuongthuc = findViewById(R.id.phuongthuc);
        buttonpdf = findViewById(R.id.btnpdf);
        recipient_adress = findViewById(R.id.recipient_adress);
        btnthanhtoandonhang = findViewById(R.id.btnthanhtoandonhang);
        btntrangchu = findViewById(R.id.btntrangchu);
    }

    public void thanhtoandonhang()
    {
        String tempproduct = "", tempsize = "";
        String tempquatity ="";
        for(int i = 0; i < array.size(); i++)
        {
            if (tempproduct.isEmpty()) {
                tempproduct = array.get(i).getId();
            }else {
                tempproduct = tempproduct + "," + array.get(i).getId();
            }
            if (tempquatity.isEmpty()) {
                tempquatity = array.get(i).getQuantity().toString();
            }else {
                tempquatity = tempquatity + "," + array.get(i).getQuantity().toString();
            }
            if (tempsize.isEmpty()) {
                tempsize = array.get(i).getSize();
            }else {
                tempsize = tempsize + "," + array.get(i).getSize();
            }
        }
       // Log.d("THANHTOAN", tempproduct+"\n"+ tempsize+"\n"+ tempquatity);
        if(recipient_name.getText().toString().isEmpty() ||
                recipient_phone.getText().toString().isEmpty() ||
                recipient_mail.getText().toString().isEmpty() ||
                recipient_adress.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            CallApiUser.callApi.post_ApiOrder(recipient_name.getText().toString(),
                    recipient_phone.getText().toString(),
                    recipient_mail.getText().toString(),
                    recipient_adress.getText().toString(),
                    tempproduct, tempsize, tempquatity,
                    0,dem, product_activity.iduser
            ).enqueue(new Callback<cart_item>() {
                @Override
                public void onResponse(Call<cart_item> call, Response<cart_item> response) {
                    Log.d("THANHTOAN", "Thành công!");
                    btnthanhtoandonhang.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<cart_item> call, Throwable t) {
                    //Log.d("THANHTOAN", ""+t);
                }
            });
        }

    }
    private void addtoPDF()
    {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            array = (ArrayList<cart_item>) intent.getSerializableExtra("thanhtoan");
        //    Log.d("RECEIPT", array.toString());
            for (int i = 0; i < array.size(); i++)
            {
                dem += (array.get(i).getPrice()*array.get(i).getQuantity());
                sanphamgiaohang.append(array.get(i).getName()+ "\n"
                +"Giá: "+array.get(i).getPrice()+"\t"+ "Số lượng: "
                        + array.get(i).getQuantity() +"\n");
            }
            total_price.setText(String.valueOf(dem) + " VND");
        }
        dialog = new AlertDialog.Builder(this).setCancelable(false).setMessage("Xin đợi").create();
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
            BaseColor color1 = new BaseColor(220, 100, 100, 255);
            float fontsize = 26.5f;
         //  BaseFont fontname = BaseFont.createFont("assets/fonts/time_new_roman.ttf", "UTF-8", BaseFont.NOT_EMBEDDED);
            //create title
            Font titlefont = new Font();
            //titlefont.setStyle(PDType1Font.COURIER_BOLD);
            titlefont.setSize(42f);
            titlefont.setColor(BaseColor.BLACK);
            titlefont.setStyle(Font.NORMAL);
            PDFUtils.addNewItem(document, "PURCHASE INVOICE", Element.ALIGN_CENTER, titlefont);

            // thêm thông tin
            Font textfont = new Font();
            textfont.setSize(fontsize);
            textfont.setColor(color);
            textfont.setStyle(Font.NORMAL);
            PDFUtils.addNewItem(document, "Nhân viên: ", Element.ALIGN_LEFT, titlefont);
            PDFUtils.addNewItem(document, "\tDung Ngô", Element.ALIGN_LEFT, textfont);

            PDFUtils.addLineSeperator(document);

            //thêm detail
            PDFUtils.addLineSpace(document);
            PDFUtils.addNewItem(document, "DETAIL", Element.ALIGN_CENTER, titlefont);
            PDFUtils.addLineSeperator(document);


            PDFUtils.addLineSeperator(document);

            //use RxJava , fetch image from url to PDF
            Observable.fromIterable(array).flatMap(model-> getBitmapfromURL(this,model,document))
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(model-> {
                        // với mỗi item, thêm chi tết
                        PDFUtils.addNewItemWithLeftorRight(document, model.getName(),"", titlefont, textfont);

                        PDFUtils.addLineSeperator(document);

                        float fontsizegia = 30.5f;
                        Font textfontgia = new Font();
                        textfontgia.setSize(fontsizegia);
                        textfontgia.setColor(color1);
                        textfontgia.setStyle(Font.NORMAL);
                        PDFUtils.addNewItem(document, "Price: "+model.getPrice()*model.getQuantity(), Element.ALIGN_LEFT, textfontgia);

                       // PDFUtils.addLineSeperator(document);
                        float fontsizemota = 33.5f;
                        Font textfontmota = new Font();
                        textfontmota.setSize(fontsizemota);
                        textfontmota.setColor(color);
                        textfontmota.setStyle(Font.NORMAL);
                        PDFUtils.addNewItem(document, "Quatity: "+model.getQuantity(), Element.ALIGN_LEFT, textfontmota);


                        PDFUtils.addNewItem(document, "Size: "+model.getSize(), Element.ALIGN_LEFT, textfontgia);

                        PDFUtils.addLineSeperator(document);
                        PDFUtils.addLineSeperator(document);
                        PDFUtils.addLineSeperator(document);
                    }, throwable -> {
                    }, () ->{
                        //complete
                        PDFUtils.addNewItem(document, "TOTAL: " +dem, Element.ALIGN_RIGHT, titlefont);
                        PDFUtils.addLineSeperator(document);
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
    private ObservableSource<cart_item> getBitmapfromURL(DeliveryActivity deliveryActivity, cart_item array, Document document) {
        String url = "http://" + CallApiUser.LOCALHOST + ":3000/api/v1/products/" + array.getId().trim() + "/image";
        return Observable.fromCallable(() -> {
            Bitmap bitmap = Glide.with(DeliveryActivity.this)
                    .asBitmap().load(url).submit().get();

            Image image = Image.getInstance(bitmaptobytearray(bitmap));
            image.scaleAbsolute(420, 420);
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
        File file = new File(android.os.Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator
        + getResources().getString(R.string.app_name)
        +File.separator);
        if(!file.exists())
        {
            file.mkdir();
        }
        return file.getPath() +File.separator;
    }
}