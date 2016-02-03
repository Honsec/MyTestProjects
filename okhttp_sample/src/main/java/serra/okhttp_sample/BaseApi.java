package serra.okhttp_sample;

import java.io.File;
import java.util.Map;

import hongsec.library.okhttp.OkHttpUtils;
import hongsec.library.okhttp.builder.GetBuilder;
import hongsec.library.okhttp.builder.PostFileBuilder;
import hongsec.library.okhttp.builder.PostFormBuilder;
import hongsec.library.okhttp.callback.Callback;

/**
 * Created by Hongsec on 2016-02-02.
 */
public abstract  class BaseApi {


    private GetBuilder getBuilder;
    private PostFormBuilder postBuilder;
    private PostFileBuilder postFileBuilder;


    protected abstract  Map<String,String> setHeaders();



    protected abstract  String getUrl();

    /**
     *  get params
     * @return
     */
    protected abstract Map<String,String> set_get_param();



    protected abstract String getJsonContent();


    protected  abstract File getFile();


    public void do_get(String tag, Callback callback){

        getBuilder = OkHttpUtils.get();

        //url
        getBuilder.url(getUrl());

        //add get Params
        getBuilder.params(set_get_param());

        //add headers
        getBuilder.headers(setHeaders());

        getBuilder.tag(tag);

        getBuilder.build().execute(callback);


    }






    public void do_post(String tag,Callback callback){

        postBuilder = OkHttpUtils.post();

        postBuilder.url(getUrl());

        postBuilder.params(set_get_param());

        postBuilder.headers(setHeaders());

        postBuilder.tag(tag);

        postBuilder.build().execute(callback);

    }



    public void do_postfile(String tag,Callback callback){

         postFileBuilder = OkHttpUtils.postFile();

        postFileBuilder.url(getUrl());

        postFileBuilder.params(set_get_param());

        postFileBuilder.file(getFile());

        postFileBuilder.tag(tag);

        postFileBuilder.build().execute(callback);

    }




    public void do_getImage(String tag,Callback callback){

        getBuilder = OkHttpUtils.get();

        getBuilder.url(getUrl());


        getBuilder.tag(tag);

        getBuilder.build().connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000).execute(callback);


    }



}
