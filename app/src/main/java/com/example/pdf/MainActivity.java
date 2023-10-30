package com.example.pdf;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.edt1);
        et2=(EditText)findViewById(R.id.edt2);
        et3=(EditText)findViewById(R.id.edt3);
        et4=(EditText)findViewById(R.id.edt4);
        btn1=(Button)findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=et1.getText().toString();
                String age=et2.getText().toString();
                String contactno=et3.getText().toString();
                String location=et4.getText().toString();
                try {
                    createPdf(name,age,contactno,location);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPdf(String name, String age, String contactno, String location) throws
            FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "Np.pdf");
        OutputStream op = new FileOutputStream(file);
        PdfWriter pw = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(pw);
        Document document = new Document(pdfDocument);
        Drawable d = getDrawable(R.drawable.fl);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmmapData = stream.toByteArray();
        ImageData imagedata = ImageDataFactory.create(bitmmapData);
        Image image = new Image(imagedata);
        float width[] = {200f, 200f};
        Table table = new Table(width);
        table.addCell(new Cell().add(new Paragraph("Name")));
        table.addCell(new Cell().add(new Paragraph(name)));
        table.addCell(new Cell().add(new Paragraph("Age")));
        table.addCell(new Cell().add(new Paragraph(age)));
        table.addCell(new Cell().add(new Paragraph("Contact_ No")));
        table.addCell(new Cell().add(new Paragraph(contactno)));
        table.addCell(new Cell().add(new Paragraph("Loacation")));
        table.addCell(new Cell().add(new Paragraph(location)));
        document.add(image);
        document.add(table);
        document.close();
        Toast.makeText(this, "pdf Created", Toast.LENGTH_SHORT).show();
    }
}