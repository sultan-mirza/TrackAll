package in.kuari.trackall.controller;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import in.kuari.trackall.couriers.AFL;
import in.kuari.trackall.couriers.AJExpress;
import in.kuari.trackall.couriers.ANL;
import in.kuari.trackall.couriers.AirBorne;
import in.kuari.trackall.couriers.AirStar;
import in.kuari.trackall.couriers.AirState;
import in.kuari.trackall.couriers.AirWings;
import in.kuari.trackall.couriers.AkashGanga;
import in.kuari.trackall.couriers.AkrExpress;
import in.kuari.trackall.couriers.AlleppeyParcel;
import in.kuari.trackall.couriers.Bombino;
import in.kuari.trackall.couriers.DTDC;
import in.kuari.trackall.couriers.Default;
import in.kuari.trackall.interfaces.CourierDao;

/**
 * Created by sultan_mirza on 1/17/16.
 */
public class CourierController {
    private WebView webView;
    private long courierID;
    private Context context;

    public CourierController(Context context,WebView webView){
        this.context=context;
        this.webView=webView;

    }


    public void populateView(long id){
        CourierDao obj=null;
       // Log.d("idin popview",id+"");
        int i=(int)id;
        switch (i){
            case 1:
            obj=new DTDC(webView,context);
                break;
            case 2:
                obj=new Bombino(webView,context);
                break;
            case 3:
                obj=new AFL(webView,context);
                break;
            case 4:
                obj=new AirStar(webView,context);
                break;
            case 5:
                obj=new AirState(webView,context);
                break;
            case 6:
                obj=new AirBorne(webView,context);
                break;
            case 7:
                obj=new AirWings(webView,context);
                break;
            case 8:
                obj=new AJExpress(webView,context);
                break;
            case 9:
                obj=new AkashGanga(webView,context);
                break;
            case 10:
                obj=new AkrExpress(webView,context);
                break;
            case 11:
                obj=new AlleppeyParcel(webView,context);
                break;
            case 12:
                obj=new ANL(webView,context);
                break;

            default:
                obj=new Default(webView,context);

                break;
        }


obj.load();;

    }

}
