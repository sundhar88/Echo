package com.ivara.aravi.echoshopping;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

public class documentListFetcher extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private Firebase fb;
    private String DocURL;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list_fetcher);
        Firebase.setAndroidContext(this);

        progressDialog = new ProgressDialog(documentListFetcher.this);
        progressDialog.setMessage("Fetching price list . . .");

        fb = new Firebase("https://abbsmeet.firebaseio.com/");

        Firebase data = fb.child("ECHOSHOPPER").child("DOCS");

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.show();
                DocURL = dataSnapshot.getValue(String.class);
                grabTheDoc();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }

    private void grabTheDoc() {

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.download(DocURL, Environment.getExternalStorageDirectory() + "/imageViewFolder","list.pdf")
                .build()
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        ViewPdf();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private void ViewPdf() {

        PDFView pdfView = (PDFView)findViewById(R.id.docs_view);

        File docs = new File(Environment.getExternalStorageDirectory() + "/imageViewFolder","list.pdf");

        pdfView.fromFile(docs)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }
}
