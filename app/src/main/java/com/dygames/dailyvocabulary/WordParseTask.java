package com.dygames.dailyvocabulary;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.RemoteViews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WordParseTask extends AsyncTask<String, Void, Elements> {

    private Exception exception;
    public MainActivity delegate;
    public Button wordTarget;
    public int wordTargetId;

    protected Elements doInBackground(String... word) {
        try {
            String url = "http://endic.naver.com/search.nhn?sLn=kr&isOnlyViewEE=N&query=" + word[0];

            Document doc = Jsoup.connect(url).get();
            Elements ele = doc.select("#content > div > dl > dt.first > span.fnt_e30 > a");

            Document doc2 = Jsoup.connect("http://endic.naver.com" + ele.attr("href")).get();
            Elements ele2 = doc2.select("#zoom_content > div > dl > dt > em > span.fnt_k06");

            return ele2;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(Elements result) {

        if(delegate!=null)
        {
            delegate.postResult(wordTarget, result);
        }
        else
        {
            AppData.getInstance().setTextMap.put(result.get(0).text(), wordTargetId);
        }
    }
}
